package ru.mirea.computerclub.presentation.computers

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import ru.mirea.computerclub.R
import ru.mirea.computerclub.core.BasePagingAdapter
import ru.mirea.computerclub.databinding.ItemComputerBinding
import ru.mirea.computerclub.domain.entities.Computer

class ComputersAdapter(private val picasso: Picasso)
    : BasePagingAdapter<Computer, ItemComputerBinding>(DIFF_CALLBACK, ItemComputerBinding::inflate) {

    private var onItemClick: ((Computer) -> Unit)? = null

    override fun bindView(binding: ItemComputerBinding, item: Computer, position: Int) {
        with(binding) {
            compName.text = item.name
            compDesc.text = item.description
            picasso
                .load(item.photoUrl)
                .placeholder(R.drawable.ic_broken)
                .error(R.drawable.ic_broken)
                .centerCrop()
                .fit()
                .into(compImage)
        }
    }

    override fun onClick(view: View, item: Computer, position: Int) {
        onItemClick?.invoke(item)
    }

    fun setOnItemClick(listener: (Computer) -> Unit) {
        onItemClick = listener
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