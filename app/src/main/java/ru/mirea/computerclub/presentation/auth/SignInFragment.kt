package ru.mirea.computerclub.presentation.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.mirea.computerclub.App
import ru.mirea.computerclub.core.isValidEmail
import ru.mirea.computerclub.core.toastLong
import ru.mirea.computerclub.databinding.FragmentSigninBinding
import javax.inject.Inject

class SignInFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

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
    ): View = FragmentSigninBinding.inflate(inflater, container, false).also { _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpViews()
    }

    private fun setUpViews() {
        with(binding) {
            submitBtn.setOnClickListener{ _ ->
                if(validateEdits()) {
                    toastLong("Заполните поля!")
                    return@setOnClickListener
                }

                if(!emailEdit.text.isValidEmail()){
                    toastLong("Почта введена неверно")
                    return@setOnClickListener
                }

                viewModel.signIn(binding.emailEdit.text.toString(), binding.passwordEdit.text.toString())
            }
        }
    }

    private fun validateEdits() = with(binding) {
        emailEdit.text.isNullOrEmpty() ||
        passwordEdit.text.isNullOrEmpty()
    }

    private fun setUpObservers() {
        viewModel.error.observe(viewLifecycleOwner) { e ->
            toastLong(e.message)
        }

        viewModel.userId.observe(viewLifecycleOwner) { userId ->
            sp?.edit()?.putInt("userId", userId ?: -1)?.apply()
            //findNavController().navigate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}