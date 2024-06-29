package com.example.chatapp.data

open class Event<out T>(val content:T) {
    var hasbeenhandled=false
    fun getContentOrNull(): T? {
        return if(hasbeenhandled) null
        else{
            hasbeenhandled=true
            content
        }
    }
}