package com.example.firebaseuygulama.util

interface NotifyMessage {
    fun onSuccess(msg: String)
    fun onFailure(msg: String?)
}