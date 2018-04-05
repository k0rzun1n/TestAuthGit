package com.k0rzun1n.testauthgit.auth

import android.content.Intent

/**
 * Created by krz on 05-Apr-18.
 */
class FBProfileAuth : IProfileAuth {
    override var signed: IProfileAuth.SignedInto = IProfileAuth.SignedInto.NOTSIGNED
    override val RC_SIGN_IN: Int =111

    override fun handleSignInResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun getPicLink(): String {
        return ""
    }

    override fun getName(): String {
        return ""
    }

    override fun signIn() {
    }

    override fun signOut() {
    }

}