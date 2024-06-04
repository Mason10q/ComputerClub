package ru.mirea.computerclub.presentation.basket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import kotlinx.coroutines.launch
import ru.mirea.computerclub.App
import ru.mirea.computerclub.R
import ru.mirea.computerclub.core.FooterLoadStateAdapter
import ru.mirea.computerclub.core.addItemMargins
import ru.mirea.computerclub.core.toastLong
import ru.mirea.computerclub.databinding.FragmentBasketBinding
import javax.inject.Inject

class BasketFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<BasketViewModel> { viewModelFactory }

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var adapter: BasketAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App?)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBasketBinding.inflate(inflater, container, false).also { _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpEmptyView()
        setUpViews()

        lifecycleScope.launch {
            viewModel.getBasket()
        }
    }

    private fun setUpViews() {
        binding.basketRecycler.also {
            it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
            it.addItemMargins(30, 40)
        }

        adapter.setOnDeleteClick { id, pos ->
            viewModel.removeFromBasket(id)
            binding.buyButton.text = context?.getString(R.string.screen_basket_buy, adapter.snapshot().items.filter { it.id != id }.sumOf { it.price })
        }

        adapter.addLoadStateListener { loadState ->
            if(loadState.prepend.endOfPaginationReached) {
                binding.buyButton.isVisible = adapter.itemCount > 0
                binding.emptyLayout.root.isVisible = adapter.itemCount < 1
            }

            binding.buyButton.text = context?.getString(R.string.screen_basket_buy, adapter.snapshot().items.sumOf { it.price })
        }

        binding.buyButton.setOnClickListener {
            binding.buyButton.isVisible = false
            binding.emptyLayout.root.isVisible = true
            viewModel.buyComputers(adapter.snapshot().items.map { it.id })
            toastLong("Успешно оплачено!")

            lifecycleScope.launch {
                adapter.submitData(PagingData.empty())
            }
        }
    }

    private fun setUpEmptyView() {
        with(binding.emptyLayout) {
            notifyText.text = context?.getString(ru.mirea.computerclub.R.string.screen_basket_empty_title)
            notifyDescription.text = context?.getString(ru.mirea.computerclub.R.string.screen_basket_empty_description)
            notifyImage.setImageDrawable(context?.getDrawable(R.drawable.ic_nothing_found))
        }
    }

    private fun setUpObservers() {
        viewModel.basket.observe(viewLifecycleOwner) { data ->
            lifecycleScope.launch {
                adapter.submitData(data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}