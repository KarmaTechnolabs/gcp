package com.app.estore.ui.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.animation.addListener
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.app.estore.R
import com.app.estore.adapter.IntroductionPageAdapter
import com.app.estore.base.BaseActivity
import com.app.estore.custom.gotoActivity
import com.app.estore.databinding.ActivityOnBoardingBinding
import com.app.estore.model.IntroductionModel
import com.app.estore.utils.UserStateManager
import java.util.*


class OnBoardingActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnBoardingBinding

    private lateinit var introductionPageAdapter: IntroductionPageAdapter
    private var currentSelectedPage = 0
    private var previousSelectedPage = -1
    private val introductionModels: List<IntroductionModel> by lazy {
        createIntroModels()
    }
    private var startEndValue: Pair<Float, Float>? = null
    private var timer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)

        introductionPageAdapter = IntroductionPageAdapter(introductionModels)
        binding.viewPager.adapter = introductionPageAdapter
        binding.dotsIndicator.setViewPager2(binding.viewPager)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                previousSelectedPage = currentSelectedPage
                currentSelectedPage = position
                checkPageStartEnd()
            }
        })
        binding.nextTV.setOnClickListener(this)
        binding.skipTV.setOnClickListener(this)
        binding.getStartedTV.setOnClickListener(this)

        val width: Int = Resources.getSystem().displayMetrics.widthPixels

        startEndValue = Pair(
            (width.toFloat() / 2).minus(binding.skipTV.width),
            (width.toFloat() / 2).minus(binding.nextTV.width)
        )

        pageAutoSwitcher()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.nextTV -> {
                previousSelectedPage = currentSelectedPage
                currentSelectedPage += 1
                if (currentSelectedPage < introductionModels.size) {
                    binding.viewPager.currentItem = currentSelectedPage
                }
                checkPageStartEnd()
            }
            R.id.skipTV -> {
                previousSelectedPage = currentSelectedPage
                binding.viewPager.currentItem = introductionModels.size - 1
                currentSelectedPage = introductionModels.size - 1
                checkPageStartEnd()
            }
            R.id.getStartedTV -> {
                UserStateManager.markOnBoardingComplete()
                gotoActivity(LoginContainerActivity::class.java, clearAllActivity = true)

            }
        }
    }


    private fun checkPageStartEnd() {
        //TODO - MAKE ANIMATION MORE SMOOTH AND ATTRACTIVE
        if (currentSelectedPage == 2) {

            //Cancel the auto pager so that it won't run again
            timer?.cancel()

            val skipAnimator =
                ObjectAnimator.ofFloat(binding.skipTV, "translationX", startEndValue?.first ?: 0F)
                    .apply {
                        duration = 400
                    }
            val nextAnimator =
                ObjectAnimator.ofFloat(
                    binding.nextTV,
                    "translationX",
                    startEndValue?.second?.unaryMinus() ?: 0F
                )
                    .apply {
                        duration = 400
                    }

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(skipAnimator, nextAnimator)
            animatorSet.addListener({
                binding.skipTV.visibility = View.INVISIBLE
                binding.nextTV.visibility = View.INVISIBLE
                binding.getStartedTV.visibility = View.VISIBLE
            })
            animatorSet.start()
        } else {
            //Again Assign the page change listener so that it can work properly
            timer?.cancel()
            timer = null
            pageAutoSwitcher()

            if (previousSelectedPage == 2) {
                val skipAnimator =
                    ObjectAnimator.ofFloat(binding.skipTV, "translationX", 0F)
                        .apply {
                            duration = 400
                        }
                val nextAnimator =
                    ObjectAnimator.ofFloat(binding.nextTV, "translationX", 0F)
                        .apply {
                            duration = 400
                        }

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(skipAnimator, nextAnimator)
                animatorSet.addListener({}, {
                    binding.getStartedTV.visibility = View.INVISIBLE
                    binding.skipTV.visibility = View.VISIBLE
                    binding.nextTV.visibility = View.VISIBLE
                })
                animatorSet.start()
            }
        }
    }

    private fun pageAutoSwitcher(seconds: Int = 5) {
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (currentSelectedPage != introductionModels.size - 1)
                        binding.nextTV.performClick()
                }
            }
        }, seconds * 1000.toLong(), seconds * 1000.toLong())
    }



    private fun createIntroModels(): List<IntroductionModel> {
        val introPage1 = IntroductionModel(
            R.drawable.ic_splash_logo,
            getString(R.string.app_name),
            getString(R.string.app_name)
        )
        val introPage2 = IntroductionModel(
            R.drawable.ic_splash_logo,
            getString(R.string.app_name),
            getString(R.string.app_name)
        )
        val introPage3 = IntroductionModel(
            R.drawable.ic_splash_logo,
            getString(R.string.app_name),
            getString(R.string.app_name)
        )

        return listOf(introPage1, introPage2, introPage3)
    }

}
