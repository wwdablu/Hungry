package com.soumya.wwdablu.hungry.customview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import kotlin.math.abs


class RestaurantCard : CardView {

    private val MIN_DISTACE: Int = 50

    private var mStartX: Float = 0f
    private var mStartY: Float = 0f
    private var mMoved: Boolean = false

    constructor(context: Context) : super(context) {
        //
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        //
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        //
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event == null) {
            return true
        }

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {

                mStartX = x
                mStartY = y
                mMoved = false

                animateScaling(0.95f, 0.95f)
            }

            MotionEvent.ACTION_UP -> {
                animateScaling(1f, 1f)

                if (abs(x - mStartX) <= MIN_DISTACE && abs(y - mStartY) <= MIN_DISTACE) {
                    performClick()
                }
            }

            MotionEvent.ACTION_MOVE -> {

                if (!mMoved) {
                    animateScaling(1f, 1f)
                }
            }
        }

        return true
    }

    private fun animateScaling(x: Float, y: Float) {
        val scaleX = ObjectAnimator.ofFloat(this, "scaleX", x)
        val scaleY = ObjectAnimator.ofFloat(this, "scaleY", y)
        scaleX.duration = 200
        scaleY.duration = 200

        val scaleDown = AnimatorSet()
        scaleDown.play(scaleX).with(scaleY)
        scaleDown.start()
    }
}