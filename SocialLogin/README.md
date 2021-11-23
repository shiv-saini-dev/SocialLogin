# Social Login Module #

Social Authentication is a combined helper module to implement social login for Facebook, gmail and
apple using respective SDK.
** Usage **

Flow below steps for integration Import SocialLogin as module in App Level Gradle

```go

   implementation project(path: ':SocialLogin')
    
   implementation 'com.facebook.android:facebook-login:latest.release'

```

Logging in with Social Manager is as easy as:

```go
 interface MEDIUM {
        companion object {
            const val FACEBOOK = "FACEBOOK"
            const val GOOGLE = "GOOGLE"
        }
    }
    
 SocialLoginManager  loginManager = new SocialLoginManager();
  loginManager.login(TYPE, this, new SocialManagerListener() {
      @Override
      public void success(@NonNull SocialLoginRequest socialLoginRequest) {
         Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
      }
      @Override
      public void failed(int errorCOde, @NonNull String errorMsg) {
         Toast.makeText(MainActivity.this, "errorMsg", Toast.LENGTH_SHORT).show();
      }
  });    
    
```

# Response model#

```go

interface SocialManagerListener {
    fun success(socialLoginRequest: SocialLoginRequest)

    fun failed(errorCode:Int,errorMsg: String)
}

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

interface ERROR_TYPE {
     companion object {
        const val NO_INTERNET = 1
        const val CANCELLED = 2
        const val GENERIC = 0
        }
    }
    
```

# Usage #

Social Authentication supports login providers for Facebook, google, apple sign in.

# Using Facebook Login#

To get started, you first need to register an application with Facebook. After registering your app,
go into your app dashboard's settings page. Click "Add Platform", and fill in your packageName and "
start Activity".

Replace facebook_app_id and fb_login_protocol_scheme with your app

```go
 <meta-data
       android:name="com.facebook.sdk.ApplicationId"
       android:value="@string/facebook_app_id" />

        <activity
            android:name="com.facebook.FacebookActivity"
            android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="@string/app_name" />
        <activity
            android:name="com.facebook.CustomTabActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data android:scheme="@string/fb_login_protocol_scheme" />
            </intent-filter>
        </activity>
```

you can initiate the login screen by calling

```go
        SocialLoginManager  loginManager = new SocialLoginManager();
        type = Constants.MEDIUM.FACEBOOK;
        loginManager.login(Constants.MEDIUM.FACEBOOK, this, new SocialManagerListener() {
        @Override
        public void success(@NonNull SocialLoginRequest socialLoginRequest) {
             Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void failed(int errorCOde, @NonNull String errorMsg) {
            Toast.makeText(MainActivity.this, "errorMsg", Toast.LENGTH_SHORT).show();
        }
        });

```

override onACtivityResult in Activity or Fragment

```go
  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (type.equalsIgnoreCase(Constants.MEDIUM.FACEBOOK)) {
            loginManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
```

# Using Google Login# 
To get started, you first need to register an application with firebase.then create Android App".

Next Download google-services.json and add it to app level of your project

Then, you can initiate the login screen by calling:

```go
        SocialLoginManager  loginManager = new SocialLoginManager();
        type = Constants.MEDIUM.GOOGLE;
        loginManager.login(Constants.MEDIUM.GOOGLE, this, new SocialManagerListener() {
        @Override
        public void success(@NonNull SocialLoginRequest socialLoginRequest) {
             Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void failed(int errorCOde, @NonNull String errorMsg) {
            Toast.makeText(MainActivity.this, "errorMsg", Toast.LENGTH_SHORT).show();
        }
        });

```

# Error Cases thrown#

case 1 - code 0, message != nil Error from SDK during login case 2 - code 1, message = nil Internet
connection issue





**Author :- **

**Amar kumar**





