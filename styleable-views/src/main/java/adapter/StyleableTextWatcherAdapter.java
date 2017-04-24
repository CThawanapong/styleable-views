package adapter;

import android.text.Editable;
import android.text.TextWatcher;

import view.StyleableEditText;

/**
 * Created by Chavit Thawanapong on 4/25/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class StyleableTextWatcherAdapter implements TextWatcher {
    private static final String TAG = StyleableTextWatcherAdapter.class.getSimpleName();

    private final StyleableEditText view;
    private final StyleableTextWatcherListener listener;

    public StyleableTextWatcherAdapter(StyleableEditText editText, StyleableTextWatcherListener listener) {
        this.view = editText;
        this.listener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listener.onTextChanged(view, s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface StyleableTextWatcherListener {
        void onTextChanged(StyleableEditText view, String text);
    }
}