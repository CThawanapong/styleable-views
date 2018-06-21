package com.github.cthawanapong.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.view.View

/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class BundleSavedState : View.BaseSavedState {
    companion object {
        @JvmField
        @NonNull
        val CREATOR: Parcelable.Creator<Any> = object : Parcelable.Creator<Any> {
            override fun createFromParcel(source: Parcel?): Any {
                return BundleSavedState(source)
            }

            override fun newArray(size: Int): Array<Any> {
                return arrayOf(size)
            }

        }
    }

    lateinit var bundle: Bundle

    constructor(source: Parcel?) : super(source) {
        bundle = source?.readBundle(Bundle::class.java.classLoader) ?: Bundle()
    }

    constructor(superState: Parcelable) : super(superState)

    override fun writeToParcel(out: Parcel?, flags: Int) {
        super.writeToParcel(out, flags)
        out?.writeBundle(bundle)
    }
}