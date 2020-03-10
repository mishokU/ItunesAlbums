package com.example.itunesmusic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesmusic.databinding.CellSingleTrackBinding
import com.example.itunesmusic.domain.models.AlbumModel
import com.example.itunesmusic.domain.models.SingleTrackModel
import kotlinx.android.synthetic.main.cell_single_track.view.*

class AlbumPlayListAdapter(private val onClickListener : OnTrackClickListener) : ListAdapter<SingleTrackModel,
        AlbumPlayListAdapter.AlbumPlayListViewHolder>(DiffCallback) {

    //Set index for each song
    fun bindList(it : List<SingleTrackModel>?){
        if(!it.isNullOrEmpty()){
            var number = 0
            for(song in it){
                number++
                song.id = number
            }
            submitList(it)
        }
    }


    companion object DiffCallback: DiffUtil.ItemCallback<SingleTrackModel>() {

        override fun areItemsTheSame(oldItem: SingleTrackModel, newItem: SingleTrackModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SingleTrackModel, newItem: SingleTrackModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : AlbumPlayListViewHolder {
        return AlbumPlayListViewHolder(CellSingleTrackBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AlbumPlayListViewHolder, position: Int){
        val song = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(song)
        }
        holder.bind(song)
    }

    class AlbumPlayListViewHolder(private val binding: CellSingleTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: SingleTrackModel?) {
            binding.track = track
            binding.executePendingBindings()
        }
    }

    class OnTrackClickListener(val clickListener: (track: SingleTrackModel?) -> Unit) {
        fun onClick(track: SingleTrackModel) = clickListener(track)
    }
}

