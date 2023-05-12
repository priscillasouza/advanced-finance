package com.advancedfinance.entrance.presentation.screen.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.advancedfinance.entrance.databinding.EntrancePagerViewOnboardingItemBinding

class OnboardingViewPagerAdapter(
    private var title: List<String>,
    private var subtitle: List<String>,
    private var images: List<Int>
) : RecyclerView.Adapter<OnboardingViewPagerAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = EntrancePagerViewOnboardingItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewPagerHolder(binding)
    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewPagerHolder(
        layout: EntrancePagerViewOnboardingItemBinding
    ) : RecyclerView.ViewHolder(layout.root) {
        val itemTitle = layout.textViewTitleOnboarding
        val itemSubtitle = layout.textViewSubtitleOnboarding
        val itemImage = layout.imageOnboarding
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.apply {
            itemTitle.text = title[position]
            itemSubtitle.text = subtitle[position]
            itemImage.setImageResource(images[position])
        }
    }
}