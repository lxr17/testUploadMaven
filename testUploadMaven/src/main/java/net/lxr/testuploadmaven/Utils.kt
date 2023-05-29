package net.lxr.testuploadmaven

import android.content.Context
import android.util.TypedValue

object Utils {
    /**
     * dp转px
     */
    fun dp2px(context: Context, dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics
        )
    }
}