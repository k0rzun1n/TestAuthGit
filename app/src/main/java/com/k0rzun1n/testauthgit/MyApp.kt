package com.k0rzun1n.testauthgit

import android.app.Application
import android.content.Intent
import android.widget.Toast
import com.k0rzun1n.testauthgit.auth.IProfileAuth
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk

/**
 * Created by krz on 01-Apr-18.
 */


class MyApp : Application(){
    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                Toast.makeText(this@MyApp, "AccessToken invalidated", Toast.LENGTH_LONG).show()
                val intent = Intent(this@MyApp, SigninActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
    var profAuth: IProfileAuth? = null
    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
    }
}