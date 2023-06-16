package com.example.newlipchat

data class flag(
    var camera_on: String,
    var current_face_id: String,
    var face: String,
    var text: String
) {
    constructor(): this("","","","")
}