package com.plateform.socialManagers;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * used for login using google.
 */
public class GoogleLoginManager {

    public static int RC_SIGN_IN = 1;
    private final Context mContext;
    private GoogleSignInClient mGoogleApiClient;
    private GoogleLoginCallbackManager mGoogleLoginCallbackManager;


    /**
     * used for taking require data for init google login manager class.
     *
     * @param context                    used for init google callback.
     * @param googleLoginCallbackManager
     */
    public GoogleLoginManager(Context context, GoogleLoginCallbackManager googleLoginCallbackManager) {
        mContext = context;
        mGoogleLoginCallbackManager = googleLoginCallbackManager;
        initGoogle();
    }

    /**
     * init google login.
     */
    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = GoogleSignIn.getClient(mContext, gso);
    }

    /**
     * used for start google login.
     *
     * @param fragmentActivity
     */
    public void startGoogleLogin(FragmentActivity fragmentActivity) {
        mGoogleApiClient.signOut();
        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        fragmentActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * parse google response data.
     *
     * @param data
     */
    public void handleResponse(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result != null && result.getSignInAccount() != null) {
            mGoogleLoginCallbackManager.onGoogleInfoSuccess(result.getSignInAccount());
        } else {
            mGoogleLoginCallbackManager.onGoogleInfoError();
        }
    }

    /**
     * used for google login result callback.
     */
    public interface GoogleLoginCallbackManager {

        void onGoogleInfoError();

        void onGoogleInfoSuccess(GoogleSignInAccount googleSignInAccount);
    }


}
