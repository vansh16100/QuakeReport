package com.example.earthquakereport

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class MakeEarthQuakeListFromString (private val earthQuakeString: String){
    fun extractEarthquakes(): ArrayList<EarthQuakeClass> {
        val earthquakes : ArrayList<EarthQuakeClass> = ArrayList()
        try {
            val root : JSONObject = JSONObject(earthQuakeString)
            val featuresArray = root.optJSONArray("features")
            for(i in 0..featuresArray?.length()!!) {
                if (i == featuresArray.length()) continue
                val jsonObject = featuresArray.optJSONObject(i)
                val jsonObjectProperties = jsonObject.optJSONObject("properties")
                val magnitude = jsonObjectProperties?.optDouble("mag")?.toFloat()
                val place = jsonObjectProperties?.optString("place")
                val time = jsonObjectProperties?.optLong("time")
                val url = jsonObjectProperties?.optString("url")

                magnitude?.let {
                    if (place != null && time != null && url != null) {
                        earthquakes.add(EarthQuakeClass(it, place, time, url))
                    }
                }
            }
        } catch (e: JSONException) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e)
        }
        return earthquakes
    }
}

