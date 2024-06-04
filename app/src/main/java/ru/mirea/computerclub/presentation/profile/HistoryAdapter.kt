package ru.mirea.computerclub.presentation.profile

import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import ru.mirea.computerclub.core.BasePagingAdapter
import ru.mirea.computerclub.core.addItemMargins
import ru.mirea.computerclub.databinding.ItemHistoryBinding
import ru.mirea.computerclub.domain.entities.Purchase
import javax.inject.Inject

class HistoryAdapter @Inject constructor(private val picasso: Picasso) :
    BasePagingAdapter<Purchase, ItemHistoryBinding>(DIFF_CALLBACK, ItemHistoryBinding::inflate) {


    override fun bindView(binding: ItemHistoryBinding, item: Purchase, position: Int) {
        with(binding) {
            date.text = item.date
            computerRecycler.adapter = HistoryComputerAdapter(picasso).apply { this.addItems(item.computers) }
            computerRecycler.addItemMargins(20, 10)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Purchase>() {
            override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean =
                oldItem.computers.hashCode() == newItem.computers.hashCode()

            override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean =
                oldItem == newItem
        }
    }
}