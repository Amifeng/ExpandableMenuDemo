package com.finn.expandablemenu

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.PointF
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.BounceInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var menuViews = mutableListOf<FloatingActionButton>()
    private var isMenuOpen = false
    companion object {
        private const val DISTANCE: Int = 70
        private const val ANIMATION_ALPHA: Long = 600
        private const val ANIMATION_TRANSLATION: Long = 600
        private const val ANIMATION_ROTATION: Long = 800
    }
    private val childMenuPoints by lazy {
        mutableListOf(
            PointF(DISTANCE.toDp() * -1, 0F),
            PointF(DISTANCE.toDp() * -2, 0F),
            PointF(DISTANCE.toDp() * -3, 0F),
            PointF(DISTANCE.toDp() * -4, 0F)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        menuViews.add(main_fab_minus)
        menuViews.add(main_fab_plus)
        menuViews.add(main_fab_return)
        menuViews.add(main_fab_transfer)

        main_layout_bg.setOnClickListener {
            showCloseAnim()
        }

        main_fab.setOnClickListener {
            if (!isMenuOpen) {
                showOpenAnim()
            } else {
                showCloseAnim()
            }
        }
    }

    private fun showOpenAnim() {
        main_fab.isEnabled = false
        main_layout_bg.show()
        //for循环来开始小图标的出现动画
        for (i in menuViews.indices) {
            menuViews[i].show()
            val set = AnimatorSet()
            with(set) {
                playTogether(
                    ObjectAnimator.ofFloat(menuViews[i], "translationX", 0F, childMenuPoints[i].x),
                    ObjectAnimator.ofFloat(menuViews[i], "translationY", 0F, childMenuPoints[i].y),
                    ObjectAnimator.ofFloat(menuViews[i], "alpha", 0F, 1F).setDuration(ANIMATION_ALPHA)
                )
                interpolator = BounceInterpolator()//添加回弹动画
                duration = ANIMATION_TRANSLATION
                start()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        //菜单状态置开启
                        isMenuOpen = true
                        main_fab.isEnabled = true
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
            }
        }

        //转动图标本身45°
        val rotate: ObjectAnimator =
            ObjectAnimator.ofFloat(main_fab, "rotation", 0F, 90F)
                .setDuration(ANIMATION_ROTATION)
        rotate.interpolator = BounceInterpolator()//添加回弹动画
        rotate.start()
    }


    private fun showCloseAnim() {
        main_fab.isEnabled = false
        main_layout_bg.hide()
        //for循环来开始小图标的出现动画
        for (i in menuViews.indices) {
            val set = AnimatorSet()
            with(set){
                playTogether(
                    ObjectAnimator.ofFloat(menuViews[i], "translationX", childMenuPoints[i].x, 0F),
                    ObjectAnimator.ofFloat(menuViews[i], "translationY", childMenuPoints[i].y, 0F),
                    ObjectAnimator.ofFloat(menuViews[i], "alpha", 1f, 0f).setDuration(ANIMATION_ALPHA)
                )
                interpolator = BounceInterpolator() //添加回弹动画
                duration = ANIMATION_TRANSLATION
                start()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        menuViews[i].hide()
                        //菜单状态置关闭
                        isMenuOpen = false
                        main_fab.isEnabled = true
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }
                })
            }
        }

        //转动标本身45°
        val rotate: ObjectAnimator = ObjectAnimator.ofFloat(main_fab, "rotation", 0F, 90F)
            .setDuration(ANIMATION_ROTATION)
        rotate.interpolator = BounceInterpolator()
        rotate.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}