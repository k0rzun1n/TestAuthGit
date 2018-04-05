package com.k0rzun1n.testauthgit

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.Toast
import com.k0rzun1n.testauthgit.data.GitUserSearch
import com.k0rzun1n.testauthgit.data.ItemsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_git_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GitSearchActivity : AppCompatActivity() {

    var mResultsList: MutableList<ItemsItem?>? = null
    var mPage = 1
    val mApiInterface = APIClient.client?.create(APIInterface::class.java)
    val mHandler = Handler()
    var mScrollPreLast = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_search)

        val pa = (application as MyApp).profAuth
        tvProfileNameDrawer.text = pa?.getName()
        Picasso.get().load(pa?.getPicLink()).into(ivProfilePicDrawer)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearchString.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed({
                    mResultsList?.clear()
                    mTotalResultsCount = 0
                    mScrollPreLast = 0
                    searchGit()
                }, 600)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        lvSearchResults.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(lv: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (totalItemCount == mTotalResultsCount)
                    return
                val lastItem = firstVisibleItem + visibleItemCount
                if (lastItem == totalItemCount && mScrollPreLast != lastItem) {
                    mScrollPreLast = lastItem
                    mPage++
                    Toast.makeText(lv?.context, "Loading page " + mPage, Toast.LENGTH_SHORT).show()
                    val callSearchNext = mApiInterface?.doFindUsers(etSearchString.text.toString(), mPage)
                    callSearchNext?.enqueue(object : Callback<GitUserSearch> {
                        override fun onResponse(call: Call<GitUserSearch>?, response: Response<GitUserSearch>?) {
                            response?.body()?.items?.forEach { mResultsList?.add(it)}
                            (lvSearchResults.adapter as GitListAdapter).notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<GitUserSearch>?, t: Throwable?) {
                            call?.cancel()
                        }
                    })
                }
            }

            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {}
        })
        searchGit()
    }

    var mTotalResultsCount = 0
    fun searchGit() {
        mPage = 1
        mResultsList = mResultsList ?: MutableList<ItemsItem?>(0, { null })
        mApiInterface
                ?.doFindUsers(etSearchString.text.toString(), mPage)
                ?.enqueue(object : Callback<GitUserSearch> {
                    override fun onResponse(call: Call<GitUserSearch>?, response: Response<GitUserSearch>?) {
                        response?.body()?.items?.forEach { mResultsList?.add(it)}
                        mTotalResultsCount = response?.body()?.totalCount ?: 0
                        Toast.makeText(applicationContext, "Total results: " + mTotalResultsCount, Toast.LENGTH_SHORT).show()
                        if (lvSearchResults.adapter == null)
                            lvSearchResults.adapter = GitListAdapter(mResultsList!!, applicationContext)
                        (lvSearchResults.adapter as GitListAdapter).notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<GitUserSearch>?, t: Throwable?) {
                        call?.cancel()
                    }
                })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etSearchString.windowToken, 0)
        }
        etSearchString.clearFocus()
        return super.onTouchEvent(event)
    }

}
