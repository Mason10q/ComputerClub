package ru.mirea.computerclub.presentation.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ru.mirea.computerclub.MainActivity
import ru.mirea.computerclub.R
import ru.mirea.computerclub.databinding.ActivityAuthBinding

class AuthActivity: AppCompatActivity() {


    private var sp: SharedPreferences? = null

    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpViews()

        sp = this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).also {
            if(it.getInt("userId", -1) > 0) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun setUpViews() {
        binding.authPager.adapter = AuthPagerAdapter(this)

        TabLayoutMediator(binding.authTabs, binding.authPager) { tab, position ->
            tab.text = when(position) {
                0 -> this.getString(R.string.screen_signin_enter)
                1 -> this.getString(R.string.screen_signup_register)
                else -> ""
            }
        }.attach()
    }
}