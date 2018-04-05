package com.k0rzun1n.testauthgit

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GoogleProfileAuth : IProfileAuth {
    override var signed = IProfileAuth.SignedInto.NOTSIGNED
    override val RC_SIGN_IN = 9001
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mActivity: SigninActivity
    var mAccount: GoogleSignInAccount? = null

    @SuppressLint("RestrictedApi")
    constructor(activity: SigninActivity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        mActivity = activity
    }

    @SuppressLint("RestrictedApi")
    override fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(mActivity, signInIntent, RC_SIGN_IN, null)
    }

    @SuppressLint("RestrictedApi")
    override fun handleSignInResult(data: Intent?) {
        var completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            if (completedTask.isSuccessful()) {
                Log.e("SIGNIN","WIN")
                mAccount = completedTask.getResult()
                mActivity.onSigned()
            }else{
                Log.e("SIGNIN","FAIL")
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    @SuppressLint("RestrictedApi")
    override fun signOut() {
        mGoogleSignInClient.revokeAccess()

    }

    override fun getName(): String {
        return mAccount?.displayName?:"..."
    }

    override fun getPicLink(): String {
        return mAccount?.photoUrl?.toString()?:""
    }
}