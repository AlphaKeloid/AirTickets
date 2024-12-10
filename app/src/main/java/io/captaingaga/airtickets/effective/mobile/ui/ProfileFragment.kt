package io.captaingaga.airtickets.effective.mobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.captaingaga.airtickets.effective.mobile.databinding.FragmentHotelsBinding
import io.captaingaga.airtickets.effective.mobile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binging: FragmentProfileBinding? = null
    private val binding get() = checkNotNull(_binging)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binging = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}