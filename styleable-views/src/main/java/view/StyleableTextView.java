package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.github.cthawanapong.styleableviews.R;

import manager.TypeFaceManager;

/**
 * Created by Chavit Thawanapong on 4/25/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class StyleableTextView extends AppCompatTextView {
    private static final String TAG = StyleableTextView.class.getSimpleName();

    public StyleableTextView(Context context) {
        super(context);

        if (isInEditMode())
            return;

        initInstance(context, null);
    }

    public StyleableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

        initInstance(context, attrs);
    }

    public StyleableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            return;

        initInstance(context, attrs);
    }

    private void initInstance(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StyleableTextView);

            if (ta != null) {
                String fontAsset = ta.getString(R.styleable.StyleableEditText_typeFaceAsset);

                if (!TextUtils.isEmpty(fontAsset)) {
                    fontAsset = "fonts/" + fontAsset;

                    Typeface tf = TypeFaceManager.getInstance().getFont(fontAsset);
                    int style = Typeface.NORMAL;

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
