package com.jjh.android.game.ui.drivers

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

import com.jjh.android.game.model.Driver

class DriversViewModel : ViewModel() {

    companion object {
        private const val TAG = "SlideshowViewModel"
        private const val URL = "http://ergast.com/api/f1/"
        private val GSON = Gson()
    }

    val drivers: MutableLiveData<List<Driver>> = MutableLiveData<List<Driver>>()

    fun loadDriverData(year: Int) {
        Log.d(TAG, "loadDriverData")
        GetRequestAsyncTask(year).execute()
    }

    inner class GetRequestAsyncTask(val year: Int) : AsyncTask<Unit, Unit, String>() {

        override fun doInBackground(vararg params: Unit): String {
            Log.d(TAG, "GetRequestAsyncTask.doInBackground")
            val client = OkHttpClient()
            val urlString = "$URL$year/drivers.json"
            val request: Request = Request.Builder().url(urlString).get().build()

            return try {
                val response: Response = client.newCall(request).execute()
                response.body?.string() ?: ""
            } catch (e: Exception) {
                Log.d(TAG, e.localizedMessage)
                ""
            }
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            Log.d(TAG, "GetRequestAsyncTask.onPostExecute")
            if (result.isNotEmpty() && !result.contains("Bad Request")) {
                val startIndex = result.indexOf("\"Drivers\":") + "\"Drivers\":".length
                val driversJson = result.slice(IntRange(startIndex, result.length - 4))
                Log.d(TAG, driversJson)
                driversJson.run {
                    // Example of converting JSON to an object
                    val driversList: List<Driver> =
                        GSON.fromJson(this, Array<Driver>::class.java)
                            .toList()
                    drivers.value = driversList
                }
            }
        }

    }

}