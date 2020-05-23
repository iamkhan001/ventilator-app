package com.aafiyahtech.ventilator.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aafiyahtech.ventilator.config.ApiClient
import com.aafiyahtech.ventilator.config.ApiInterface
import com.aafiyahtech.ventilator.models.*
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCaller (private val type: String, apiUrl: String): Thread() {

    companion object {
        private const val TAG = "ApiCaller"
        const val GET_GROUP_1_A = "get_group_1_a"
        const val GET_GROUP_1_B = "get_group_1_b"
        const val GET_GROUP_2_A = "get_group_2_a"
        const val GET_GROUP_2_B = "get_group_2_b"
        const val GET_GROUP_3_A = "get_group_3_a"
        const val GET_GROUP_3_B = "get_group_3_b"
        const val GET_GROUP_3_C = "get_group_3_c"
        const val GET_GROUP_4_A = "get_group_4_a"
        const val GET_GROUP_5_B = "get_group_5_b"
        const val GET_GROUP_6_A = "get_group_6_a"

        const val SET_GROUP_1_A = "set_group_1_a"
        const val SET_GROUP_1_B = "set_group_1_b"
        const val SET_GROUP_2_A = "set_group_2_a"
        const val SET_GROUP_2_B = "set_group_2_b"
        const val SET_GROUP_3_A = "set_group_3_a"
        const val SET_GROUP_3_B = "set_group_3_b"
        const val SET_GROUP_3_C = "set_group_3_c"
        const val SET_GROUP_4_A = "set_group_4_a"
        const val SET_GROUP_5_B = "set_group_5_b"
        const val SET_GROUP_6_A = "set_group_6_a"

        const val DELAY = 1000L
    }

    private var call: Call<ResponseBody>? = null

    var isRecursive = false

    private val apiClient = ApiClient.getRetrofitInstance(apiUrl).create(ApiInterface::class.java)
    
    var mGroup1a: MutableLiveData<Group_1_A>? = null
    var mGroup1b: MutableLiveData<Group_1_B>? = null
    var mGroup2a: MutableLiveData<Group_2_A>? = null
    var mGroup2b: MutableLiveData<Group_2_B>? = null
    var mGroup3a: MutableLiveData<Group_3_A>? = null
    var mGroup3b: MutableLiveData<Group_3_B>? = null
    var mGroup4a: MutableLiveData<Group_4_A>? = null
    var mGroup5b: MutableLiveData<Group_5_B>? = null
    var mGroup6a: MutableLiveData<Group_6_A>? = null
    var onApiResponseListener: OnApiResponseListener? = null

    override fun run() {
        super.run()
        when(type) {
            GET_GROUP_1_A -> {
                loadDataGroup1a()
            }
            GET_GROUP_1_B -> {
                loadDataGroup1b()
            }
            GET_GROUP_2_A -> {
                loadDataGroup2a()
            }
            GET_GROUP_2_B -> {
                loadDataGroup2b()
            }
            GET_GROUP_3_A ->{
                loadDataGroup3a()
            }
            GET_GROUP_3_B ->{
                loadDataGroup3b()
            }
            GET_GROUP_4_A ->{
                loadDataGroup4a()
            }
            GET_GROUP_5_B->{
                loadDataGroup5b()
            }
            GET_GROUP_6_A ->{
                loadDataGroup6a()
            }
        }
    }

    private fun loadDataGroup1a() {

        call = apiClient.getGroup1A(GET_GROUP_1_A)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group1A: Group_1_A? = Gson().fromJson(r.getJSONObject("data").toString(), Group_1_A::class.java)
                                if (group1A != null) {
                                    mGroup1a?.postValue(group1A)
                                }
                                return
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }

                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")
                }

            }
        )
    }

    private fun loadDataGroup1b() {

        call = apiClient.getGroup1A(GET_GROUP_1_B)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group1B: Group_1_B? = Gson().fromJson(r.getJSONObject("data").toString(), Group_1_B::class.java)
                                if (group1B != null) {
                                    mGroup1b?.postValue(group1B)
                                }
                                return
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }

                    onApiResponseListener?.onError(msg)
                    
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")
                }

            }
        )
    }

    private fun loadDataGroup2a() {

        call = apiClient.getGroup1A(GET_GROUP_2_A)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group2a: Group_2_A? = Gson().fromJson(r.getJSONObject("data").toString(), Group_2_A::class.java)
                                if (group2a != null) {
                                    mGroup2a?.postValue(group2a)
                                }
                                return
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")
                }

            }
        )
    }

    private fun loadDataGroup2b() {

        call = apiClient.getGroup1A(GET_GROUP_2_B)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group2B: Group_2_B? = Gson().fromJson(r.getJSONObject("data").toString(), Group_2_B::class.java)
                                if (group2B != null) {
                                    mGroup2b?.postValue(group2B)
                                    return
                                }
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")

                }

            }
        )
    }

    private fun loadDataGroup3a() {

        call = apiClient.getGroup1A(GET_GROUP_3_A)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group: Group_3_A? = Gson().fromJson(r.getJSONObject("data").toString(), Group_3_A::class.java)
                                if (group != null) {
                                    mGroup3a?.postValue(group)
                                    return
                                }
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")

                }

            }
        )
    }

    private fun loadDataGroup3b() {

        call = apiClient.getGroup1A(GET_GROUP_3_B)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group: Group_3_B? = Gson().fromJson(r.getJSONObject("data").toString(), Group_3_B::class.java)
                                if (group != null) {
                                    mGroup3b?.postValue(group)
                                    return
                                }
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")

                }

            }
        )
    }

    private fun loadDataGroup4a() {

        call = apiClient.getGroup1A(GET_GROUP_4_A)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group: Group_4_A? = Gson().fromJson(r.getJSONObject("data").toString(), Group_4_A::class.java)
                                if (group != null) {
                                    mGroup4a?.postValue(group)
                                    return
                                }
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")

                }

            }
        )
    }

    private fun loadDataGroup5b() {

        call = apiClient.getGroup1A(GET_GROUP_5_B)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group: Group_5_B? = Gson().fromJson(r.getJSONObject("data").toString(), Group_5_B::class.java)
                                if (group != null) {
                                    mGroup5b?.postValue(group)
                                    return
                                }
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")

                }

            }
        )
    }

    private fun loadDataGroup6a() {

        call = apiClient.getGroup1A(GET_GROUP_6_A)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                val group: Group_6_A? = Gson().fromJson(r.getJSONObject("data").toString(), Group_6_A::class.java)
                                if (group != null) {
                                    mGroup6a?.postValue(group)

                                    if (isRecursive){
                                        sleep(DELAY)
                                        loadDataGroup6a()
                                        return
                                    }

                                    return
                                }
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }
                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")

                }

            }
        )
    }

    fun setDataGroup(api: String, data: String) {
        Log.e(TAG,"api: $api data: $data")
        call = apiClient.setGroup1A(api, data)
        call?.enqueue(
            object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e(TAG, "success: " + response.code() + "\nbody: " + response.body() + "\nerror: body " + response.errorBody())

                    if (response.body() != null){
                        try {
                            val r = JSONObject(response.body()!!.string())
                            if (r.getBoolean("success")) {
                                onApiResponseListener?.onSuccess()
                                return
                            }
                            val msg = if (r.has("message")) {
                                r.getString("message")
                            }else {
                                "Error Occurred!"
                            }
                            onApiResponseListener?.onError(msg)
                            return
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    val msg = try {
                        val r = JSONObject(response.errorBody()!!.string())
                        if (r.has("message")) {
                            r.getString("message")
                        }else {
                            "Error Occurred!"
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                        "Error Occurred!"
                    }

                    onApiResponseListener?.onError(msg)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "Failed: " + t.message)
                    onApiResponseListener?.onError("Cannot connect to ventilator. Please check IP Address")
                }

            }
        )
    }



    fun stopExecution(){
        isRecursive = false
        call?.cancel()
    }
    
    interface OnApiResponseListener{
        fun onError(msg: String)
        fun onSuccess(){}
    }

}