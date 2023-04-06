package com.kevinmedia.submission1githubuser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchUsers(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<GithubUser>
)

@Parcelize
data class GithubUser(

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    ) : Parcelable

data class DetailUser(
    var login: String,
    var name: String,
    @SerializedName("html_url")
    var htmlUrl: String?,

    @SerializedName("public_repos")
    var publicRepos: String,

    var followers: String,
    var following: String,

    @SerializedName("avatar_url")
    var avatarUrl: String
)
