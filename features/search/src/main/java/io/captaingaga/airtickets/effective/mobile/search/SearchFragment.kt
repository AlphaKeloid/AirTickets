package io.captaingaga.airtickets.effective.mobile.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.GenericDiffCallback
import io.captaingaga.airtickets.effective.mobile.common.R
import io.captaingaga.airtickets.effective.mobile.search.comppnents.UITicketOfferItem
import io.captaingaga.airtickets.effective.mobile.search.comppnents.toFormattedDate
import io.captaingaga.airtickets.effective.mobile.search.comppnents.toUiItems
import io.captaingaga.airtickets.effective.mobile.search.databinding.FragmentSearchBinding
import io.captaingaga.airtickets.effective.mobile.search.ui.offersTicketsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binging get() = checkNotNull(_binding)
    private val offersTicketsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UITicketOfferItem>()).build(),
            offersTicketsAdapter(::offersTicketsItemClick)
        )
    }
    private val offersTicketsViewModel: OffersTicketsViewModel by viewModel<OffersTicketsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = SearchFragmentArgs.fromBundle(requireArguments())
        binging.apply {
            goBack.setOnClickListener { findNavController().popBackStack() }
            textFrom.editText?.setText(args.from)
            textTo.editText?.setText(args.to)
            textFrom.setEndIconOnClickListener { swapText() }
            showAll.visibility = View.GONE
            directFlightsRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = offersTicketsAdapter
                addItemDecoration(object :
                    MaterialDividerItemDecoration(requireContext(), RecyclerView.VERTICAL) {
                    override fun getDividerColor(): Int {
                        return ContextCompat.getColor(requireContext(), R.color.grey_4)
                    }
                }.apply {
                    dividerThickness = resources.getDimension(R.dimen.space_small).toInt() / 8
                    isLastItemDecorated = false
                })
            }
            showAllTickets.setOnClickListener { /* TODO: navigate next screen */ }
            comeBack.setOnClickListener {
                MaterialDatePicker.Builder.datePicker().setTitleText("Дата обратного вылета")
                    .build().show(parentFragmentManager, MaterialDatePicker::class.java.name)
            }
            datePicker.setOnClickListener {
                val picker =
                    MaterialDatePicker.Builder.datePicker().setTitleText("Дата вылета").build()
                picker.addOnPositiveButtonClickListener { epochMillis ->
                    datePicker.text = epochMillis.toFormattedDate()
                }

                picker.show(parentFragmentManager, MaterialDatePicker::class.java.name)
            }
            filters.setOnClickListener { /* TODO: navigate next screen */ }
        }

        lifecycleScope.launch {
            offersTicketsViewModel.offersTickets.collect { result ->
                when (result) {
                    is AppResult.Loading -> {} // TODO("shaw ui loading")

                    is AppResult.Success -> {
                        offersTicketsAdapter.items = result.data.toUiItems()
                    }

                    is AppResult.Failure -> {
                        Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun offersTicketsItemClick(item: UITicketOfferItem) {
        Log.i(TAG, "offersTicketsItemClick: ${item.title}")
    }

    private fun swapText() {
        binging.apply {
            val from = textFrom.editText?.text.toString()
            val to = textTo.editText?.text.toString()

            textFrom.editText?.setText(to)
            textTo.editText?.setText(from)
        }
    }

    companion object {
        const val TAG = "SearchFragment"
    }
}