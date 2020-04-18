package com.example.itunesmusic.ui.album

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.itunesmusic.R
import com.example.itunesmusic.databinding.FragmentOneAlbumBinding
import com.example.itunesmusic.di.utils.Injectable
import com.example.itunesmusic.di.utils.injectViewModel
import com.example.itunesmusic.domain.converters.bindEmptyList
import com.example.itunesmusic.domain.converters.bindProgressBar
import com.example.itunesmusic.domain.models.AlbumModel
import com.example.itunesmusic.domain.viewModel.OneAlbumViewModel
import javax.inject.Inject


class OneAlbumFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: OneAlbumViewModel

    private lateinit var binding : FragmentOneAlbumBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            bindProgressBar(binding.albumPb, it)
            bindEmptyList(binding.songsToolbar, it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOneAlbumBinding.inflate(inflater)

        viewModel = injectViewModel(viewModelFactory)
        viewModel.collectionId = getAlbumId()

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

    private fun getAlbumId() : Int {
        //Get album from bundle via all albums fragment
        val album = OneAlbumFragmentArgs.fromBundle(
            requireArguments()
        ).album
        binding.album = album
        return album.collectionId
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
                viewModel.deleteAllSongs(viewModel.collectionId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initPlayList() {
        binding.songsRv.adapter =
            AlbumPlayListAdapter(
                AlbumPlayListAdapter.OnTrackClickListener {
                    println(it)
                })
    }

}
