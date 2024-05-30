package ru.mirea.computerclub.presentation.computers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.mirea.computerclub.App
import ru.mirea.computerclub.R
import ru.mirea.computerclub.core.FooterLoadStateAdapter
import ru.mirea.computerclub.core.addItemMargins
import ru.mirea.computerclub.databinding.FragmentComputersBinding
import javax.inject.Inject

class ComputersFragment: Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ComputersViewModel> { viewModelFactory }

    private var _binding: FragmentComputersBinding? = null
    private val binding get() = _binding!!

   @Inject lateinit var adapter: ComputersAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App?)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentComputersBinding.inflate(inflater, container, false).also { _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getAllComputers()
        }
        setUpEmptyView()
        setUpObservers()
        setUpViews()


    }

    private fun setUpViews() {
        with(binding) {
            computerRecycler.also {
                it.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter())
                it.addItemMargins(15, 10)
            }

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    lifecycleScope.launch {
                        viewModel.searchComputers(newText.toString())
                    }
                    return true
                }
            })

            adapter.addLoadStateListener { loadState ->
                if (loadState.prepend.endOfPaginationReached) {
                    binding.emptyLayout.root.isVisible = adapter.itemCount < 1
                }
            }
        }
    }

    private fun setUpEmptyView() {
        with(binding.emptyLayout) {
            notifyText.text = context?.getString(R.string.screen_computer_empty_title)
            notifyDescription.text = context?.getString(R.string.screen_computer_empty_description)
            notifyImage.setImageDrawable(context?.getDrawable(R.drawable.ic_broken))
        }
    }

    private fun setUpObservers() {
        viewModel.computers.observe(viewLifecycleOwner){ data ->
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