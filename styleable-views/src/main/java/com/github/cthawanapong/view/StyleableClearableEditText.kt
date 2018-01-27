package com.github.cthawanapong.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.cthawanapong.adapter.StyleableTextWatcherAdapter
import com.github.cthawanapong.styleableviews.R

/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class StyleableClearableEditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr), View.OnTouchListener, View.OnFocusChangeListener, StyleableTextWatcherAdapter.StyleableTextWatcherListener {
    companion object {
        @JvmStatic
        private val TAG = StyleableClearableEditText::class.java.simpleName
    }

    //Data Members
    private var mLocation = Location.RIGHT
    private var mDrawable: Drawable? = null
    private var mListener: Listener? = null
    private var mOnTouchListener: View.OnTouchListener? = null
    private var mOnFocusChangeListener: View.OnFocusChangeListener? = null

    init {
        init(context, attrs, defStyleAttr)
        initIcon()
        setClearIconVisible(false)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(StyleableTextWatcherAdapter(this, this))
    }

    private fun initIcon() {
        mDrawable = compoundDrawables[mLocation.idx]

        if (mDrawable == null) {
            mDrawable = ContextCompat.getDrawable(context, R.drawable.ic_cancel_text_input)
        }

        mDrawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            val min = paddingTop + it.intrinsicHeight + paddingBottom
            if (suggestedMinimumHeight < min) {
                minimumHeight = min
            }
        }
    }

    private fun getDisplayedDrawable(): Drawable? {
        return compoundDrawables[mLocation.idx]
    }

    private fun setClearIconVisible(visible: Boolean) {
        val cd = compoundDrawables
        val displayed = getDisplayedDrawable()
        val wasVisible = displayed != null
        if (visible != wasVisible) {
            val x = if (visible) mDrawable else null
            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, if (mLocation == Location.LEFT) x else cd[0], cd[1], if (mLocation == Location.RIGHT) x else cd[2],
                    cd[3])
        }
    }

    private enum class Location(val idx: Int) {
        LEFT(0),
        RIGHT(2)
    }

    interface Listener {
        fun didClearText()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (getDisplayedDrawable() != null) {
            event?.let { mEvent ->
                val x = mEvent.x.toInt()
                val y = mEvent.y.toInt()

                mDrawable?.let {
                    val left = if (mLocation == Location.LEFT) 0 else width - paddingRight - it.intrinsicWidth
                    val right = if (mLocation == Location.LEFT) paddingLeft + it.intrinsicWidth else width
                    val tappedX = x in left..right && y >= 0 && y <= bottom - top
                    if (tappedX) {
                        if (mEvent.action == MotionEvent.ACTION_UP) {
                            setText("")
                            mListener?.didClearText()
                        }

                        return true
                    }
                }
            }
        }

        return mOnTouchListener?.onTouch(v, event) ?: false
    }

    override fun onTextChanged(view: StyleableClearableEditText, text: String) {
        if (isFocused) {
            setClearIconVisible(text.isNotEmpty())
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(text.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }

        mOnFocusChangeListener?.onFocusChange(v, hasFocus)
    }
}