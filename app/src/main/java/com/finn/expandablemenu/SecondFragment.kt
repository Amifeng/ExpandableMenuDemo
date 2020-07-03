package com.finn.expandablemenu

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.PointF
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var menuViews = mutableListOf<FloatingActionButton>()

    private var isMenuOpen = false

    companion object {
        private const val DISTANCE: Int = 100
        private const val ANIMATION_ALPHA: Long = 600
        private const val ANIMATION_TRANSLATION: Long = 600
        private const val ANIMATION_ROTATION: Long = 800
    }

    private val childMenuPoints by lazy {
        mutableListOf(
            PointF(0F, DISTANCE.toDp() * -1),
            PointF(DISTANCE.toDp() * -3/4, DISTANCE.toDp() * 1 / 2),
            PointF(DISTANCE.toDp() * -3/4, DISTANCE.toDp() * -1 / 2),
            PointF(0F, DISTANCE.toDp())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuViews.add(fab_minus)
        menuViews.add(fab_plus)
        menuViews.add(fab_return)
        menuViews.add(fab_transfer)

        //设置弹出菜单后，显示透明度背景
        layout_bg.setBackgroundColorRes(R.color.color_on_black_50)

        button_back.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        layout_bg.setOnClickListener {
            showCloseAnim()
        }

        fab_publish.setOnClickListener {
            if (!isMenuOpen) {
                showOpenAnim()
            } else {
                showCloseAnim()
            }
        }
    }

    private fun showOpenAnim() {
        with(fab_publish) {
            isEnabled = false
            setImageResource(R.drawable.ic_close)
            imageTintMode = PorterDuff.Mode.SRC_ATOP
            imageTintList = resources.getColor(R.color.colorBlack).toColorState()
            backgroundTintMode = PorterDuff.Mode.SRC_ATOP
            backgroundTintList = resources.getColor(R.color.colorWhite).toColorState()
            rippleColor = resources.getColor(R.color.color_on_primary_50)
        }
        layout_bg.show()
        //for循环来开始小图标的出现动画
        for (i in menuViews.indices) {
            menuViews[i].show()
            val set = AnimatorSet()
            set.playTogether(
                ObjectAnimator.ofFloat(
                    menuViews[i],
                    "translationX",
                    0F,
                    childMenuPoints[i].x
                ),
                ObjectAnimator.ofFloat(
                    menuViews[i],
                    "translationY",
                    0F,
                    childMenuPoints[i].y
                )
                , ObjectAnimator.ofFloat(menuViews[i], "alpha", 0F, 1F).setDuration(ANIMATION_ALPHA)
            )
            set.interpolator = BounceInterpolator()//添加回弹动画
            set.duration = ANIMATION_TRANSLATION
            set.start()
            set.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    //菜单状态置开启
                    isMenuOpen = true
                    fab_publish.isEnabled = true
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }

        //转动加号大图标本身45°
        val rotate: ObjectAnimator =
            ObjectAnimator.ofFloat(fab_publish, "rotation", 0F, 90F).setDuration(ANIMATION_ROTATION)
        rotate.interpolator = BounceInterpolator()//添加回弹动画
        rotate.start()
    }


    private fun showCloseAnim() {
        with(fab_publish) {
            isEnabled = false
            setImageResource(R.drawable.ic_menu)
            imageTintMode = PorterDuff.Mode.SRC_ATOP
            imageTintList = resources.getColor(R.color.colorWhite).toColorState()
            backgroundTintMode = PorterDuff.Mode.SRC_ATOP
            backgroundTintList = resources.getColor(R.color.colorPrimary).toColorState()
            rippleColor = resources.getColor(R.color.colorWhite)
        }
        layout_bg.hide()
        //for循环来开始小图标的出现动画
        for (i in menuViews.indices) {
            val set = AnimatorSet()
            set.playTogether(
                ObjectAnimator.ofFloat(
                    menuViews[i],
                    "translationX",
                    childMenuPoints[i].x,
                    0F
                ),
                ObjectAnimator.ofFloat(
                    menuViews[i],
                    "translationY",
                    childMenuPoints[i].y,
                    0F
                )
                , ObjectAnimator.ofFloat(menuViews[i], "alpha", 1f, 0f).setDuration(ANIMATION_ALPHA)
            )
            set.interpolator = BounceInterpolator() //添加回弹动画
            set.duration = ANIMATION_TRANSLATION
            set.start()
            set.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    menuViews[i].hide()
                    //菜单状态置关闭
                    isMenuOpen = false
                    fab_publish.isEnabled = true
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }

        //转动加号大图标本身45°
        val rotate: ObjectAnimator =
            ObjectAnimator.ofFloat(fab_publish, "rotation", 0F, 90F).setDuration(ANIMATION_ROTATION)
        rotate.interpolator = BounceInterpolator()
        rotate.start()
    }

}
