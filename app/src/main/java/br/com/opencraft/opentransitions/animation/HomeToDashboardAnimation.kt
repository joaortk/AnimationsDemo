package br.com.opencraft.opentransitions.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View

object HomeToDashboardAnimation {
    fun animate(scale: Float,
                viewsToScale: Array<View>,
                alphaFrom: Float,
                alphaTo: Float,
                viewsToAnimAlpha: Array<View>,
                viewsToFade: Array<View>,
                viewsToTranslateDown: Array<View>,
                duration: Long, listener: AnimatorListenerWrapper) {

        val animators = ArrayList<ValueAnimator>()

        //SCALE
        val scaleXH = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, scale)
        val scaleYH = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, scale)
        viewsToScale.forEach {
            animators.add(ObjectAnimator.ofPropertyValuesHolder(it, scaleXH, scaleYH).apply { this.duration = duration })
        }

        //FADE
        val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)
        viewsToFade.forEach {
            animators.add(ObjectAnimator.ofPropertyValuesHolder(it, fade).apply { this.duration = duration })
        }

        //ALPHA
        val alpha = ValueAnimator.ofFloat(alphaFrom, alphaTo).apply {
            this.duration = duration
            addUpdateListener { animation ->
                viewsToAnimAlpha.forEach {
                    it.background = getColorDrawable(it.background as ColorDrawable, animation.animatedValue as Float)
                }
            }
        }
        animators.add(alpha)

        //TRANSLATE DOWN
        viewsToTranslateDown.forEach {
            animators.add(ObjectAnimator.ofFloat(it, View.TRANSLATION_Y, it.translationY, (it.parent as View).height - it.y).apply { this.duration = duration })
        }

        val animSet = AnimatorSet()
        animSet.playTogether(animators.toList())
        animSet.addListener(listener)
        animSet.start()

    }

    fun reverse(scale: Float,
                viewsToScale: Array<View>,
                alphaFrom: Float,
                alphaTo: Float,
                viewsToAnimAlpha: Array<View>,
                viewsToFade: Array<View>,
                viewsToTranslateDown: Array<View>,
                duration: Long, listener: AnimatorListenerWrapper) {

        val animators = ArrayList<ValueAnimator>()

        //SCALE
        val scaleXH = PropertyValuesHolder.ofFloat(View.SCALE_X, scale, 1f)
        val scaleYH = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale, 1f)
        viewsToScale.forEach {
            animators.add(ObjectAnimator.ofPropertyValuesHolder(it, scaleXH, scaleYH).apply { this.duration = duration })
        }

        //FADE
        val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
        viewsToFade.forEach {
            animators.add(ObjectAnimator.ofPropertyValuesHolder(it, fade).apply { this.duration = duration })
        }

        //ALPHA
        val alpha = ValueAnimator.ofFloat(alphaTo, alphaFrom).apply {
            this.duration = duration
            addUpdateListener { animation ->
                viewsToAnimAlpha.forEach {
                    it.background = getColorDrawable(it.background as ColorDrawable, animation.animatedValue as Float)
                }
            }
        }
        animators.add(alpha)

        //TRANSLATE DOWN
        viewsToTranslateDown.forEach {
            animators.add(ObjectAnimator.ofFloat(it, View.TRANSLATION_Y, it.translationY, 0f).apply { this.duration = duration })
        }

        val animSet = AnimatorSet()
        animSet.playTogether(animators.toList())
        animSet.addListener(listener)
        animSet.start()

    }

    private fun getColorDrawable(colorDrawable: ColorDrawable, animatedValue: Float): ColorDrawable {
        val alpha = Math.round(255 * animatedValue)
        val red = Color.red(colorDrawable.color)
        val green = Color.green(colorDrawable.color)
        val blue = Color.blue(colorDrawable.color)
        return ColorDrawable(Color.argb(alpha, red, green, blue))
    }
}