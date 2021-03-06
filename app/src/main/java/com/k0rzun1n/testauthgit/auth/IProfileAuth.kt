package com.k0rzun1n.testauthgit.auth

import android.content.Intent

/**
 * Created by krz on 02-Apr-18.
 */
interface IProfileAuth {
    var signed: SignedInto
    val RC_SIGN_IN: Int
    fun handleSignInResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun getPicLink(): String
    fun getName(): String
    fun signIn()
    fun signOut()
    enum class SignedInto {
        NOTSIGNED, FACEBOOK, VK, GOOGLE
    }
}