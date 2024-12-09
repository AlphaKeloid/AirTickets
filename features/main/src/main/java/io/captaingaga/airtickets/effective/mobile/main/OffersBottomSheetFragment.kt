package io.captaingaga.airtickets.effective.mobile.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import io.captaingaga.airtickets.effective.mobile.main.components.UIRecommendedItem
import io.captaingaga.airtickets.effective.mobile.main.components.stub.recommendedStubList
import io.captaingaga.airtickets.effective.mobile.main.databinding.FragmentOffersBottomSheetBinding
import io.captaingaga.airtickets.effective.mobile.main.ui.CyrillicInputValidation
import io.captaingaga.airtickets.effective.mobile.main.ui.recommendedAdapter
import io.captaingaga.airtickets.effective.mobile.main.viewmodels.NavParamsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_FROM_FIELD = "from"
private const val ARG_TO_FIELD = "to"

class OffersBottomSheetFragment : BottomSheetDialogFragment() {

    private var inputFieldFrom: String? = null
    private var inputFieldTo: String? = null

    private var _binding: FragmentOffersBottomSheetBinding? = null
    private val binding get() = checkNotNull(_binding)
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private val recommendationsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UIRecommendedItem>()).build(),
            recommendedAdapter(::recommendationsItemClick)
        )
    }

    private val navParamsViewModel: NavParamsViewModel by viewModel<NavParamsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inputFieldFrom = arguments?.getString(ARG_FROM_FIELD)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_FROM_FIELD, binding.textFrom.editText?.text.toString())
        outState.putString(ARG_TO_FIELD, binding.textTo.editText?.text.toString())
    }

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
        savedInstanceState: Bundle?
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
            navParamsViewModel.params.collect { params ->
                inputFieldFrom = params.departFrom.takeIf { it.isNotEmpty() } ?: inputFieldFrom
                inputFieldTo = params.arriveTo
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
                setText(inputFieldFrom)
                navParamsViewModel.updateDepart(inputFieldFrom.orEmpty())
                addTextChangedListener(
                    CyrillicInputValidation(
                        context = requireContext(),
                        editText = textFrom.editText
                    ) {
                        navParamsViewModel.updateDepart(it)
                    }
                )
            }
            textTo.editText?.apply {
                setText(inputFieldTo)
                requestFocus()
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                addTextChangedListener(
                    CyrillicInputValidation(
                        context = requireContext(),
                        editText = textFrom.editText
                    ) {
                        navParamsViewModel.updateArrive(it)
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
                navParamsViewModel.updateArrive(random)
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
        navParamsViewModel.updateArrive(item.destinationTitle)
    }

    private fun navigateToSearch() {
        lifecycleScope.launch {
            navParamsViewModel.params.collect {
                val toSearch = MainFragmentDirections
                    .actionNavigationMainToSearchFeatureNavigationGraph(
                        from = it.departFrom,
                        to = it.arriveTo
                    )
                findNavController().navigate(toSearch)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SearchBottomSheetFragment"

        @JvmStatic
        fun newInstance(from: String) = OffersBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_FROM_FIELD, from)
            }
        }
    }
}
