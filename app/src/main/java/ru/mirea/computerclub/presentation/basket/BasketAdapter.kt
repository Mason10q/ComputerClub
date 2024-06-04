package ru.mirea.computerclub.presentation.basket

import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import ru.mirea.computerclub.R
import ru.mirea.computerclub.core.BasePagingAdapter
import ru.mirea.computerclub.databinding.ItemBasketBinding
import ru.mirea.computerclub.domain.entities.Computer
import javax.inject.Inject

class BasketAdapter @Inject constructor(private val picasso: Picasso) :
    BasePagingAdapter<Computer, ItemBasketBinding>(DIFF_CALLBACK, ItemBasketBinding::inflate) {

    private var onDelete: ((Int, Int) -> Unit)? = null

    override fun bindView(binding: ItemBasketBinding, item: Computer, position: Int) {
        with(binding) {
            picasso
                .load(item.photoUrl)
                .placeholder(R.drawable.ic_broken)
                .error(R.drawable.ic_broken)
                .centerCrop()
                .fit()
                .into(computerPhoto)

            computerName.text = item.name
            computerPrice.text = binding.root.context.getString(R.string.screen_basket_price, item.price)

            removeBtn.setOnClickListener{
                onDelete?.invoke(item.id, position)
                notifyItemRemoved(position)
            }
        }
    }

    fun setOnDeleteClick(listener: (Int, Int) -> Unit) {
        onDelete = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Computer>() {
            override fun areItemsTheSame(oldItem: Computer, newItem: Computer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Computer, newItem: Computer): Boolean =
                oldItem == newItem
        }
    }
}