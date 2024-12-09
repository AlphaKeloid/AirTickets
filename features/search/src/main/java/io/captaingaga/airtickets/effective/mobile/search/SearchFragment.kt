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
import io.captaingaga.airtickets.effective.mobile.search.comppnents.isEmpty
import io.captaingaga.airtickets.effective.mobile.search.comppnents.toUiItems
import io.captaingaga.airtickets.effective.mobile.search.databinding.FragmentSearchBinding
import io.captaingaga.airtickets.effective.mobile.search.ui.offersTicketsAdapter
import io.captaingaga.airtickets.effective.mobile.search.viewmodels.OffersTicketsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_FROM_FIELD = "fromText"
private const val ARG_TO_FIELD = "toText"

class SearchFragment : Fragment() {

    private var routeParam: String? = null
    private var textFromField: String? = null
    private var textToField: String? = null
    private var date: String? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val offersTicketsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UITicketOfferItem>()).build(),
            offersTicketsAdapter(::offersTicketsItemClick)
        )
    }

    private val args: SearchFragmentArgs by navArgs()
    private val offersTicketsViewModel: OffersTicketsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _binding?.let {
            outState.putString(ARG_FROM_FIELD, it.textFrom.text.toString())
            outState.putString(ARG_TO_FIELD, it.textTo.text.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textFromField = savedInstanceState?.getString(ARG_FROM_FIELD) ?: textFromField
        textToField = savedInstanceState?.getString(ARG_TO_FIELD) ?: textToField
        if (savedInstanceState == null) {
            offersTicketsViewModel.updateRoute(args.from, args.to)
        }
        lifecycleScope.launch {
            offersTicketsViewModel.date.collect { date = it }
        }

        binding.apply {
            goBack.setOnClickListener { findNavController().popBackStack() }
            textFrom.text = textFromField
            textTo.text = textToField
            swap.setOnClickListener { swapText() }
            comeBack.setOnClickListener {
                datePickerDialog("Дата обратного вылета")
                    .show(parentFragmentManager, MaterialDatePicker::class.java.name)
            }
            datePicker.apply {
                text = date
                setOnClickListener {
                    val picker = datePickerDialog("Дата вылета")
                    picker.addOnPositiveButtonClickListener { epochMillis ->
                        val selected = epochMillis.toFormattedDate()
                        offersTicketsViewModel.updateDate(selected)
                        datePicker.text = selected
                        date = selected
                    }
                    picker.show(parentFragmentManager, MaterialDatePicker::class.java.name)
                }
            }
            howMany.setOnClickListener {  }
            filters.setOnClickListener {  }
            showAll.visibility = View.GONE
            showAllTickets.setOnClickListener {
                val toTickets = SearchFragmentDirections
                    .actionSearchFragmentToTicketsFeatureNavigationGraph(
                        route = routeParam.orEmpty(),
                        date = date.orEmpty(),
                        passengers = 1
                    )
                findNavController().navigate(toTickets)
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
            offersTicketsViewModel.route.collect { param ->
                routeParam = param.takeIf { !it.isEmpty() }?.asString()
                textFromField = param.from
                textToField = param.to
                binding.textFrom.text = textFromField
                binding.textTo.text = textToField
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
            textFrom.text = to
            textTo.text = from
            textFromField = to
            textToField = from
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
