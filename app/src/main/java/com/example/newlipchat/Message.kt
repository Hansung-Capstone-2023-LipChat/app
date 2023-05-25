package com.example.newlipchat

data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
