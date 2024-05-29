package ru.mirea.computerclub.presentation.auth

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.mirea.computerclub.App
import ru.mirea.computerclub.core.isValidEmail
import ru.mirea.computerclub.core.toastLong
import ru.mirea.computerclub.databinding.FragmentSignupBinding
import javax.inject.Inject

class SignUpFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private var sp: SharedPreferences? = null

    private var date: String? = null

    private val datePicker =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            date = ("$dayOfMonth.$month.$year");
            binding.datePickBtn.text = date;
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App?)?.appComponent?.inject(this)
        sp = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignupBinding.inflate(inflater, container, false).also { _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpViews()
    }

    private fun setUpViews() {
        with(binding) {

            datePickBtn.setOnClickListener { _ ->
                context?.let {
                    DatePickerDialog(it, datePicker, 2022, 11, 11).show()
                }
            }

            registerBtn.setOnClickListener {
                if (validateEdits()) {
                    toastLong("Заполните поля!")
                    return@setOnClickListener
                }

                if (!emailEdit.text.isValidEmail()) {
                    toastLong("Почта введена не по формату!")
                    return@setOnClickListener
                }

                if (passwordEdit.text.toString() != passwordRepeatEdit.text.toString()) {
                    toastLong("Пароли не совпадают")
                    return@setOnClickListener
                }

                viewModel.signUp(emailEdit.text.toString(), passwordEdit.text.toString(), nameEdit.text.toString(), date!!)
            }
        }
    }

    private fun validateEdits(): Boolean =
        with(binding) {
            nameEdit.text.isNullOrEmpty() ||
            emailEdit.text.isNullOrEmpty() ||
            passwordEdit.text.isNullOrEmpty() ||
            passwordRepeatEdit.text.isNullOrEmpty() ||
            date.isNullOrEmpty()
        }

    private fun setUpObservers() {
        viewModel.error.observe(viewLifecycleOwner) { e ->
            toastLong(e.message)
        }

        viewModel.userId.observe(viewLifecycleOwner) { userId ->
            toastLong("Успешно зарегестрирован")
            sp?.edit()?.putInt("userId", userId ?: -1)?.apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}