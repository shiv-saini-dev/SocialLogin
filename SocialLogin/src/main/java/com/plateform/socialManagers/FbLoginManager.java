package com.plateform.socialManagers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.plateform.model.SocialLoginRequest;

import java.util.Arrays;

/**
 * used for login using fb.
 */
public class FbLoginManager {

    private Context mContext;
    private CallbackManager mCallbackManager;
    private FbLoginCallbackManager mFbLoginCallbackManager;
    private String FB = "FB";

    /**
     * used for taking require data for init fb login manager class.
     *
     * @param context                used for init fb callback.
     * @param fbLoginCallbackManager used for callback.
     */
    public FbLoginManager(Context context, FbLoginCallbackManager fbLoginCallbackManager) {
        mContext = context;
        mFbLoginCallbackManager = fbLoginCallbackManager;
        registerFbCallBacks();
    }


    /**
     * used for register callback.
     */
    private void registerFbCallBacks() {
        FacebookSdk.sdkInitialize(mContext);
        if (FacebookSdk.isInitialized()) {
            mCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    mFbLoginCallbackManager.onFbInfoProgress(true);
                    handleFacebookLoginData(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    mFbLoginCallbackManager.onFbInfoCancel();
                }

                @Override
                public void onError(FacebookException exception) {
                    if (exception != null) {
                        exception.printStackTrace();
                        if (!TextUtils.isEmpty(exception.getMessage())) {
                            mFbLoginCallbackManager.onFbInfoError(exception.getMessage());
                        }
                    }
                }
            });
        }
    }

    /**
     * used for after authorized fetch user data.
     *
     * @param accessToken - token received from facebook
     */
    private void handleFacebookLoginData(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    String id = null, name = null, email = null;
                    try {
                        id = object.optString(FbConstants.FB_ID_KEY);
                        name = object.optString(FbConstants.FB_NAME_KEY);
                        email = object.optString(FbConstants.FB_EMAIL_KEY);
                        SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
                        socialLoginRequest.setSocialKey(id);
                        socialLoginRequest.setMedium(FB);
                        socialLoginRequest.setName(name);
                        socialLoginRequest.setEmail(email);
                        mFbLoginCallbackManager.onFbInfoSuccess(socialLoginRequest);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        if (!TextUtils.isEmpty(exception.getMessage())) {
                            mFbLoginCallbackManager.onFbInfoError(exception.getMessage());
                        }
                    } finally {
                        clearObjects();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(FbConstants.FB_FIELDS_KEY, FbConstants.FB_FIELDS_VALUE);
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void clearObjects() {
        mContext = null;
        mCallbackManager = null;
        mFbLoginCallbackManager = null;
    }

    /**
     * required for get result callback from facebook activity.
     *
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void facebookLoginRequest(Activity activity) {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email"));
    }

    /**
     * used for fb login result callback.
     */
    public interface FbLoginCallbackManager {

        void onFbInfoProgress(boolean isShow);

        void onFbInfoError(String errorMsg);

        void onFbInfoCancel();

        void onFbInfoSuccess(SocialLoginRequest socialLoginRequest);
    }

    /**
     * fb login constants.
     */
    public interface FbConstants {

        String FB_FIELDS_KEY = "fields";
        String FB_FIELDS_VALUE = "id,name,email";
        String FB_ID_KEY = "id";
        String FB_NAME_KEY = "name";
        String FB_EMAIL_KEY = "email";
    }
}
