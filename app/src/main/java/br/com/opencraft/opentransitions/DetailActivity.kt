package br.com.opencraft.opentransitions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.opencraft.opentransitions.adapter.MyPagerAdapter
import br.com.opencraft.opentransitions.adapter.VerticalParallaxPageTranformer
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        pager.adapter = MyPagerAdapter(this)
        pager.offscreenPageLimit = 2
        pager.overScrollMode
        pager.setPageTransformer(true, VerticalParallaxPageTranformer())

    }

}
