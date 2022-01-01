package com.example.firebaseuygulama.model

data class User(
    val userId: String = "",
    val userNick: String="",
    val userName: String = "",
    val userSurname:String = "",
    val userMail: String = "",
    val userProfileUrl: String = "",
    val userFavoriList:ArrayList<String> =ArrayList()
)
