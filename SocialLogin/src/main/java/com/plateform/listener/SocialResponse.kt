package com.plateform.listener

import com.plateform.model.SocialLoginRequest

sealed class SocialResponse {
    data class success(var socialLoginRequest: SocialLoginRequest) : SocialResponse()
    data class failed(var error: String) : SocialResponse()
}
