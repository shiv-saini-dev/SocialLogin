package com.plateform.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class SocialLoginRequest {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("medium")
    var medium: String? = null

    @SerializedName("socialKey")
    var socialKey: String? = null
}