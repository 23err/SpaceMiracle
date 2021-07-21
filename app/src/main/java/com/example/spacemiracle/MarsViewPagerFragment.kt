package com.example.spacemiracle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.example.spacemiracle.databinding.FragmentMarsViewPagerBinding

private const val MARS_PHOTO = "mars photo"

class MarsViewPagerFragment : Fragment() {
    private var marsPhoto: MarsPhoto? = null

    private var _binding: FragmentMarsViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            marsPhoto = it.getParcelable(MARS_PHOTO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMarsViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewData()

    }

    private fun setViewData() = with(binding) {
        marsPhoto?.let {
            tvLabel.text = it.name
            imageView.load(it.urlImage)
            tvFullName.text = it.fullName
            tvSol.text = it.sol.toString()
            tvEarthDate.text = it.earthDate
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(marsPhoto: MarsPhoto) =
            MarsViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MARS_PHOTO, marsPhoto)
                }
            }
    }
}