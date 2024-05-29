package ru.mirea.computerclub.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment = when(position) {
            0 -> SignInFragment()
            1 -> SignUpFragment()
            else -> SignInFragment()
        }

    override fun getItemCount(): Int = 2

}