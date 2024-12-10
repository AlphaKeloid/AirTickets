package io.captaingaga.airtickets.effective.mobile.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.captaingaga.airtickets.effective.mobile.common.GenericDiffCallback
import io.captaingaga.airtickets.effective.mobile.common.R
import io.captaingaga.airtickets.effective.mobile.main.components.CyrillicInputValidation
import io.captaingaga.airtickets.effective.mobile.main.components.UIRecommendedItem
import io.captaingaga.airtickets.effective.mobile.main.components.stub.recommendedStubList
import io.captaingaga.airtickets.effective.mobile.main.databinding.FragmentOffersBottomSheetBinding
import io.captaingaga.airtickets.effective.mobile.main.ui.recommendedAdapter
import io.captaingaga.airtickets.effective.mobile.main.viewmodels.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class OffersBottomSheetFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentOffersBottomSheetBinding? = null
    private val binding get() = checkNotNull(_binding)
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private val recommendationsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UIRecommendedItem>()).build(),
            recommendedAdapter(::recommendationsItemClick)
        )
    }

    private val mainViewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.behavior.apply {
            isFitToContents = false
            skipCollapsed = true
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOffersBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as BottomSheetDialog).behavior.apply {
            saveFlags = BottomSheetBehavior.SAVE_ALL
            state = BottomSheetBehavior.STATE_EXPANDED

        }
        val parentLayout =
            (dialog as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val layoutParams = parentLayout!!.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        parentLayout.layoutParams = layoutParams
        val imm = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        lifecycleScope.launch {
            mainViewModel.params.collect {
                binding.apply {
                    if (textFrom.editText?.text.toString().isEmpty()) {
                        textFrom.editText?.setText(it.departFrom)
                    }
                    if (textTo.editText?.text.toString().isEmpty()) {
                        textTo.editText?.setText(it.arriveTo)
                    }
                }
            }
        }

        binding.apply {
            recommendationsRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = recommendationsAdapter
                addItemDecoration(
                    object :
                        MaterialDividerItemDecoration(requireContext(), RecyclerView.VERTICAL) {
                        override fun getDividerColor(): Int {
                            return ContextCompat.getColor(requireContext(), R.color.grey_4)
                        }
                    }.apply {
                        dividerThickness = resources.getDimension(R.dimen.space_8).toInt() / 8
                        isLastItemDecorated = false
                    }
                )
            }

            textFrom.editText?.apply {
                addTextChangedListener(
                    CyrillicInputValidation(
                        context = requireContext(),
                        editText = textFrom.editText
                    ) {
                        mainViewModel.updateDepart(it)
                    }
                )
            }
            textTo.editText?.apply {
                requestFocus()
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                addTextChangedListener(
                    CyrillicInputValidation(
                        context = requireContext(),
                        editText = textFrom.editText
                    ) {
                        mainViewModel.updateArrive(it)
                    }
                )
                setOnEditorActionListener { view, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
                        navigateToSearch()
                    }
                    false

                }
            }
            anyWhere.setOnClickListener {
                val random = recommendedStubList.random().destinationTitle
                textTo.editText?.setText(random)
                mainViewModel.updateArrive(random)
            }

            // stubs
            complexRoute.setOnClickListener { /*TODO:  navigateToSearch to complexRoute stub */ }
            weekend.setOnClickListener { /*TODO:  navigateToSearch to weekend stub */ }
            lastMinuteTickets.setOnClickListener { /*TODO:  navigateToSearch to lastMinuteTickets stub */ }

        }

        recommendationsAdapter.items = recommendedStubList
    }

    private fun recommendationsItemClick(item: UIRecommendedItem) {
        binding.textTo.editText?.setText(item.destinationTitle)
        mainViewModel.updateArrive(item.destinationTitle)
    }

    private fun navigateToSearch() {
        lifecycleScope.launch {
            val params = mainViewModel.params.first()
            val toSearch = MainFragmentDirections
                .actionNavigationMainToSearchFeatureNavigationGraph(
                    from = params.departFrom,
                    to = params.arriveTo
                )
            findNavController().navigate(toSearch)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SearchBottomSheetFragment"
    }
}
