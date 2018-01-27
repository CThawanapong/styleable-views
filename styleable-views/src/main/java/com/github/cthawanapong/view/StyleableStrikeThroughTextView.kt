package com.github.cthawanapong.view

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.AppCompatTextView
import android.text.style.StrikethroughSpan
import android.util.AttributeSet
import com.github.cthawanapong.styleableviews.R

/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class StyleableStrikeThroughTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        private val TAG = StyleableStrikeThroughTextView::class.java.simpleName

        @JvmStatic
        private val STRIKE_THROUGH_SPAN by lazy { StrikethroughSpan() }
    }

    var enableStrikeThrough: Boolean = false
        set(value) {
            field = value
            setInternalEnableStrikeThrough(field)
        }

    init {
        if (!isInEditMode) {
            initInstance(context, attrs, defStyleAttr)
        }
    }

    private fun initInstance(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.StyleableStrikeThroughTextView)

            with(ta) {
                enableStrikeThrough = ta.getBoolean(R.styleable.StyleableStrikeThroughTextView_enableStrikeThrough, false)
            }

            ta.recycle()
        }
    }

    private fun setInternalEnableStrikeThrough(enable: Boolean) {
        paintFlags = if (enable) {
            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}