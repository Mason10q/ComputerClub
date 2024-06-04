package ru.mirea.computerclub.presentation.profile

import com.squareup.picasso.Picasso
import ru.mirea.computerclub.R
import ru.mirea.computerclub.core.BaseAdapter
import ru.mirea.computerclub.databinding.ItemHistoryComputerBinding
import ru.mirea.computerclub.domain.entities.Computer

class HistoryComputerAdapter(private val picasso: Picasso):
    BaseAdapter<Computer, ItemHistoryComputerBinding>(ItemHistoryComputerBinding::inflate) {

    override fun bindView(binding: ItemHistoryComputerBinding, item: Computer, position: Int) {
        with(binding) {
            picasso
                .load(item.photoUrl)
                .placeholder(R.drawable.ic_broken)
                .error(R.drawable.ic_broken)
                .centerCrop()
                .fit()
                .into(computerPhoto)

            computerName.text = item.name
            computerPrice.text =
                binding.root.context.getString(R.string.screen_basket_price, item.price)
        }
    }

}