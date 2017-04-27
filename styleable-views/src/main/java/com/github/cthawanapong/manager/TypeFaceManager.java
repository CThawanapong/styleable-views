package com.github.cthawanapong.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chavit Thawanapong on 4/25/2017 AD.
 * Email: c.thawanapong@gmail.com
 */

public class TypeFaceManager {
    private static final String TAG = TypeFaceManager.class.getSimpleName();

    private static TypeFaceManager instance;
    private Context mContext;
    private AssetManager mAssetManager;
    private Map<String, Typeface> mFontList;
    private Map<String, String> mDefaultFontList;

    private TypeFaceManager() {
    }

    public static TypeFaceManager getInstance() {
        if (instance == null)
            instance = new TypeFaceManager();
        return instance;
    }

    public void init(Context applicationContext) {
        mContext = applicationContext;
        this.mAssetManager = mContext.getAssets();
        mFontList = new HashMap<>();
        mDefaultFontList = new HashMap<>();
    }

    public Typeface getFont(String asset) {
        if (mFontList.containsKey(asset))
            return mFontList.get(asset);

        Typeface font = null;

        try {
            font = Typeface.createFromAsset(mAssetManager, asset);
            mFontList.put(asset, font);
        } catch (Exception e) {
            Log.e(TAG, "getFont: ", e);
        }

        if (font == null) {
            try {
                String fixedAsset = fixAssetFilename(asset);
                font = Typeface.createFromAsset(mAssetManager, fixedAsset);
                mFontList.put(asset, font);
                mFontList.put(fixedAsset, font);
            } catch (Exception e) {
                Log.e(TAG, "getFont: ", e);
            }
        }

        return font;
    }

    private String fixAssetFilename(String asset) {
        // Empty font filename?
        // Just return it. We can't help.
        if (TextUtils.isEmpty(asset))
            return asset;

        // Make sure that the font ends in '.ttf' or '.ttc'
        if ((!asset.endsWith(".ttf")) && (!asset.endsWith(".ttc")))
            asset = String.format("%s.ttf", asset);

        return asset;
    }

    public void addDefaultFontForLocale(String localeCode, String fontName) {
        mDefaultFontList.put(localeCode, fontName);
    }

    public boolean isDefaultFontMapAvailable() {
        return mDefaultFontList != null && !mDefaultFontList.isEmpty();
    }

    public boolean isFontDefaultFontAvailable(String language) {
        return mDefaultFontList.containsKey(language);
    }

    public String getDefaultFontName(String language) {
        return mDefaultFontList.get(language);
    }
}
