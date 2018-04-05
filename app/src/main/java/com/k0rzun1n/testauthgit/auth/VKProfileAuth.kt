package com.k0rzun1n.testauthgit.auth

import android.content.Intent
import com.k0rzun1n.testauthgit.SigninActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*

class VKProfileAuth : IProfileAuth {
    override var signed: IProfileAuth.SignedInto = IProfileAuth.SignedInto.NOTSIGNED
    override val RC_SIGN_IN: Int = 10485
    lateinit var mActivity: SigninActivity


    private val sMyScope = arrayOf(VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.NOHTTPS, VKScope.MESSAGES, VKScope.DOCS)

    constructor(activity: SigninActivity) {
        mActivity = activity
    }

    override fun handleSignInResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                // User passed Authorization
                mActivity.onSigned()
            }

            override fun onError(error: VKError) {
                // User didn't pass Authorization
            }
        }
        VKSdk.onActivityResult(requestCode, resultCode, data, callback)
    }

    override fun signIn() {
        VKSdk.login(mActivity, *sMyScope)
    }

    override fun signOut() {
        VKSdk.logout()
    }

    override fun getPicLink(): String {
        val request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "first_name,last_name,photo_max,photo_max_orig"))
        request.secure = false
        request.useSystemLanguage = false
        var pic = ""
        request.executeSyncWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                pic = response?.json?.getJSONArray("response")?.getJSONObject(0)?.getString("photo_max") ?: ""
            }
        })
        return pic
    }

    override fun getName(): String {
        val request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                "first_name,last_name,photo_max,photo_max_orig"))
        request.secure = false
        request.useSystemLanguage = false
        var name = ""
        request.executeSyncWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                name += response?.json?.getJSONArray("response")?.getJSONObject(0)?.getString("first_name") ?: ""
                name += " "
                name += response?.json?.getJSONArray("response")?.getJSONObject(0)?.getString("last_name") ?: ""
            }
        })
        return name
    }

}