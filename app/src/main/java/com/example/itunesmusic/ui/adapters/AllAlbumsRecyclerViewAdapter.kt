package com.example.itunesmusic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesmusic.databinding.CellAlbumBinding
import com.example.itunesmusic.domain.models.AlbumModel
import java.util.*

class AllAlbumsRecyclerViewAdapter(private val onClickListener : OnAlbumClickListener) : ListAdapter<AlbumModel,
        AllAlbumsRecyclerViewAdapter.AlbumViewHolder>(DiffCallback) {

    //We need this tmp list for sorting
    private var list = currentList

    fun customSubmitList(it : List<AlbumModel>){
        list = it
        submitList(it)
    }

    //For animation and performance
    companion object DiffCallback: DiffUtil.ItemCallback<AlbumModel>() {
        override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : AlbumViewHolder {
        return AlbumViewHolder(CellAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int){
        val cityProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(cityProperty)
        }
        holder.bind(cityProperty)
    }

    fun sortList() {
        list.sortBy { it.album_name }
        submitList(list)
    }


    class AlbumViewHolder(private val binding: CellAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumModel?) {
            binding.album = album
            binding.executePendingBindings()
        }
    }

    class OnAlbumClickListener(val clickListener: (album: AlbumModel?) -> Unit) {
        fun onClick(album: AlbumModel) = clickListener(album)
    }

}

