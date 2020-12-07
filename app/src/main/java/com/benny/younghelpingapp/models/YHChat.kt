package com.benny.younghelpingapp.models

data class YHChat(
    var id:String = "",
    var name:String = "",
    var users:List<String> = emptyList()

)