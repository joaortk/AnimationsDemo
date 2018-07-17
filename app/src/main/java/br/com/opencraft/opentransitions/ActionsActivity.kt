package br.com.opencraft.opentransitions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import kotlinx.android.synthetic.main.activity_actions.*

class ActionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actions)
    }

    override fun onEnterAnimationComplete() {
        circularReveal()
        animateViews()
    }

    private fun animateViews() {
        header.translationX = header.width.toFloat()
        header.animate().translationX(0f).setDuration(300L).start()
        body.translationY = body.height.toFloat()
        body.animate().translationY(0f).setDuration(300L).start()

        val springFechar = SpringAnimation(fechar, DynamicAnimation.TRANSLATION_Y)
        springFechar.setStartValue(-3000f)
        springFechar.animateToFinalPosition(0f)
        springFechar.setStartVelocity(0f)
        springFechar.start()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularReveal() {
        try {
            val cx = (circle_aux.left + circle_aux.right) / 2
            val cy = (circle_aux.top + circle_aux.bottom) / 2
            val finalRadius = Math.max(view_root.width, view_root.height)

            val anim = ViewAnimationUtils.createCircularReveal(view_root, cx, cy, 0f, finalRadius.toFloat())
            view_root.visibility = View.VISIBLE
            anim.duration = 300
            anim.interpolator = AccelerateInterpolator()
            anim.start()
        } catch (ex: Exception) {

        }
    }

    fun fechar(view: View?) {
        onBackPressed()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish()
        } else {
            circularHide()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularHide() {
        val cx = (circle_aux.left + circle_aux.right) / 2
        val cy = (circle_aux.top + circle_aux.bottom) / 2

        val finalRadius = (Math.max(view_root.getWidth(), view_root.getHeight()) * 1.1).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
                view_root, cx, cy, finalRadius, 0f)

        circularReveal.duration = 400
        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view_root.visibility = View.INVISIBLE
                finish()
            }
        })

        circularReveal.start()
    }
}
