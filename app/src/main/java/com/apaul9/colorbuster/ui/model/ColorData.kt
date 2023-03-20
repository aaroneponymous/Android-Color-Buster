package com.apaul9.colorbuster.ui.model

import android.util.Log
import com.google.gson.Gson

private const val TAG = "ColorData"

data class Color(val colorID: Int, val hexString: String, val colorName: String)

class ColorList: ArrayList<Color>()

class ColorData(jsonString: String) {

    var colors = ArrayList<ColorData>()
    init {
        val gson = Gson()
        colors = gson.fromJson(jsonString, ColorData::class.java)
        Log.d(TAG, colors.toString())
    }
}
