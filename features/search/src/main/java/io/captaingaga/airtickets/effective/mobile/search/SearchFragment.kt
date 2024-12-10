package io.captaingaga.airtickets.effective.mobile.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.GenericDiffCallback
import io.captaingaga.airtickets.effective.mobile.common.R
import io.captaingaga.airtickets.effective.mobile.data.utils.toFormattedDate
import io.captaingaga.airtickets.effective.mobile.search.comppnents.UITicketOfferItem
import io.captaingaga.airtickets.effective.mobile.search.comppnents.asString
import io.captaingaga.airtickets.effective.mobile.search.comppnents.toUiItems
import io.captaingaga.airtickets.effective.mobile.search.databinding.FragmentSearchBinding
import io.captaingaga.airtickets.effective.mobile.search.ui.offersTicketsAdapter
import io.captaingaga.airtickets.effective.mobile.search.viewmodels.OffersTicketsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val offersTicketsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UITicketOfferItem>()).build(),
            offersTicketsAdapter(::offersTicketsItemClick)
        )
    }

    private val args: SearchFragmentArgs by navArgs()
    private val offersTicketsViewModel: OffersTicketsViewModel by viewModel {
        parametersOf(args.from, args.to)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            offersTicketsViewModel.date
        }

        binding.apply {
            goBack.setOnClickListener { findNavController().popBackStack() }
            swap.setOnClickListener { swapText() }
            comeBack.setOnClickListener {
                datePickerDialog("Дата обратного вылета")
                    .show(parentFragmentManager, MaterialDatePicker::class.java.name)
            }
            datePicker.apply {
                setOnClickListener {
                    val picker = datePickerDialog("Дата вылета")
                    picker.addOnPositiveButtonClickListener { epochMillis ->
                        val selected = epochMillis.toFormattedDate()
                        offersTicketsViewModel.updateDate(selected)
                    }
                    picker.show(parentFragmentManager, MaterialDatePicker::class.java.name)
                }
            }
            howMany.setOnClickListener { }
            filters.setOnClickListener {
                val toFiltersStub =
                    SearchFragmentDirections.actionSearchFragmentToFiltersStubFragment()
                findNavController().navigate(toFiltersStub)
            }
            showAll.visibility = View.GONE
            showAllTickets.setOnClickListener {
                lifecycleScope.launch {
                    val route = offersTicketsViewModel.route.first()
                    val date = offersTicketsViewModel.date.first()

                    val toTickets = SearchFragmentDirections
                        .actionSearchFragmentToTicketsFeatureNavigationGraph(
                            route = route.asString(),
                            date = date,
                            passengers = 1
                        )
                    findNavController().navigate(toTickets)
                }
            }

            directFlightsRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = offersTicketsAdapter
                addItemDecoration(
                    MaterialDividerItemDecoration(
                        requireContext(),
                        RecyclerView.VERTICAL
                    ).apply {
                        dividerThickness = resources.getDimension(R.dimen.space_8).toInt() / 8
                        isLastItemDecorated = false
                    })
            }
        }
        lifecycleScope.launch {
            offersTicketsViewModel.date.collect { value ->
                binding.datePicker.text = value
            }
        }

        lifecycleScope.launch {
            offersTicketsViewModel.route.collect { value ->
                binding.textFrom.text = value.from
                binding.textTo.text = value.to
            }
        }

        lifecycleScope.launch {
            offersTicketsViewModel.offersTickets.collect { result ->
                when (result) {
                    is AppResult.Loading -> { /* TODO: Show loading */
                    }

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


    private fun swapText() {
        binding.apply {
            val from = textFrom.text.toString()
            val to = textTo.text.toString()
            offersTicketsViewModel.updateRoute(to, from)
        }
    }

    private fun datePickerDialog(title: String): MaterialDatePicker<Long> {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(today)
            .build()
    }

    private fun offersTicketsItemClick(item: UITicketOfferItem) {
        Log.i(TAG, "offersTicketsItemClick: ${item.title}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SearchFragment"
    }
}
