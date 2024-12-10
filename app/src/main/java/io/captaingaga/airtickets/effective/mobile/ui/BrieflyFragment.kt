package io.captaingaga.airtickets.effective.mobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.captaingaga.airtickets.effective.mobile.databinding.FragmentBrieflyBinding
import io.captaingaga.airtickets.effective.mobile.databinding.FragmentHotelsBinding

class BrieflyFragment : Fragment() {

    private var _binging: FragmentBrieflyBinding? = null
    private val binding get() = checkNotNull(_binging)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binging = FragmentBrieflyBinding.inflate(inflater, container, false)
        return binding.root
    }
}