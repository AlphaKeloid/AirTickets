package io.captaingaga.airtickets.effective.mobile.stub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import io.captaingaga.airtickets.effective.mobile.stub.components.mapBottomNavigationItems
import io.captaingaga.airtickets.effective.mobile.stub.databinding.FragmentBottomNavigationStubBinding

private const val ARG_DESTINATION = "destination"

class BottomNavigationStubFragment : Fragment() {
    private var destination: Int? = null

    private val args: BottomNavigationStubFragmentArgs by navArgs()

    private var _binding: FragmentBottomNavigationStubBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            destination = it.getInt(ARG_DESTINATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationStubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stub = args.destination.mapBottomNavigationItems()
        binding.apply {
            Glide.with(binding.root.context)
                .load(stub.second)
                .into(icon)
            title.text = ContextCompat.getString(requireContext(), stub.first)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(destination: Int) =
            BottomNavigationStubFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_DESTINATION, destination)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}