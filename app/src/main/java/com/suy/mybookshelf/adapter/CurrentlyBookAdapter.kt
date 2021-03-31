package com.suy.mybookshelf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suy.mybookshelf.R
import com.suy.mybookshelf.databinding.ItemBookBinding
import com.suy.mybookshelf.listener.CurrentlyBookListener
import com.suy.mybookshelf.room.entity.CurrentlyBook
import com.suy.mybookshelf.utils.ShelfType

class CurrentlyBookAdapter(
    private val list: List<CurrentlyBook>, private val listener: CurrentlyBookListener
) : RecyclerView.Adapter<CurrentlyBookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentlyBook: CurrentlyBook) {
            with(binding) {
                ivBook.load(currentlyBook.cover) {
                    placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.bg_loading))
                    error(ContextCompat.getDrawable(itemView.context, R.drawable.bg_loading))
                }
                tvTitleBook.text = currentlyBook.title
                tvAuthor.text = currentlyBook.author
                val popUpMenu = PopupMenu(itemView.context, btnBook)
                with(popUpMenu) {
                    inflate(R.menu.menu_bookshelf_type)
                    menu.getItem(0).isEnabled = false
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menu_want -> {
                                listener.onCurrentlyBookClicked(currentlyBook, ShelfType.WANT)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.menu_read -> {
                                listener.onCurrentlyBookClicked(currentlyBook, ShelfType.READ)
                                return@setOnMenuItemClickListener true
                            }
                            else -> return@setOnMenuItemClickListener false
                        }
                    }
                    btnBook.setOnClickListener {
                        show()
                    }
                }
            }
        }
    }
}