package com.github.cthawanapong.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.widget.Button
import com.github.cthawanapong.model.BundleSavedState
import com.github.cthawanapong.styleableviews.R
import kotlinx.android.synthetic.main.view_styleable_button.view.*


/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class StyleableButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        private val TAG = StyleableButton::class.java.simpleName

        @JvmStatic
        private val ARG_CHILD_STATES = "childrenStates"
        @JvmStatic
        private val ARG_BUTTON_TEXT = "ARG_BUTTON_TEXT"
        @JvmStatic
        private val ARG_TEXT_SIZE = "ARG_TEXT_SIZE"
        @JvmStatic
        private val ARG_BUTTON_BACKGROUND = "ARG_BUTTON_BACKGROUND"
        @JvmStatic
        private val ARG_TEXT_COLOR = "ARG_TEXT_COLOR"
        @JvmStatic
        private val ARG_DRAWABLE_PADDING = "ARG_DRAWABLE_PADDING"
        @JvmStatic
        private val ARG_HORIZONTAL_PADDING = "ARG_HORIZONTAL_PADDING"
        @JvmStatic
        private val ARG_VERTICAL_PADDING = "ARG_VERTICAL_PADDING"
        @JvmStatic
        private val ARG_BORDER_COLOR = "ARG_BORDER_COLOR"
        @JvmStatic
        private val ARG_CORNER_RADIUS = "ARG_CORNER_RADIUS"
        @JvmStatic
        private val ARG_ELEVATION = "ARG_ELEVATION"
    }

    //Data Members
    var buttonText: String = ""
        set(value) {
            field = value
            setInternalButtonText(field)
        }
    var buttonTextSize: Float = 0.toFloat()
        set(value) {
            field = value
            setInternalTextSize(field)
        }
    var buttonBackgroundColor: Int = 0
        set(value) {
            field = value
            setInternalButtonBackgroundColor(field)
        }
    var buttonTextColor: Int = 0
        set(value) {
            field = value
            setInternalButtonTextColor(field)
        }
    var buttonLeftIcon: Drawable? = null
        set(value) {
            field = value
            setInternalButtonLeftIcon(field)
        }
    var buttonDrawablePadding: Int = 0
        set(value) {
            field = value
            setInternalButtonDrawablePadding(field)
        }
    var buttonHorizontalPadding: Int = 0
        set(value) {
            field = value
            setInternalButtonPadding()
        }
    var buttonVerticalPadding: Int = 0
        set(value) {
            field = value
            setInternalButtonPadding()
        }
    var buttonBorderColor: Int = 0
        set(value) {
            field = value
            setInternalButtonBorderColor(field)
        }
    var buttonCornerRadius: Int = 0
        set(value) {
            field = value
            setInternalButtonCornerRadius(field)
        }
    var buttonElevation: Int = 0
        set(value) {
            field = value
            setInternalButtonElevation(field)
        }
    val styleableButton: Button
        get() = button

    init {
        initInflate()
        initInstance(context, attrs, defStyleAttr)
    }

    private fun initInflate() {
        View.inflate(context, R.layout.view_styleable_button, this)
    }

    private fun initInstance(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.StyleableButton)

            with(ta) {
                buttonText = ta.getString(R.styleable.StyleableButton_styleableButtonText)
                buttonTextSize = ta.getDimensionPixelSize(R.styleable.StyleableButton_styleableButtonTextSize, context.resources.getDimensionPixelSize(R.dimen.styleable_default_button_text_size)).toFloat()
                buttonBackgroundColor = ta.getColor(R.styleable.StyleableButton_styleableButtonBackgroundColor, Color.TRANSPARENT)
                buttonTextColor = ta.getColor(R.styleable.StyleableButton_styleableButtonTextColor, ContextCompat.getColor(context, R.color.default_text_color))
                val buttonLeftIconRes = ta.getResourceId(R.styleable.StyleableButton_styleableButtonLeftDrawable, 0)
                if (buttonLeftIconRes != 0) {
                    buttonLeftIcon = AppCompatResources.getDrawable(context, buttonLeftIconRes)
                }
                buttonDrawablePadding = ta.getDimensionPixelSize(R.styleable.StyleableButton_styleableButtonDrawablePadding, 0)
                buttonHorizontalPadding = ta.getDimensionPixelSize(R.styleable.StyleableButton_styleableButtonHorizontalPadding, 0)
                buttonVerticalPadding = ta.getDimensionPixelSize(R.styleable.StyleableButton_styleableButtonVerticalPadding, context.resources.getDimension(R.dimen.styleable_default_button_horizontal_padding).toInt())
                buttonBorderColor = ta.getColor(R.styleable.StyleableButton_styleableButtonBorderColor, Color.TRANSPARENT)
                buttonCornerRadius = ta.getDimensionPixelSize(R.styleable.StyleableButton_styleableButtonCornerRadius, 0)
                buttonElevation = ta.getDimensionPixelSize(R.styleable.StyleableButton_styleableButtonElevation, 0)
            }

            ta.recycle()
        }
    }

    private fun setInternalButtonText(buttonText: String) {
        button.text = buttonText
    }

    private fun setInternalTextSize(textSize: Float) {
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    private fun setInternalButtonBackgroundColor(backgroundColor: Int) {
        layoutText.setCardBackgroundColor(backgroundColor)
    }

    private fun setInternalButtonTextColor(color: Int) {
        button.setTextColor(color)
    }

    private fun setInternalButtonLeftIcon(leftIcon: Drawable?) {
        button.setCompoundDrawablesWithIntrinsicBounds(leftIcon, null, null, null)
    }

    private fun setInternalButtonDrawablePadding(padding: Int) {
        button.compoundDrawablePadding = padding
    }

    private fun setInternalButtonPadding() {
        button.setPadding(buttonHorizontalPadding, buttonVerticalPadding, buttonHorizontalPadding, buttonVerticalPadding)
    }

    private fun setInternalButtonBorderColor(color: Int) {
        setCardBackgroundColor(color)
    }

    private fun setInternalButtonCornerRadius(newRadius: Int) {
        layoutText.radius = newRadius.toFloat()
        radius = newRadius.toFloat()
    }

    private fun setInternalButtonElevation(elevation: Int) {
        cardElevation = elevation.toFloat()
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        //Save Child State
        var id: Int
        val childrenStates = Bundle()
        for (i in 0 until childCount) {
            id = getChildAt(i).id
            if (id != 0) {
                val childrenState = SparseArray<Parcelable>()
                getChildAt(i).saveHierarchyState(childrenState)
                childrenStates.putSparseParcelableArray(id.toString(), childrenState)
            }
        }

        val saveBundle = Bundle().apply {
            putBundle(ARG_CHILD_STATES, childrenStates)
            putString(ARG_BUTTON_TEXT, buttonText)
            putFloat(ARG_TEXT_SIZE, buttonTextSize)
            putInt(ARG_BUTTON_BACKGROUND, buttonBackgroundColor)
            putInt(ARG_TEXT_COLOR, buttonTextColor)
            putInt(ARG_DRAWABLE_PADDING, buttonDrawablePadding)
            putInt(ARG_HORIZONTAL_PADDING, buttonHorizontalPadding)
            putInt(ARG_VERTICAL_PADDING, buttonVerticalPadding)
            putInt(ARG_BORDER_COLOR, buttonBorderColor)
            putInt(ARG_CORNER_RADIUS, buttonCornerRadius)
            putInt(ARG_ELEVATION, buttonElevation)
        }

        //Save it to Parcelable
        return BundleSavedState(superState)
                .apply {
                    bundle = saveBundle
                }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as BundleSavedState
        super.onRestoreInstanceState(ss.superState)

        //Restore SparseArray

        val bundle = ss.bundle
        val childStates = bundle.getBundle(ARG_CHILD_STATES)

        //Restore Children's state
        var id: Int
        for (i in 0 until childCount) {
            id = getChildAt(i).id
            if (id != 0) {
                if (childStates.containsKey(id.toString())) {
                    val childrenState = childStates.getSparseParcelableArray<Parcelable>(id.toString())
                    getChildAt(i).restoreHierarchyState(childrenState)
                }
            }
        }

        //Restore State Here
        buttonText = bundle.getString(ARG_BUTTON_TEXT)
        buttonTextSize = bundle.getFloat(ARG_TEXT_SIZE)
        buttonBackgroundColor = bundle.getInt(ARG_BUTTON_BACKGROUND)
        buttonTextColor = bundle.getInt(ARG_TEXT_COLOR)
        buttonDrawablePadding = bundle.getInt(ARG_DRAWABLE_PADDING)
        buttonHorizontalPadding = bundle.getInt(ARG_HORIZONTAL_PADDING)
        buttonVerticalPadding = bundle.getInt(ARG_VERTICAL_PADDING)
        buttonBorderColor = bundle.getInt(ARG_BORDER_COLOR)
        buttonCornerRadius = bundle.getInt(ARG_CORNER_RADIUS)
        buttonElevation = bundle.getInt(ARG_ELEVATION)
    }
}