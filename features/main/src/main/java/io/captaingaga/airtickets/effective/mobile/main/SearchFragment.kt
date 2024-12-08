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
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.captaingaga.airtickets.effective.mobile.common.R
import io.captaingaga.airtickets.effective.mobile.main.components.UIRecommendedItem
import io.captaingaga.airtickets.effective.mobile.main.components.stub.recommendedStubList
import io.captaingaga.airtickets.effective.mobile.main.databinding.FragmentSearchBinding
import io.captaingaga.airtickets.effective.mobile.main.ui.GenericDiffCallback
import io.captaingaga.airtickets.effective.mobile.main.ui.recommendedAdapter

private const val ARG_FOCUS_FIELD = "focus_field"

class SearchFragment : BottomSheetDialogFragment() {

    private var focusField: Int? = null
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val recommendationsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UIRecommendedItem>()).build(),
            recommendedAdapter(::recommendationsItemClick)
        )
    }


    companion object {
        const val TAG = "SearchFragment"

        @JvmStatic
        fun forFromField() = newInstance(FocusField.FROM)

        fun forToField() = newInstance(FocusField.TO)

        private fun newInstance(focusField: Int) = SearchFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_FOCUS_FIELD, focusField)
            }
        }

        object FocusField {
            const val FROM = 0
            const val TO = 1
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            focusField = it.getInt(ARG_FOCUS_FIELD)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.behavior.apply {
            isFitToContents = false
            skipCollapsed = true
        }
        return bottomSheetDialog
    }

    //
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (dialog as BottomSheetDialog).behavior.apply {
            saveFlags = BottomSheetBehavior.SAVE_ALL
            state = BottomSheetBehavior.STATE_EXPANDED

        }
        val parentLayout =
            (dialog as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val layoutParams = parentLayout!!.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        parentLayout.layoutParams = layoutParams

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
                        dividerThickness = resources.getDimension(R.dimen.space_small).toInt() / 8
                        isLastItemDecorated = false
                    }
                )
            }
            textTo.editText
                ?.setOnEditorActionListener { _, actionId, _ -> imeActionDone(actionId) }
            anyWhere.setOnClickListener {
                textTo.editText?.setText(recommendedStubList.random().destinationTitle)
            }

            // stubs
            complexRoute.setOnClickListener { /*TODO:  navigate to complexRoute stub */ }
            weekend.setOnClickListener { /*TODO:  navigate to weekend stub */ }
            lastMinuteTickets.setOnClickListener { /*TODO:  navigate to lastMinuteTickets stub */ }

        }

        recommendationsAdapter.items = recommendedStubList

        val imm = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        when (focusField) {
            FocusField.FROM -> binding.textFrom.editText?.apply {
                requestFocus()
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }

            FocusField.TO -> binding.textTo.editText?.apply {
                requestFocus()
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    private fun recommendationsItemClick(item: UIRecommendedItem) {
        binding.textTo.editText?.setText(item.destinationTitle)
    }

    private fun imeActionDone(action: Int): Boolean {
        if (action == EditorInfo.IME_ACTION_DONE) {
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HIDDEN
            // TODO: navigate to next screen
            return true
        }
        return false
    }
}
