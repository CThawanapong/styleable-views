package com.github.cthawanapong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.github.cthawanapong.manager.TypeFaceManager;
import com.github.cthawanapong.styleableviews.R;

/**
 * Created by Chavit Thawanapong on 4/25/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class StyleableEditText extends TextInputEditText {
    private static final String TAG = StyleableEditText.class.getSimpleName();

    public StyleableEditText(Context context) {
        super(context);

        if (isInEditMode())
            return;

        initInstance(context, null);
    }

    public StyleableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

        initInstance(context, attrs);
    }

    public StyleableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            return;

        initInstance(context, attrs);
    }

    private void initInstance(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StyleableEditText);

            if (ta != null) {
                String fontAsset = ta.getString(R.styleable.StyleableEditText_typeFaceAsset);
                if (!TextUtils.isEmpty(fontAsset)) {
                    fontAsset = "fonts/" + fontAsset;
                    Typeface tf = TypeFaceManager.getInstance().getFont(fontAsset);
                    int style = Typeface.NORMAL;
                    float size = getTextSize();

                    if (getTypeface() != null)
                        style = getTypeface().getStyle();

                    if (tf != null)
                        setTypeface(tf, style);
                    else
                        Log.d(TAG, String.format("Could not create a font from asset: %s", fontAsset));
                }

                ta.recycle();
            }
        }
    }
}
