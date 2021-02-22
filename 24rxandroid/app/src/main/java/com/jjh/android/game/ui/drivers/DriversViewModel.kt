package com.jjh.android.game.ui.drivers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

import com.jjh.android.game.model.Driver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class DriversViewModel : ViewModel() {

    companion object {
        private const val TAG = "DriversViewModel"
        private const val URL = "http://ergast.com/api/f1/"
        private val GSON = Gson()
    }

    val drivers: MutableLiveData<List<Driver>> = MutableLiveData<List<Driver>>()

    fun loadDriverData(year: Int) {
        Log.d(TAG, "loadDriverData($year)")

        Observable.create<String> {
            val client = OkHttpClient()
            val urlString = "$URL$year/drivers.json"
            val request: Request = Request.Builder().url(urlString).get().build()
            val result = try {
                val response: Response = client.newCall(request).execute()
                response.body?.string() ?: ""
            } catch (e: Exception) {
                Log.d(TAG, e.localizedMessage)
                ""
            }
            it.onNext(result)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, it)
                if (it.isNotEmpty() && !it.contains("Bad Request")) {
                    val startIndex = it.indexOf("\"Drivers\":") + "\"Drivers\":".length
                    val driversJson = it.slice(IntRange(startIndex, it.length - 4))
                    Log.d(TAG, driversJson)
                    driversJson.run {
                        // Convert JSON to an object
                        val driversList: List<Driver> =
                            GSON.fromJson(this, Array<Driver>::class.java)
                                .toList()
                        Log.d(TAG, driversList.toString())
                        drivers.value = driversList
                    }
                }
            }
    }

}