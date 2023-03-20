package com.apaul9.colorbuster.ui.model

import android.util.Log
import com.google.gson.Gson

private const val TAG = "Color Data:"

data class ColorValues(val colorID: Int, val hexString: String, val colorName: String)

class ColorList: ArrayList<ColorValues>()

class ColorData(jsonString: String) {

    var colors = ArrayList<ColorValues>()
    init {
        val gson = Gson()
        colors = gson.fromJson(jsonString, ColorList::class.java)
        Log.d(TAG, colors.toString())
    }
}
