package com.kevinmedia.submission1githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UsersAdapter(private val listUsers: List<GithubUser>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val user = listUsers[position]
        viewHolder.usernameTextView.text = user.login
        viewHolder.profileUrlTextView.text = user.htmlUrl
        Glide.with(viewHolder.avatarImageView.context)
            .load(user.avatarUrl)
            .into(viewHolder.avatarImageView)

        // Set click listener
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)
        }
    }

    override fun getItemCount() = listUsers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
        val profileUrlTextView: TextView = view.findViewById(R.id.profileUrlTextView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}

