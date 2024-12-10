package io.captaingaga.airtickets.effective.mobile.stub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.captaingaga.airtickets.effective.mobile.stub.databinding.FragmentFiltersStubBinding

class FiltersStubFragment : Fragment() {

    private var _binding: FragmentFiltersStubBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersStubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}