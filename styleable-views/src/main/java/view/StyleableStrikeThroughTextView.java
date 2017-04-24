package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.AttributeSet;
import android.util.Log;

import com.github.cthawanapong.styleableviews.R;

import manager.TypeFaceManager;

/**
 * Created by Chavit Thawanapong on 4/25/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class StyleableStrikeThroughTextView extends AppCompatTextView {
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

    //Data Members
    private boolean enableStrikeThrough;

    public StyleableStrikeThroughTextView(Context context) {
        super(context);

        if (isInEditMode())
            return;

        initInstance(context, null);
    }

    public StyleableStrikeThroughTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

        initInstance(context, attrs);
    }

    public StyleableStrikeThroughTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            return;

        initInstance(context, attrs);
    }

    private void initInstance(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StyleableStrikeThroughTextView);

            if (ta != null) {
                String fontAsset = ta.getString(R.styleable.StyleableStrikeThroughTextView_typeFaceAsset);
                enableStrikeThrough = ta.getBoolean(R.styleable.StyleableStrikeThroughTextView_enableStrikeThrough, true);

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
                        Log.d("FontText", String.format("Could not create a font from asset: %s", fontAsset));
                }
                ta.recycle();
            }
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (enableStrikeThrough) {
            setPaintFlags(getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            setPaintFlags(getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public void setEnableStrikeThrough(boolean enableStrikeThrough) {
        this.enableStrikeThrough = enableStrikeThrough;
    }
}
