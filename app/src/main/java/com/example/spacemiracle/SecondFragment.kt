package com.example.spacemiracle

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spacemiracle.databinding.SecondFragmentBinding
import com.example.spacemiracle.repository.MarsPicturesData

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel
    private var _binding: SecondFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MarsViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MarsViewPagerAdapter(childFragmentManager)
        binding.MarsViewPager.adapter = adapter
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner) { render(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(it: MarsPicturesData) {
        when (it) {
            is MarsPicturesData.Success -> {
                adapter.apply {
                    list = it.serverResponseData.photos
                    notifyDataSetChanged()
                }

            }
            is MarsPicturesData.Loading -> {

            }
            is MarsPicturesData.Error -> {

            }
        }
    }
}