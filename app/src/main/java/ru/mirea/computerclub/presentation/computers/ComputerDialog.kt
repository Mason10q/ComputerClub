package ru.mirea.computerclub.presentation.computers

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import ru.mirea.computerclub.App
import ru.mirea.computerclub.R
import ru.mirea.computerclub.databinding.DialogComputerBinding
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

private const val COLLAPSED_HEIGHT = 300

class ComputerDialog : BottomSheetDialogFragment() {

    private var _binding: DialogComputerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ComputersViewModel> { viewModelFactory }

    private var computer: Computer? = null

    override fun getTheme(): Int = R.style.BottomDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App?)?.appComponent?.inject(this)
    }

    override fun onStart() {
        super.onStart()

        val density = context?.resources?.displayMetrics?.density ?: 0.0f

        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogComputerBinding.inflate(inflater, container, false).also { _binding = it }.root


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        getStartData()
        setUpViews()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getStartData() {
        computer = arguments?.getSerializable("computer", Computer::class.java)
        computer?.id?.let { viewModel.isInBasket(it) }
    }

    private fun setUpViews() {
        with(binding) {
            computerName.text = computer?.name
            computerDescription.text = computer?.description
            computerPrice.text = context?.getString(R.string.dialog_computer_price, computer?.price)

            Picasso.get().load(computer?.photoUrl)
                .placeholder(R.drawable.ic_broken)
                .error(R.drawable.ic_broken)
                .into(computerPhoto)
        }
    }

    private fun setUpObservers() {
        viewModel.isInBasket.observe(viewLifecycleOwner) { isInBasket ->
            setButtonState(isInBasket)
        }
    }

    private fun setButtonState(active: Boolean) {
        val typedValue = TypedValue()
        if(active) context?.theme?.resolveAttribute(R.attr.button_inactive_text_color, typedValue, true)
        else context?.theme?.resolveAttribute(R.attr.button_active_text_color, typedValue, true)


        with(binding.addBtn) {
            text = context?.getString(if(active) R.string.dialog_computer_remove else R.string.dialog_computer_add)
            isSelected = !active
            setTextColor(typedValue.data)
            setOnClickListener {
                computer?.id?.let { if(active) viewModel.removeFromBasket(it) else viewModel.addToBasket(it) }
                setButtonState(!active)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}