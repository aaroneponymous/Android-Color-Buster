package com.apaul9.colorbuster.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apaul9.colorbuster.ui.model.ColorData
import com.apaul9.colorbuster.ui.model.ColorVal

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private lateinit var colorList: ColorData

    private val _colors = MutableLiveData<List<ColorVal>>()
    var colors: LiveData<List<ColorVal>> = _colors

    init {
        val jsonString = app.assets.open("data.json").bufferedReader().use { it.readText() }
        colorList = ColorData(jsonString)
        _colors.value = colorList.colors

        Log.v("JSONSTRING: ", jsonString);
    }


}