package com.kevinmedia.submission1githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<List<GithubUser>>()
    val user: LiveData<List<GithubUser>> = _user

    private val _listUser = MutableLiveData<List<GithubUser>>()
    val listUser: LiveData<List<GithubUser>> = _listUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getData()
    }

    fun replaceListUser() {
        _listUser.value = _user.value
    }

    fun getDataUserSearch(searchUser: String) {
        _isLoading.value = true
        val service = ApiConfig.getApiService().getUserSearch(searchUser, MainActivity.APICode)
        service.enqueue(object : Callback<SearchUsers> {
            override fun onResponse(call: Call<SearchUsers>, response: Response<SearchUsers>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getData() {
        _isLoading.value = true
        val service = ApiConfig.getApiService().getUsers(MainActivity.APICode)
        service.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _user.value = responseBody
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}