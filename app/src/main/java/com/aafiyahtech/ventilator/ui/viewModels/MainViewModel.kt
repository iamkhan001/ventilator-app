package com.aafiyahtech.ventilator.ui.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aafiyahtech.ventilator.models.*
import com.aafiyahtech.ventilator.utils.AppDataProvider

class MainViewModel: ViewModel() {

    var appDataProvider: AppDataProvider? = null
    var mGroup1A =  MutableLiveData<Group_1_A>()
    var mGroup1B =  MutableLiveData<Group_1_B>()
    var mGroup2A =  MutableLiveData<Group_2_A>()
    var mGroup2B =  MutableLiveData<Group_2_B>()
    var mGroup3A =  MutableLiveData<Group_3_A>()
    var mGroup3B =  MutableLiveData<Group_3_B>()
    var mGroup3C =  MutableLiveData<Group_3_C>()
    var mGroup4A =  MutableLiveData<Group_4_A>()
    var mGroup5B =  MutableLiveData<Group_5_B>()
    var mGroup6A =  MutableLiveData<Group_6_A>()

    fun init(activity: AppCompatActivity) {
        appDataProvider = AppDataProvider.getInstance(activity.applicationContext)
    }


}