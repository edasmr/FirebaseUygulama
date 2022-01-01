package com.example.firebaseuygulama.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.model.Product

class PhotoAdapter(var context: Context) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    var dataList = emptyList<Product>()

    internal fun setDataList(dataList: List<Product>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image : ImageView
        var title : TextView
        var desc : TextView
        lateinit var price:TextView
        lateinit var favImg: ImageView

        init {
            image = itemView.findViewById(R.id.image)
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            price =itemView.findViewById(R.id.price)
            favImg = itemView.findViewById(R.id.img_bos_kalp)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.photo_layout, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.ViewHolder, position: Int) {
        var data = dataList[position]

        holder.title.text = data.productTitle
        holder.desc.text = data.productDesc
        holder.price.text = data.productPrice

        Glide.with(context)
            .load(data.ProductProfileUrl)
            .into(holder.image)

       // holder.image.setImageResource(data.productimage)

        holder.favImg.setOnClickListener {
            //holder.favImg.setImageIcon

           // holder.
        }


    }

    override fun getItemCount() = dataList.size
    }
