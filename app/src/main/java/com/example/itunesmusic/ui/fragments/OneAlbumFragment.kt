package com.example.itunesmusic.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.itunesmusic.R
import com.example.itunesmusic.databinding.FragmentOneAlbumBinding
import com.example.itunesmusic.domain.converters.bindEmptyList
import com.example.itunesmusic.domain.converters.bindProgressBar
import com.example.itunesmusic.domain.models.AlbumModel
import com.example.itunesmusic.domain.viewModel.OneAlbumViewModel
import com.example.itunesmusic.domain.viewModelFactories.OneAlbumViewModelFactory
import com.example.itunesmusic.ui.adapters.AlbumPlayListAdapter


class OneAlbumFragment : Fragment() {

    private lateinit var binding : FragmentOneAlbumBinding
    private lateinit var albumModel: AlbumModel
    //private lateinit var adapter : AlbumPlayListAdapter

    private val viewModel : OneAlbumViewModel by lazy {

        getAlbumFromBundle()

        val application = requireNotNull(this.activity).application
        ViewModelProvider(this,OneAlbumViewModelFactory(albumModel,application))
            .get(OneAlbumViewModel::class.java)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            bindProgressBar(binding.albumPb, it)
            bindEmptyList(binding.albumTv, it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOneAlbumBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initToolbar()
        initPlayList()
        initRefresh()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initRefresh() {
        binding.songsRefresh.setOnRefreshListener {
            viewModel.refreshSongs()
            binding.songsRefresh.isRefreshing = false
        }
    }

    private fun getAlbumFromBundle() {
        //Get album from bundle via all albums fragment
        albumModel = OneAlbumFragmentArgs.fromBundle(arguments!!).album
        binding.album = albumModel
    }

    private fun initToolbar() {
        ((activity) as AppCompatActivity).setSupportActionBar(binding.fullAlbumToolbar)
        ((activity) as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Back button to the album list
        binding.fullAlbumToolbar.setNavigationOnClickListener {
            this.findNavController().navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).menuInflater.inflate(R.menu.album_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete_all_current_songs -> {
                viewModel.deleteAllSongs(albumModel.collectionId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initPlayList() {
        binding.songsRv.adapter = AlbumPlayListAdapter(AlbumPlayListAdapter.OnTrackClickListener {
            println(it)
        })
    }

}
