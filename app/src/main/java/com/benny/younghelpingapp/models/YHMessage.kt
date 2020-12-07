package com.benny.younghelpingapp.models

import java.util.*

data class YHMessage (
    var message:String = "",
    var from:String = "",
    var dob:Date = Date()
)