package com.k0rzun1n.testauthgit

import android.app.Application
import android.util.Log

/**
 * Created by krz on 01-Apr-18.
 */


class MyApp : Application(){
    var profAuth: IProfileAuth? = null
    override fun onCreate() {
        super.onCreate()
        Log.e("sup","sup")
    }
}