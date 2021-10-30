package com.example.retrofitexample

import com.google.gson.annotations.SerializedName

data class Post(val userId: String, val id: Int, val title:String,
                @SerializedName("body") val text: String)

