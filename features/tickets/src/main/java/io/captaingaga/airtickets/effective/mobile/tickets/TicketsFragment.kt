package io.captaingaga.airtickets.effective.mobile.tickets

import android.os.Bundle
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
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.GenericDiffCallback
import io.captaingaga.airtickets.effective.mobile.tickets.components.UITicketItem
import io.captaingaga.airtickets.effective.mobile.tickets.components.toUiItems
import io.captaingaga.airtickets.effective.mobile.tickets.databinding.FragmentTicketsBinding
import io.captaingaga.airtickets.effective.mobile.tickets.ui.TicketItemDecorator
import io.captaingaga.airtickets.effective.mobile.tickets.ui.ticketsAdapter
import io.captaingaga.airtickets.effective.mobile.tickets.viewmodels.TicketsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class TicketsFragment : Fragment() {

    private var _binding: FragmentTicketsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val ticketsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UITicketItem>()).build(),
            ticketsAdapter { }
        )
    }

    private val ticketsViewModel: TicketsViewModel by viewModel<TicketsViewModel>()

    private val args: TicketsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.apply {
            goBack.setOnClickListener { navController.popBackStack() }
            route.text = args.route
            info.text = StringBuilder()
                    .append(args.date).append(", ")
                    .append(args.passengers.toString())
                    .append(" пассажир").toString()
            ticketsRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = ticketsAdapter
                addItemDecoration(TicketItemDecorator(requireContext()))
            }
            filter.setOnClickListener { }
            chart.setOnClickListener { }
        }
        lifecycleScope.launch {
            ticketsViewModel.tickets.collect { result ->
                when (result) {
                    is AppResult.Failure -> Toast.makeText(
                        requireContext(),
                        "Что-то пошло не так",
                        Toast.LENGTH_SHORT
                    ).show()

                    AppResult.Loading -> {} // TODO("shaw ui loading")
                    is AppResult.Success -> {
                        ticketsAdapter.items = result.data.toUiItems()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "TicketsFragment"
    }
}
