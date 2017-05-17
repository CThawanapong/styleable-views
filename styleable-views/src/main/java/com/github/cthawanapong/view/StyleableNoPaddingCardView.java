package com.github.cthawanapong.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.github.cthawanapong.model.BundleSavedState;

/**
 * Created by Chavit Thawanapong on 5/17/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class StyleableNoPaddingCardView extends CardView {
    private static final String ARG_IS_FIRST_INIT = "ARG_IS_FIRST_INIT";
    private static final String ARG_CHILD_STATES = "childrenStates";
    private boolean isFirstInit = true;

    public StyleableNoPaddingCardView(Context context) {
        super(context);
        init();
    }

    public StyleableNoPaddingCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StyleableNoPaddingCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Optional: Prevent pre-L from adding inner card padding
        setPreventCornerOverlap(false);
        // Optional: make Lollipop and above add shadow padding to match pre-L padding
        setUseCompatPadding(true);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        // FIX shadow padding
        if (isFirstInit && params instanceof ViewGroup.MarginLayoutParams) {
            isFirstInit = false;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) params;
            layoutParams.bottomMargin -= (getPaddingBottom() - getContentPaddingBottom());
            layoutParams.leftMargin -= (getPaddingLeft() - getContentPaddingLeft());
            layoutParams.rightMargin -= (getPaddingRight() - getContentPaddingRight());
            layoutParams.topMargin -= (getPaddingTop() - getContentPaddingTop());
        }

        super.setLayoutParams(params);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        //Save Child State
        int id;
        Bundle childrenStates = new Bundle();
        for (int i = 0; i < getChildCount(); i++) {
            id = getChildAt(i).getId();
            if (id != 0) {
                SparseArray childrenState = new SparseArray();
                getChildAt(i).saveHierarchyState(childrenState);
                childrenStates.putSparseParcelableArray(String.valueOf(id), childrenState);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putBundle(ARG_CHILD_STATES, childrenStates);
        bundle.putBoolean(ARG_IS_FIRST_INIT, isFirstInit);

        //Save it to Parcelable
        BundleSavedState ss = new BundleSavedState(superState);
        ss.setBundle(bundle);
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        //Restore SparseArray

        Bundle bundle = ss.getBundle();
        Bundle childStates = bundle.getBundle(ARG_CHILD_STATES);

        //Restore Children's state
        int id;
        for (int i = 0; i < getChildCount(); i++) {
            id = getChildAt(i).getId();
            if (id != 0) {
                if (childStates.containsKey(String.valueOf(id))) {
                    SparseArray childrenState =
                            childStates.getSparseParcelableArray(String.valueOf(id));
                    getChildAt(i).restoreHierarchyState(childrenState);
                }
            }
        }

        //Restore State Here
        isFirstInit = bundle.getBoolean(ARG_IS_FIRST_INIT);
    }
}