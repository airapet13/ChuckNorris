package com.example.chucknorris

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

var data = mutableListOf<String>()

class JokesViewModel : ViewModel() {

    private var _resultLive = MutableLiveData<List<String>>()
    var resultLive: LiveData<List<String>> = _resultLive

    fun getCurrentData(count: String) {
        if (count == "" || count.toInt() != 0) {
            val api = Retrofit.Builder()
                .baseUrl("https://api.icndb.com/jokes/random/${count}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequests::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getJoke().awaitResponse()
                    if (response.isSuccessful) {
                        for (i in 0 until count.toInt()) {
                            data.add(response.body()!!.value[i].joke.replace("&quot;", "\""))
                        }
                    }
                } catch (e: Exception) {
                    data.add("Something went wrong, check your internet connection\n")
                }
                _resultLive.postValue(data)
                data = mutableListOf()
            }
        } else {
            data.add("Chuck's zero joke")
            _resultLive.postValue(data)
            data = mutableListOf()
        }
    }
}