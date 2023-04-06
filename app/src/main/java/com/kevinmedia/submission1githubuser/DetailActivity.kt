package com.kevinmedia.submission1githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.kevinmedia.submission1githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        if (detailViewModel.userlogin.isEmpty()) {
            val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
            detailViewModel.userlogin = user.login.toString()
        }

        detailViewModel.detailuser.observe(this, {
            setDetailUser(it)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
            resetDetailUser()
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setDetailUser(detailuser: DetailUser) {
        binding.nameView.text = detailuser.name ?: " - "
        binding.countRepoView.text = detailuser.publicRepos ?: " - "
        binding.urlUser.text = detailuser.htmlUrl ?: " - "
        binding.countFollowersView.text = detailuser.followers
        binding.countFollowingView.text = detailuser.following
        val actionBar = supportActionBar

        actionBar!!.title = detailuser.login.toUsernameFormat()
        Glide.with(this)
            .load(detailuser.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgAvatar)
    }

    private fun resetDetailUser() {
        binding.nameView.text = ""
        binding.countRepoView.text = ""
        binding.urlUser.text = ""
        binding.countFollowersView.text = ""
        binding.countFollowingView.text = ""
        val actionBar = supportActionBar
        actionBar!!.title = this.title
        Glide.with(this)
            .load(R.drawable.ic_launcher_foreground)
            .into(binding.imgAvatar)
    }


    private fun String.toUsernameFormat(): String {
        return StringBuilder("@").append(this).toString()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}