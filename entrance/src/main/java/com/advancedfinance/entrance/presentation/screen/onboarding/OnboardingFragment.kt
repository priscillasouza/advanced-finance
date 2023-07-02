package com.advancedfinance.entrance.presentation.screen.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.advancedfinance.entrance.R
import com.advancedfinance.entrance.databinding.EntranceFragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var binding: EntranceFragmentOnboardingBinding
    private var titleList = mutableListOf<String>()
    private var subtitleList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = EntranceFragmentOnboardingBinding.inflate(layoutInflater, container, false)
        setViewPagerAdapter()
        setListAdapter()
        configureIndicator()
        return binding.root
    }

    private fun setViewPagerAdapter() {
        binding.apply {
            viewPagerOnboarding.adapter = OnboardingViewPagerAdapter(titleList, subtitleList, imageList)
            viewPagerOnboarding.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            buttonOnboarding.setOnClickListener {
                findNavController().navigate(OnboardingFragmentDirections.entranceActionEntranceOnboardingfragmentToEntranceLoginfragment())
            }
        }
    }

    private fun setList(title: String, subtitle: String, image: Int) {
        titleList.add(title)
        subtitleList.add(subtitle)
        imageList.add(image)
    }

    private fun setListAdapter() {
        setList(getString(R.string.entrance_text_title_first_page_onboarding),
            getString(R.string.entrance_text_subtitle_first_page_onboarding),
            R.drawable.image_app)
        setList(getString(R.string.entrance_text_title_second_page_onboarding),
            getString(R.string.entrance_text_subtitle_second_page_onboarding),
            R.drawable.image_app)
        setList(getString(R.string.entrance_text_title_third_page_onboarding),
            getString(R.string.entrance_text_subtitle_third_page_onboarding), R.drawable.image_app)
        setList(getString(R.string.entrance_text_title_fourth_page_onboarding),
            getString(R.string.entrance_text_subtitle_fourth_page_onboarding),
            R.drawable.image_app)
    }

    private fun configureIndicator() {
        val indicator3 = binding.indicatorOnboarding
        indicator3.setViewPager(binding.viewPagerOnboarding)
    }
}