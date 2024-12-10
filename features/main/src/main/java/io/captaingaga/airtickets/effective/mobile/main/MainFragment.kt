package io.captaingaga.airtickets.effective.mobile.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.GenericDiffCallback
import io.captaingaga.airtickets.effective.mobile.main.components.CyrillicInputValidation
import io.captaingaga.airtickets.effective.mobile.main.components.UiOfferItem
import io.captaingaga.airtickets.effective.mobile.main.components.toUiItems
import io.captaingaga.airtickets.effective.mobile.main.databinding.FragmentMainBinding
import io.captaingaga.airtickets.effective.mobile.main.ui.OfferItemDecorator
import io.captaingaga.airtickets.effective.mobile.main.ui.offerAdapter
import io.captaingaga.airtickets.effective.mobile.main.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val offersAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(GenericDiffCallback<UiOfferItem>()).build(),
            offerAdapter()
        )
    }

    private val mainViewModel: MainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            mainViewModel.params.collect { params ->
                binding.apply {
                    if (textFrom.text.isEmpty()) textFrom.setText(params.departFrom)
                }
            }
        }
        binding.apply {
            textFrom.addTextChangedListener(
                CyrillicInputValidation(
                    context = requireContext(),
                    editText = textFrom
                ) {
                    mainViewModel.updateDepart(it)
                }
            )
            textTo.setOnClickListener {
                OffersBottomSheetFragment()
                    .show(parentFragmentManager, OffersBottomSheetFragment.TAG)
            }
            offersRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = offersAdapter
                addItemDecoration(OfferItemDecorator(requireContext()))
            }
        }

        lifecycleScope.launch {
            mainViewModel.offer.collect { result ->
                when (result) {
                    is AppResult.Loading -> {} // TODO("shaw ui loading")

                    is AppResult.Success -> {
                        offersAdapter.items = result.data.toUiItems()
                    }

                    is AppResult.Failure -> {
                        Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT)
                            .show()
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
        private const val TAG = "MainFragment"
    }
}