package com.plateform.socialManagers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.plateform.NetworkUtils
import com.plateform.model.SocialLoginRequest
import com.plateform.constant.Constants
import com.plateform.listener.SocialManagerListener

class SocialLoginManager {

    lateinit var googleLoginManager: GoogleLoginManager
    lateinit var fbLoginManager: FbLoginManager


    /**
     * This function is used to init the sdk as per TYPE
     *
     * @param type :-               type refers which sdk want to initlize
     * @param context :-            refers to the context of ACtivity
     * @param listener :-           listener is type of SocialManagerListener which used for passing callback
     * */
    private fun initSdk(type: String, context: Context, listener: SocialManagerListener) {
        when {
            type.equals(Constants.MEDIUM.FACEBOOK) -> {
                facebookInit(context, listener)
            }
            type.equals(Constants.MEDIUM.GOOGLE) -> {
                googleInit(context, listener)
            }
        }
    }

    /**
     * This function is used to init the facebook sdk
     * @param context :-            refers to the context of ACtivity
     * @param listener :-           listener is type of SocialManagerListener which used for passing callback
     */
    private fun facebookInit(context: Context, listener: SocialManagerListener) {
        fbLoginManager = FbLoginManager(context, object : FbLoginManager.FbLoginCallbackManager {
            override fun onFbInfoProgress(isShow: Boolean) {}
            override fun onFbInfoError(errorMsg: String) {
                listener.failed(Constants.ERROR_TYPE.GENERIC, errorMsg)
            }

            override fun onFbInfoCancel() {
                listener.failed(Constants.ERROR_TYPE.CANCELLED, "")
            }

            override fun onFbInfoSuccess(socialLoginRequest: SocialLoginRequest) {
                listener.success(socialLoginRequest)
            }
        })

    }

    /**
     * This function is used to initilize Google
     * @param context :-            refers to the context of ACtivity
     * @param listener :-           listener is type of SocialManagerListener which used for passing callback

     */
    private fun googleInit(context: Context, listener: SocialManagerListener) {

        googleLoginManager = GoogleLoginManager(context, object : GoogleLoginManager.GoogleLoginCallbackManager {
            override fun onGoogleInfoError() {
                listener.failed(Constants.ERROR_TYPE.GENERIC, "")
            }

            override fun onGoogleInfoSuccess(googleSignInAccount: GoogleSignInAccount) {
                listener.success(createSoclalLoginRequest(googleSignInAccount))
            }
        })
    }

    /**
     * This function is used to create SocialLoginRequest
     * @param result : - is the result which we received in google signin Success
     * @return :- SocialLoginRequest
     */
    private fun createSoclalLoginRequest(result: GoogleSignInAccount): SocialLoginRequest {
        val socialLoginRequest = SocialLoginRequest()
        socialLoginRequest.email = result.getEmail()
        socialLoginRequest.name = result.getDisplayName()
        socialLoginRequest.socialKey = result.getId()
        socialLoginRequest.medium = Constants.MEDIUM.GOOGLE
        return socialLoginRequest
    }

    /**
     * This function is used to perform login operation as per type
     * @param type :-               type refers which sdk want to initlize
     * @param context :-            refers to the context of ACtivity
     * @param listener :-           listener is type of SocialManagerListener which used for passing callback
     *
     */
    fun login(type: String, activity: AppCompatActivity, listener: SocialManagerListener) {
        if (NetworkUtils.isNetworkConnected(activity)) {
            initSdk(type, activity, listener)
            when {
                type.equals(Constants.MEDIUM.FACEBOOK) -> {
                    fbLoginManager.facebookLoginRequest(activity)
                }
                type.equals(Constants.MEDIUM.GOOGLE) -> {
                    googleLoginManager.startGoogleLogin(activity)
                }
            }
        } else {
            listener.failed(Constants.ERROR_TYPE.NO_INTERNET, "")
        }
    }

    /**
     * This function is used to handle the response from google
     */
    fun handleResponse(data: Intent) {
        googleLoginManager.handleResponse(data)
    }

    /**
     *
     * @Params requestCode – The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param  resultCode – The integer result code returned by the child activity through its setResult().
     * @param  data – An Intent, which can return result data to the caller (various data can be attached to Intent "extras")
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        fbLoginManager.onActivityResult(requestCode, resultCode, data)
    }
}