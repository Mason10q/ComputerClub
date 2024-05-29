package ru.mirea.computerclub.presentation.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.mirea.computerclub.R
import ru.mirea.computerclub.databinding.FragmentAuthBinding

class AuthFragment: Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private var sp: SharedPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sp = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).also {
            if(it.getInt("userId", -1) > 0) {
                //findNavController().navigate()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAuthBinding.inflate(layoutInflater).also { _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        binding.authPager.adapter = activity?.let { AuthPagerAdapter(it) }

        TabLayoutMediator(binding.authTabs, binding.authPager) { tab, position ->
            tab.text = when(position) {
                0 -> context?.getString(R.string.screen_signin_enter)
                1 -> context?.getString(R.string.screen_signup_register)
                else -> ""
            }
        }.attach()
    }
}