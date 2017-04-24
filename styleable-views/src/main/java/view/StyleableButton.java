package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.cthawanapong.styleableviews.R;

import model.BundleSavedState;

/**
 * Created by Chavit Thawanapong on 4/25/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class StyleableButton extends FrameLayout implements View.OnClickListener {
    private static final String TAG = StyleableButton.class.getSimpleName();

    private static final String ARG_CHILD_STATES = "childrenStates";
    private static final String ARG_BUTTON_TEXT = "ARG_BUTTON_TEXT";
    private static final String ARG_BUTTON_PADDING = "ARG_BUTTON_PADDING";
    private static final String ARG_TEXT_SIZE = "ARG_TEXT_SIZE";
    private static final String ARG_BUTTON_BACKGROUND = "ARG_BUTTON_BACKGROUND";
    private static final String ARG_TEXT_COLOR = "ARG_TEXT_COLOR";
    private static final String ARG_DRAWABLE_PADDING = "ARG_DRAWABLE_PADDING";
    private static final String ARG_HORIZONTAL_PADDING = "ARG_HORIZONTAL_PADDING";

    //View Members
    private ViewGroup mLayoutButton;
    private ViewGroup mLayoutText;
    private ViewGroup mLayoutWrapper;
    private StyleableTextView mTextView;

    //Data Members
    private String mButtonText;
    private int mPadding;
    private float mTextSize;
    private int mBackgroundColor;
    private int mTextColor;
    private Drawable mLeftIcon;
    private int mButtonDrawablePadding;
    private int mButtonHorizontalPadding;
    private OnClickListener mListener;

    public StyleableButton(Context context) {
        super(context);
        initInflate();
        initInstance(context, null);
    }

    public StyleableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance(context, attrs);
    }

    public StyleableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StyleableButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance(context, attrs);
    }

    private void initInflate() {
        //Inflate Layout
        inflate(getContext(), R.layout.view_button, this);
    }

    private void initInstance(Context context, AttributeSet attrs) {
        mLayoutButton = (ViewGroup) findViewById(R.id.layout_button);
        mLayoutText = (ViewGroup) findViewById(R.id.layout_text);
        mLayoutWrapper = (ViewGroup) findViewById(R.id.layout_wrapper);
        mTextView = (StyleableTextView) findViewById(R.id.txt_view);

        mButtonText = "";
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StyleableButton, 0, 0);
            try {
                mButtonText = ta.getString(R.styleable.StyleableButton_buttonText);
                mPadding = ta.getDimensionPixelSize(R.styleable.StyleableButton_buttonPadding, 0);
                mTextSize = ta.getDimension(R.styleable.StyleableButton_buttonTextSize, getContext().getResources().getDimension(R.dimen.default_button_text_size));
                mBackgroundColor = ta.getColor(R.styleable.StyleableButton_buttonBackgroundColor, Color.WHITE);
                mTextColor = ta.getColor(R.styleable.StyleableButton_buttonTextColor, ContextCompat.getColor(getContext(), R.color.default_text_color));
                mLeftIcon = ta.getDrawable(R.styleable.StyleableButton_buttonLeftDrawable);
                mButtonDrawablePadding = ta.getDimensionPixelSize(R.styleable.StyleableButton_buttonDrawablePadding, 0);
                mButtonHorizontalPadding = ta.getDimensionPixelSize(R.styleable.StyleableButton_buttonHorizontalPadding, 0);
            } finally {
                ta.recycle();
            }
        }

        mLayoutButton.setPadding(mPadding, mPadding, mPadding, mPadding);
        mLayoutText.setBackgroundColor(mBackgroundColor);
        mTextView.setTextColor(mTextColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(mLeftIcon, null, null, null);
        mTextView.setCompoundDrawablePadding(mButtonDrawablePadding);
        mLayoutWrapper.setPadding(mButtonHorizontalPadding, 0, mButtonHorizontalPadding, 0);
        mLayoutWrapper.setOnClickListener(this);
        setDisplayButtonText();
    }

    private void setDisplayButtonText() {
        mTextView.setText(mButtonText);
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
        bundle.putString(ARG_BUTTON_TEXT, mButtonText);
        bundle.putInt(ARG_BUTTON_PADDING, mPadding);
        bundle.putFloat(ARG_TEXT_SIZE, mTextSize);
        bundle.putInt(ARG_BUTTON_BACKGROUND, mBackgroundColor);
        bundle.putInt(ARG_TEXT_COLOR, mTextColor);
        bundle.putInt(ARG_DRAWABLE_PADDING, mButtonDrawablePadding);
        bundle.putInt(ARG_HORIZONTAL_PADDING, mButtonHorizontalPadding);

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
        this.mButtonText = bundle.getString(ARG_BUTTON_TEXT);
        this.mPadding = bundle.getInt(ARG_BUTTON_PADDING);
        this.mTextSize = bundle.getFloat(ARG_TEXT_SIZE);
        this.mBackgroundColor = bundle.getInt(ARG_BUTTON_BACKGROUND);
        this.mTextColor = bundle.getInt(ARG_TEXT_COLOR);
        this.mButtonDrawablePadding = bundle.getInt(ARG_DRAWABLE_PADDING);
        this.mButtonHorizontalPadding = bundle.getInt(ARG_HORIZONTAL_PADDING);
    }

    public void setListener(OnClickListener mListener) {
        this.mListener = mListener;
    }

    public void setButtonText(String mButtonText) {
        this.mButtonText = mButtonText;

        setDisplayButtonText();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null && v instanceof ViewGroup) {
            mListener.onClick((ViewGroup) v);
        }
    }

    public interface OnClickListener {
        void onClick(ViewGroup viewGroup);
    }
}
