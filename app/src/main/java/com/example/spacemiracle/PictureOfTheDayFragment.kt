package com.example.spacemiracle

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.transition.*
import coil.api.load
import com.example.spacemiracle.databinding.PictureOfTheDayFragmentBinding
import com.example.spacemiracle.repository.PictureOfTheDayData
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_mars_view_pager.*
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*

class PictureOfTheDayFragment : Fragment() {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private const val TAG = "PictureOfTheDayFragment"
    }

    private lateinit var viewModel: PODViewModel
    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding get() = _binding!!
    private var isExpandedPhoto = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PictureOfTheDayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PODViewModel::class.java)
        viewModel.getData()
            .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
        setImageViewClickListener()

        imageWiki.setOnClickListener{
            TransitionManager.beginDelayedTransition(fragmentContainer, Slide(Gravity.END).setDuration(500))
            it.visibility = View.INVISIBLE
            tilWiki.visibility = View.VISIBLE
        }
    }

    private fun setImageViewClickListener() {
        ivPhotoOfDay.setOnClickListener {
            isExpandedPhoto = !isExpandedPhoto
            TransitionManager.beginDelayedTransition(
                fragmentContainer,
                TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val layoutParams = ivPhotoOfDay.layoutParams
            layoutParams.apply {
                if (isExpandedPhoto) {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    ivPhotoOfDay.scaleType = ImageView.ScaleType.CENTER_CROP
                } else {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                    ivPhotoOfDay.scaleType = ImageView.ScaleType.FIT_CENTER
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wikiSetClickListener()
        setChipClickListener()
    }

    private fun setChipClickListener() {
        binding.cgDay.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                Log.d(TAG, "onViewCreated: ${it.id}")
                when (it.id) {
                    R.id.chipDayBefore -> viewModel.getData(Day.BEFORE_DAY)
                    R.id.chipYesterday -> viewModel.getData(Day.YESTERDAY)
                    R.id.chipToday -> viewModel.getData()
                }
            }
        }
    }

    private fun wikiSetClickListener() {
        binding.tilWiki.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.etWiki.text.toString()}")
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(data: PictureOfTheDayData) = with(binding) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                loadingVisible(false)
                val serverResponseData = data.serverResponseData
//                tvTitle.text = serverResponseData.title
                var themePrimaryColor = Color.RED
                activity?.apply {
                    val typedValue = TypedValue()
                    theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
                    val themePrimaryColor = typedValue.data
                }

                val spannable = SpannableString(serverResponseData.title).apply {
                    setSpan(
                        ForegroundColorSpan(themePrimaryColor),
                        0, 1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        RelativeSizeSpan(2f),
                        0,1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }

                tvTitle.text = spannable
                tvDescription.animatedChangeText(fragmentContainer, serverResponseData.explanation)

                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    Log.d(TAG, "url picture is empty")
                } else {
                    ivPhotoOfDay.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_error_outline)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                Log.d(TAG, "Loading data")
                loadingVisible()
            }
            is PictureOfTheDayData.Error -> {
                loadingVisible(false)
                data.error.message?.let {
                    Log.e(TAG, it)
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadingVisible(visible: Boolean = true) = with(binding) {
        if (visible) {
            loadingLayout.root.visibility = View.VISIBLE
        } else {
            loadingLayout.root.visibility = View.GONE
        }

    }


}