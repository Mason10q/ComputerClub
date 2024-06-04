package ru.mirea.computerclub.presentation.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.mirea.computerclub.App
import ru.mirea.computerclub.R
import ru.mirea.computerclub.core.FooterLoadStateAdapter
import ru.mirea.computerclub.core.addItemMargins
import ru.mirea.computerclub.databinding.FragmentProfileBinding
import ru.mirea.computerclub.domain.entities.User
import ru.mirea.computerclub.presentation.auth.AuthActivity
import javax.inject.Inject

class ProfileFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var historyAdapter: HistoryAdapter

    private var sp: SharedPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App?)?.appComponent?.inject(this)
        sp = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(inflater, container, false).also{ _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpViews()

        viewModel.getUserData()
        viewModel.getPurchaseHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews() {
        setUpEmptyView()

        binding.historyRecycler.also {
            it.adapter = historyAdapter.withLoadStateFooter(FooterLoadStateAdapter())
            it.addItemMargins(15, 15)
        }

        binding.exitButton.setOnClickListener {
            sp?.edit()?.remove("userId")?.apply()
            startActivity(Intent(context, AuthActivity::class.java))
        }

        historyAdapter.addEmptyLayout(binding.emptyLayout.root)
    }


    private fun setUpUserData(user: User) {
        with(binding) {
            email.text = user.email
            name.text = user.name
            birthDate.text = user.birthDate
        }
    }

    private fun setUpEmptyView() {
        with(binding.emptyLayout) {
            notifyText.text = context?.getString(R.string.screen_profile_history_empty_title)
            notifyDescription.text = context?.getString(R.string.screen_profile_history_empty_description)
            notifyImage.setImageDrawable(context?.getDrawable(R.drawable.ic_nothing_found))
        }
    }

    private fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner) {
            setUpUserData(it)
        }

        viewModel.purchase.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                historyAdapter.submitData(it)
            }
        }
    }

}