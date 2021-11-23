package com.plateform.listener

import com.plateform.model.SocialLoginRequest

interface SocialManagerListener {
    fun success(socialLoginRequest: SocialLoginRequest)

    fun failed(errorCode:Int,errorMsg: String)
}