package com.soumya.wwdablu.hungry.customview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class RestaurantCard : CardView {

    constructor(context: Context) : super(context) {
        //
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        //
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        //
    }

    fun shrinkCard() {
        animateScaling(0.95f, 0.95f)
    }

    fun expandCard() {
        animateScaling(1f, 1f)
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