package com.aafiyahtech.ventilator.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.aafiyahtech.ventilator.utils.AppDataProvider

class MainViewModel: ViewModel() {

    var appDataProvider: AppDataProvider? = null

    fun init(activity: AppCompatActivity) {
        appDataProvider = AppDataProvider.getInstance(activity.applicationContext)
    }



}