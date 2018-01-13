package io.bkraszewski.reactivetictactoe.common

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.RelativeLayout
import android.widget.TextView

class SquaredLayout(context: Context) : RelativeLayout(context){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
