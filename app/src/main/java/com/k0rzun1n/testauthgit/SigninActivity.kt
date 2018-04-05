package com.k0rzun1n.testauthgit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.k0rzun1n.testauthgit.auth.GoogleProfileAuth
import com.k0rzun1n.testauthgit.auth.VKProfileAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //bug?
//        googleSigninButton.setOnClickListener{ onGoogleClick()}
        findViewById<View>(R.id.googleSigninButton).setOnClickListener { onGoogleClick() }
        vkSignInButton.setOnClickListener {onVKClick()}
        signOutButton.setOnClickListener { onSignOutClick() }
        updateUI()
    }


    fun onSignOutClick() {
        val pa = (application as MyApp).profAuth ?: return
        pa.signOut()
        (application as MyApp).profAuth = null
        updateUI()
    }

    fun onVKClick() {
        val gpa = VKProfileAuth(this)
        (application as MyApp).profAuth = gpa
        gpa.signIn()
    }

    fun onGoogleClick() {
        val gpa = GoogleProfileAuth(this)
        (application as MyApp).profAuth = gpa
        gpa.signIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val pa = (application as MyApp).profAuth ?: return
        if (requestCode == pa.RC_SIGN_IN) {
            pa.handleSignInResult(requestCode, resultCode, data)
        }
    }

    fun updateUI() {
        val pa = (application as MyApp).profAuth
        val signed = pa == null

        tvProfileName.text = pa?.getName()
        Picasso.get().load(pa?.getPicLink()).into(ivProfilePic)

        findViewById<View>(R.id.googleSigninButton).visibility = if (signed) View.VISIBLE else View.GONE
        vkSignInButton.visibility = if (signed) View.VISIBLE else View.GONE

        signOutButton.visibility = if (!signed) View.VISIBLE else View.GONE
        tvProfileName.visibility = if (!signed) View.VISIBLE else View.GONE
        ivProfilePic.visibility = if (!signed) View.VISIBLE else View.GONE
    }

    fun onSigned() {
        updateUI()
        val mHandler = Handler()
        signOutButton.visibility = View.GONE
        mHandler.postDelayed({
            startActivity(Intent(this, GitSearchActivity::class.java))
            mHandler.postDelayed({
                signOutButton.visibility = View.VISIBLE
            }, 3000L)
        }, 1000L)
    }

}
