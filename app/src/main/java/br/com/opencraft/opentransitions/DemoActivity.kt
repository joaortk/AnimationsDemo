package br.com.opencraft.opentransitions

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.opencraft.opentransitions.animation.AnimatorListenerWrapper
import br.com.opencraft.opentransitions.animation.HomeToDashboardAnimation
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

    var created = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
    }

    override fun onResume() {
        super.onResume()
        if (!created) {
            HomeToDashboardAnimation.reverse(0.7f,
                    arrayOf(main_card),
                    0.2f,
                    0.3f,
                    arrayOf(main_card),
                    arrayOf(content1, content2, agibank),
                    arrayOf(bottom),
                    1000L,
                    object : AnimatorListenerWrapper() {
                        override fun onAnimationEnd(animation: Animator?) {
                            created = true
                        }
                    })
        }
    }

    fun showActions(view: View?) {
        circle_aux.visibility = View.VISIBLE
        val detailIntent = Intent(this, ActionsActivity::class.java)
        startActivity(detailIntent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, circle_aux, "circle_right").toBundle())
    }

    fun showDetail(view: View?) {
        if (created) {
            created = false
            HomeToDashboardAnimation.animate(0.7f,
                    arrayOf(main_card),
                    0.2f,
                    0.3f,
                    arrayOf(main_card),
                    arrayOf(content1, content2, agibank),
                    arrayOf(bottom),
                    1000L,
                    object : AnimatorListenerWrapper() {
                        override fun onAnimationEnd(animation: Animator?) {
                            startActivityWithTransition()
                        }
                    })
        }
    }

    private fun startActivityWithTransition() {
        val detailIntent = Intent(this, DetailActivity::class.java)
        startActivity(detailIntent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, main_card, "card").toBundle())
    }
}
