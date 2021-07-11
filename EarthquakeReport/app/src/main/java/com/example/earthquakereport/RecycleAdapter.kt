package com.example.earthquakereport

import android.content.Context
import android.text.TextUtils.indexOf
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.math.floor
import android.graphics.drawable.GradientDrawable;

class RecyleAdapter(val context: Context, private val cityList: ArrayList<EarthQuakeClass>, private val clickListener : (String) -> Unit)
    : RecyclerView.Adapter<RecyleAdapter.CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val itemView : View= LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,
            false
        )
        Log.d("TAG","HELLO")
        return CustomHolder(itemView)
    }
    class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context,earthquake:EarthQuakeClass,clickListener: (String) -> Unit) {

            val decimalForamatter = DecimalFormat("0.00")
            val magnitude = decimalForamatter.format(earthquake.magnitude)
            itemView.tvText1.text = magnitude.toString()

            val x : Int = floor(magnitude.toDouble()).toInt()
            val colorId = when(x) {
                0 -> R.color.magnitude1
                1->R.color.magnitude1
                2->R.color.magnitude2
                3->R.color.magnitude3
                4->R.color.magnitude4
                5->R.color.magnitude5
                6->R.color.magnitude6
                7->R.color.magnitude7
                8->R.color.magnitude8
                9->R.color.magnitude9
                else -> R.color.magnitude10plus
            }
            val gradientDrawable:GradientDrawable = itemView.tvText1.background as GradientDrawable
            gradientDrawable.setColor(ContextCompat.getColor(context,colorId))

            val location = earthquake.city
            val idx = indexOf(location,"of")

            val city1 = if(idx == -1 || indexOf(location,"km",0,idx) == -1) "Near By" else location.substring(0,idx+2)
            val city2 = if(city1=="Near By") location else location.substring(idx+3)

            itemView.tvText20.text = city1
            itemView.tvText20.textSize = 14F
            itemView.tvText21.text = city2

            val formatterDate = SimpleDateFormat("yyyy-MM-dd")
            val formatterTime = SimpleDateFormat("h:mm a")

            val date = formatterDate.format(earthquake.date)
            val time = formatterTime.format(earthquake.date)

            itemView.tvText30.text = date.toString()
            itemView.tvText31.text = time.toString()

            itemView.setOnClickListener{ clickListener(earthquake.url)}
        }

    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {

        holder.onBind(context,cityList[position],clickListener)

    }
    override fun getItemCount() = cityList.size

}