package com.example.parkinglot.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkinglot.R
import com.example.parkinglot.model.ParkingLotsResponse

class ParkingLotAdapter(private val mList: List<ParkingLotsResponse>?,context: Context?) : RecyclerView.Adapter<ParkingLotAdapter.ViewHolder>() {

     private var mContext:Context?
    init {
        mContext = context;
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img)
        val textView: TextView = itemView.findViewById(R.id.txtparkinglot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.parkinglot_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList?.get(position)

        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
//        holder.textView.text = getString(R.string.parking_slot_number,  it.data?.parking_no)
//        itemsViewModel?.parking_no
        holder.textView.text =  mContext?.getString(R.string.parking,  itemsViewModel?.parking_no)
//        holder.textView.text = "Parking: "+  itemsViewModel?.parking_no
        itemsViewModel?.is_available?.let {
            if(it){
                holder.imageView.setImageResource(R.drawable.green_circle_24)
            }else{
                holder.imageView.setImageResource(R.drawable.red_circle_24)
            }

        }


    }
}