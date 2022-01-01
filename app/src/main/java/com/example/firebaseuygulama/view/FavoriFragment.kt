package com.example.firebaseuygulama.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.model.Product
import com.example.firebaseuygulama.util.FirebaseUtil
import com.example.firebaseuygulama.util.NotifyMessage
import de.hdodenhof.circleimageview.CircleImageView

class FavoriFragment : Fragment() {
    private lateinit var v: View
    private lateinit var imgProfile: CircleImageView
    private lateinit var txtProductDesc: TextView
    private lateinit var txtProductName: TextView
    private lateinit var txtProductPrice: TextView

    private lateinit var mProduct: Product
    private lateinit var ProductData: Product

    private fun init(){
        imgProfile = v.findViewById(R.id.product_item_imgProfile)
        txtProductDesc = v.findViewById(R.id.profile_fragment_txtUserNick)
        txtProductName = v.findViewById(R.id.profile_fragment_txtUserEmail)
        txtProductPrice = v.findViewById(R.id.profile_fragment_txtUserName)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()

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

            }
        })

    }
    private fun setData(mProduct: ArrayList<Product>){
       /* txtProductDesc.text = mProduct.productDesc
        txtProductName.text = mProduct.productTitle
        txtProductPrice.text = mProduct.productPrice



        if (mProduct.ProductProfileUrl.equals("default"))
            imgProfile.setBackgroundResource(R.drawable.etek_1)
        else
            Glide.with(v.context)
                .load(mProduct.ProductProfileUrl)
                .into(imgProfile)*/

    }



    }