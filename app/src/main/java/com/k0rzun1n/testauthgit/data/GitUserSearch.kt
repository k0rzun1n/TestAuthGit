package com.k0rzun1n.testauthgit.data

import com.google.gson.annotations.SerializedName

data class GitUserSearch(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)