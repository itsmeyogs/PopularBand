package com.dicoding.popularband.data.model

import com.dicoding.popularband.R

data class Profile (
    val photoUrl:Int,
    val name:String,
    val email:String
)

object ProfileData{
    val profile = Profile(
        photoUrl = R.drawable.me,
        name = "Yogi Islami",
        email = "yogiihs28@gmail.com"
    )
}
