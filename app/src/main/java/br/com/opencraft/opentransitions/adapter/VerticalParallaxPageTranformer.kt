package br.com.opencraft.opentransitions.adapter

import android.support.v4.view.ViewPager
import android.view.View
import br.com.opencraft.opentransitions.R

class VerticalParallaxPageTranformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = page.width * -position

        when {
            position < -1 -> // This page is way off-screen to the left (or up)
                page.alpha = 0f
            position <= 1 -> { //Next Page
                page.alpha = 1f

                // set Y position to swipe in from top
                val yPosition = position * page.height
                page.translationY = yPosition
                setYParallaxPositionAndScale(page, position)
            }
            else -> {
                val yPosition = position * page.height
                page.translationY = yPosition
                page.findViewById<View>(R.id.highlight)?.let {
                    it.translationY = -it.top.toFloat()
                    it.scaleX = 0.7f
                    it.scaleY = 0.7f
                    it.alpha = 0.3f
                }

            }
        }
    }

    private fun setYParallaxPositionAndScale(page: View, position: Float) {
        val highlight = page.findViewById<View>(R.id.highlight)
        highlight?.let {
            val scale = Math.min(0.7f + (1 - position) * 0.3f, 1f)
            it.scaleX = scale
            it.scaleY = scale
            it.alpha = 1.3f - position
            if (position >= 0)
                it.translationY = position * -it.top.toFloat()
        }

    }
}