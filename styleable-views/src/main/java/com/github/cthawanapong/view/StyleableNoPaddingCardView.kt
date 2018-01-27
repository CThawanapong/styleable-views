package com.github.cthawanapong.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import com.github.cthawanapong.model.BundleSavedState

/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class StyleableNoPaddingCardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        private val TAG = StyleableNoPaddingCardView::class.java.simpleName

        @JvmStatic
        private val ARG_CHILD_STATES = "childrenStates"
        @JvmStatic
        private val ARG_IS_FIRST_INIT = "ARG_IS_FIRST_INIT"
    }

    private var isFirstInit: Boolean = true

    init {
        preventCornerOverlap = false
        useCompatPadding = true
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        if (isFirstInit && params is ViewGroup.MarginLayoutParams) {
            isFirstInit = false
            params.bottomMargin -= paddingBottom - contentPaddingBottom
            params.leftMargin -= paddingLeft - contentPaddingLeft
            params.rightMargin -= paddingRight - contentPaddingRight
            params.topMargin -= paddingTop - contentPaddingTop
        }

        super.setLayoutParams(params)
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

        val saveBundle = Bundle()
        saveBundle.putBundle(ARG_CHILD_STATES, childrenStates)
        saveBundle.putBoolean(ARG_IS_FIRST_INIT, isFirstInit)

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
                if (childStates!!.containsKey(id.toString())) {
                    val childrenState = childStates.getSparseParcelableArray<Parcelable>(id.toString())
                    getChildAt(i).restoreHierarchyState(childrenState)
                }
            }
        }

        //Restore State Here
        isFirstInit = bundle.getBoolean(ARG_IS_FIRST_INIT)
    }
}