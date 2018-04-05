package com.k0rzun1n.testauthgit.data

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null
)