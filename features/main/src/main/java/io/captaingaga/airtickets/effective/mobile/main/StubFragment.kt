package io.captaingaga.airtickets.effective.mobile.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import io.captaingaga.airtickets.effective.mobile.main.components.stub.mapNavigationItem
import io.captaingaga.airtickets.effective.mobile.main.databinding.FragmentStubBinding

class StubFragment : Fragment() {

    private var _binding: FragmentStubBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val args: StubFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val stub = args.destination.mapNavigationItem()
            Glide.with(binding.root.context)
                .load(stub.second)
                .into(icon)
            title.text = ContextCompat.getString(requireContext(), stub.first)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}