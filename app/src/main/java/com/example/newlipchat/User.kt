//사용자 정보를 담을 클래스
package com.example.newlipchat

data class User(
    var name: String,
    var email: String,
    var uId: String
){
    constructor(): this("","","")
}
