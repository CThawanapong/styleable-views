package com.github.cthawanapong.adapter

import android.text.Editable
import android.text.TextWatcher
import com.github.cthawanapong.view.StyleableClearableEditText

/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class StyleableTextWatcherAdapter(
        private val styleableClearableEditText: StyleableClearableEditText,
        private val listener: StyleableTextWatcherListener
) : TextWatcher {
    companion object {
        @JvmStatic
        private val TAG = StyleableTextWatcherAdapter::class.java.simpleName
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        listener.onTextChanged(styleableClearableEditText, s.toString())
    }

    interface StyleableTextWatcherListener {
        fun onTextChanged(view: StyleableClearableEditText, text: String)
    }
}