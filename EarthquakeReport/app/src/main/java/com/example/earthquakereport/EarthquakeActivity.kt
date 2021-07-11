package com.example.earthquakereport

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class EarthquakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)
        val networkTask : NetworkTask = NetworkTask(this)

        val i = intent
        val startDate = i.getStringExtra("startDate")
        val endDate = i.getStringExtra("endDate")
        networkTask.execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=${startDate}&endtime=${endDate}&minmagnitude=5")
    }
    private val earthquakes : ArrayList<EarthQuakeClass> = ArrayList()
    companion object {
        class NetworkTask internal constructor(context: EarthquakeActivity) :
                AsyncTask<String, String, String>() {
            private val weakReference: WeakReference<EarthquakeActivity> = WeakReference(context)
            override fun doInBackground(vararg params: String?):String {
                try {
                    val url = URL(params[0])
                    val networkConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
                    val inputStream = networkConnection.inputStream
                    val scanner = Scanner(inputStream)
                    scanner.useDelimiter("\\A")
                    var buffer:String? =null
                    if(scanner.hasNext()) {
                        buffer = scanner.next()
                    }
                    return buffer.toString();
                }catch (e : MalformedURLException) {
                    e.printStackTrace()
                }catch (e: IOException) {
                    e.printStackTrace()
                }
                return "Failed"
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val earthquake: ArrayList<EarthQuakeClass> = MakeEarthQuakeListFromString(result).extractEarthquakes()
                val activity = weakReference.get()
                val eRecyclerView = activity?.findViewById<RecyclerView>(R.id.earthQuakeListView)
                eRecyclerView?.layoutManager = LinearLayoutManager(activity?.baseContext)
                if (activity != null) {
                    eRecyclerView?.adapter = RecyleAdapter(activity.baseContext,earthquake){ url : String -> activity.itemClicked(url) }
                }
            }

            override fun onProgressUpdate(vararg values: String?) {
                super.onProgressUpdate(*values)

            }
        }
    }
    private fun itemClicked(url : String) {
        val intent = Intent();
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.parse(url)
        intent.data = uri
        startActivity(intent)
    }
}