package com.k0rzun1n.testauthgit

import com.k0rzun1n.testauthgit.data.GitUserSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by anupamchugh on 09/01/17.
 */

interface APIInterface {

    @GET("/search/users?per_page=30")
    fun doFindUsers(@Query("q") patt: String, @Query("page") page: Int): Call<GitUserSearch>
}
