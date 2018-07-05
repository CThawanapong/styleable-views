package com.github.cthawanapong.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.v4.view.AbsSavedState

/**
 * Created by CThawanapong on 27/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class BundleSavedState : AbsSavedState {
    companion object {
        @JvmField
        @NonNull
        val CREATOR: Parcelable.Creator<BundleSavedState> = object : Parcelable.ClassLoaderCreator<BundleSavedState> {
            override fun createFromParcel(source: Parcel, loader: ClassLoader?): BundleSavedState {
                return BundleSavedState(source, loader)
            }

            override fun createFromParcel(source: Parcel): BundleSavedState {
                return BundleSavedState(source, null)
            }

            override fun newArray(size: Int): Array<BundleSavedState?> {
                return arrayOfNulls(size)
            }

        }
    }

    var bundle: Bundle = Bundle()

    constructor(source: Parcel, classLoader: ClassLoader?) : super(source, classLoader) {
        bundle = source.readBundle(classLoader) ?: Bundle()
    }

    constructor(superState: Parcelable) : super(superState)

    override fun writeToParcel(out: Parcel?, flags: Int) {
        super.writeToParcel(out, flags)
        out?.writeBundle(bundle)
    }
}