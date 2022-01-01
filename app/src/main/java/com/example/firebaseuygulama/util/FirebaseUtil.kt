package com.example.firebaseuygulama.util

import com.example.firebaseuygulama.model.Product
import com.example.firebaseuygulama.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirebaseUtil {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val mFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var mUser: User
    lateinit var mProduct: Product
    private var fUser: FirebaseUser? = null
    lateinit var docRef: DocumentReference
    lateinit var docRef2: DocumentReference
    lateinit var mQuery: Query

    fun signUpUser(
        userEmail: String,
        userPassword: String,
        notifyMessage: NotifyMessage,
        signUpUserOnComplete: (fUser: FirebaseUser?, signState: Boolean) -> Unit
    ) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    fUser = it.result.user

                    if (fUser != null) {
                        fUser!!.sendEmailVerification()
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    notifyMessage.onSuccess("Kullanıcı başarıyla kayıt oldu")
                                    signUpUserOnComplete(fUser, true)
                                } else {
                                    notifyMessage.onFailure(it.exception?.message)
                                    signUpUserOnComplete(fUser, false)
                                }
                            }
                    } else {
                        notifyMessage.onFailure("Kayıtlı kullanıcı datasına ulaşılamadı")
                        signUpUserOnComplete(fUser, false)
                    }
                } else {
                    notifyMessage.onFailure(it.exception?.message)
                    signUpUserOnComplete(fUser, false)
                }
            }
    }



    fun saveUserData(
        userData: User,
        notifyMessage: NotifyMessage,
        saveUserDataOnComplete: (saveState: Boolean) -> Unit
    ) {
        mFireStore.collection("Users").document(userData.userId)
            .set(userData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    notifyMessage.onSuccess("Lütfen email adresinize gelen linki doğrulayınız")
                    saveUserDataOnComplete(true)
                } else {
                    notifyMessage.onFailure(it.exception?.message)
                    saveUserDataOnComplete(false)
                }
            }
    }

    fun saveProductData(
        productData:Product,

        saveProductDataOnComplete: (saveState: Boolean) -> Unit
    ) {
        mFireStore.collection("Product").document(productData.productId)
            .set(productData)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    saveProductDataOnComplete(true)
                } else {

                    saveProductDataOnComplete(false)
                }
            }
    }





    fun signInUser(
        userEmail: String,
        userPassword: String,
        notifyMessage: NotifyMessage,
        signInUserOnComplete: (fUser: FirebaseUser?, signState: Boolean) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    fUser = it.result.user

                    if (fUser != null) {
                        if (fUser!!.isEmailVerified) {
                            notifyMessage.onSuccess("Başarıyla giriş yaptınız")
                            signInUserOnComplete(fUser, true)
                        } else {
                            notifyMessage.onFailure("Lütfen email adresinizi onaylayınız")
                            signInUserOnComplete(fUser, false)
                        }
                    } else {
                        notifyMessage.onFailure("Giriş yapan kullanıcı datası boş")
                        signInUserOnComplete(fUser, false)
                    }
                } else {
                    notifyMessage.onFailure(it.exception?.message)
                    signInUserOnComplete(fUser, false)
                }
            }
    }

    fun signInControl(
        notifyMessage: NotifyMessage,
        signInControlOnComplete: (fUser: FirebaseUser?, signSate: Boolean) -> Unit
    ) {
        fUser = mAuth.currentUser

        if (fUser != null) {
            if (fUser!!.isEmailVerified)
                signInControlOnComplete(fUser, true)
            else {
                notifyMessage.onFailure("Lütfen email adresinizi onaylayınız")
                signInControlOnComplete(fUser, false)
            }
        } else
            signInControlOnComplete(fUser, false)
    }

    fun getProductDataOneTime(
        productId: String,
        notifyMessage: NotifyMessage,
        getProductDataOneTimeOnComplete: (ProductData: Product?) -> Unit
    ) {
        docRef = mFireStore.collection("Product").document(productId)
        docRef.get()
            .addOnSuccessListener {
                if (it.exists()) {
                    mProduct = it.toObject(Product::class.java)!!
                    notifyMessage.onSuccess("Product verileri başarıyla alındı")
                    getProductDataOneTimeOnComplete(mProduct)
                } else {
                    notifyMessage.onFailure("Product datası boş")
                    getProductDataOneTimeOnComplete(mProduct)
                }
            }.addOnFailureListener {
                notifyMessage.onFailure(it.message)
                getProductDataOneTimeOnComplete(mProduct)
            }
    }



    fun getUserDataOneTime(
        userId: String,
        notifyMessage: NotifyMessage,
        getUserDataOneTimeOnComplete: (userData: User?) -> Unit
    ) {
        docRef = mFireStore.collection("Users").document(userId)
        docRef.get()
            .addOnSuccessListener {
                if (it.exists()) {
                    mUser = it.toObject(User::class.java)!!
                    notifyMessage.onSuccess("User verileri başarıyla alındı")
                    getUserDataOneTimeOnComplete(mUser)
                } else {
                    notifyMessage.onFailure("User datası boş")
                    getUserDataOneTimeOnComplete(mUser)
                }
            }.addOnFailureListener {
                notifyMessage.onFailure(it.message)
                getUserDataOneTimeOnComplete(mUser)
            }
    }

    fun getUserDataListener(
        userId: String,
        notifyMessage: NotifyMessage,
        getUserDataListenerOnComplete: (userData: User?) -> Unit
    ) {
        docRef = mFireStore.collection("Users").document(userId)
        docRef.addSnapshotListener { value, error ->
            if (error != null) {
                notifyMessage.onFailure(error.message)
                getUserDataListenerOnComplete(mUser)
                return@addSnapshotListener
            }

            if (value != null) {
                if (value.exists()) {
                    mUser = value.toObject(User::class.java)!!
                    notifyMessage.onSuccess("User verileri başarıyla alındı")
                    getUserDataListenerOnComplete(mUser)
                } else {
                    notifyMessage.onFailure("User datası boş")
                    getUserDataListenerOnComplete(mUser)
                }
            } else {
                notifyMessage.onFailure("Data boş")
                getUserDataListenerOnComplete(mUser)
            }
        }
    }

    fun getProductDataListener(
        notifyMessage: NotifyMessage,
        getProductDataListenerOnComplete: (productList: ArrayList<Product>?) -> Unit
    ) {
        var pList: ArrayList<Product>? = null

        mQuery = mFireStore.collection("Products")
        mQuery.addSnapshotListener { value, error ->
            if (error != null){
                notifyMessage.onFailure(error.message)
                getProductDataListenerOnComplete(pList)
                return@addSnapshotListener
            }

            if (value != null){
                if (value.documents.size > 0){
                    pList = ArrayList()

                    for (snp in 0 until value.documents.size){
                        if (value.documents.get(snp).exists()){
                            mProduct = value.documents.get(snp).toObject(Product::class.java)!!
                            pList!!.add(mProduct)

                            if (snp == value.documents.size -1)
                                getProductDataListenerOnComplete(pList)
                        }else{
                            if (snp == value.documents.size -1)
                                getProductDataListenerOnComplete(pList)
                        }
                    }
                }else
                    getProductDataListenerOnComplete(pList)
            }else
                getProductDataListenerOnComplete(pList)
        }
    }
}