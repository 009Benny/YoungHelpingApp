package com.benny.younghelpingapp.models

data class YHUser(
    var email: String = "",
    var name: String = "",
    var nickname: String = "",
    var summary: String = "",
    var address: String = "",
    var city: String = "",
    var helper: Boolean = false
)
//    constructor(email:String, name:String, nickname:String, summary:String, address:String, city:String, helper:Boolean){
//        this.email = email
//        this.name = name
//        this.summary = summary
//        this.address = address
//        this.city = city
//        this.helper = helper
//    }
//}