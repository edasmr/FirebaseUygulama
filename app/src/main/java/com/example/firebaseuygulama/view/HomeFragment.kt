package com.example.firebaseuygulama.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.model.Product
import com.example.firebaseuygulama.util.FirebaseUtil
import com.example.firebaseuygulama.util.NotifyMessage
import com.example.firebaseuygulama.util.Singleton
import com.example.firebaseuygulama.util.adapter.PhotoAdapter


class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var v:View
    private lateinit var recyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private var dataList = mutableListOf<Product>()

    private lateinit var btn_detail : Button
    private lateinit var favori: ImageView

    private fun init(){

        btn_detail.setOnClickListener(this)
        favori.setOnClickListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v=view
        recyclerView = v.findViewById(R.id.home_fragment_recycler)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        photoAdapter = PhotoAdapter(v.context)
        recyclerView.adapter = photoAdapter

        FirebaseUtil.getProductDataListener( object : NotifyMessage {
            override fun onSuccess(msg: String) {
                println(msg)
            }

            override fun onFailure(msg: String?) {
                msg?.let {
                    println(it)
                }
            }
        }, getProductDataListenerOnComplete = {productList ->
            productList?.let {
                photoAdapter.setDataList(it)
            }
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id){
                R.id.btn_detail -> goToDetail()
                R.id.img_bos_kalp -> goToFavorite()
            }
        }
    }

    private fun goToDetail() {



    }

    private fun goToFavorite(){

    }

    private fun backToPage(){

        Singleton.setPage(0)

    }
}