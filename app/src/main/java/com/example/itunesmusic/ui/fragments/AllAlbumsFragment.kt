package com.example.itunesmusic.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.itunesmusic.R
import com.example.itunesmusic.databinding.FragmentAllAlbumsBinding
import com.example.itunesmusic.domain.converters.DELETE_ALBUMS
import com.example.itunesmusic.domain.converters.SORT
import com.example.itunesmusic.domain.converters.bindEmptyList
import com.example.itunesmusic.domain.converters.bindProgressBar
import com.example.itunesmusic.domain.models.AlbumModel
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.example.itunesmusic.domain.viewModelFactories.AlbumsViewModelFactory
import com.example.itunesmusic.ui.adapters.AllAlbumsRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar


class AllAlbumsFragment : Fragment() {


    private lateinit var binding : FragmentAllAlbumsBinding
    private lateinit var adapter : AllAlbumsRecyclerViewAdapter

    private val viewModel: AlbumsViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AlbumsViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory).get(AlbumsViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.allAlbumsProperty.observe(viewLifecycleOwner, Observer<List<AlbumModel>> {albums ->
            albums?.apply {
                adapter.customSubmitList(albums)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAlbumsBinding.inflate(inflater)

        //Data binding can observe data
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel


        initToolbar()
        initObservers()
        initRecyclerView()
        initRefresh()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initRefresh() {
        binding.refreshAlbums.setOnRefreshListener {
            viewModel.refreshAlbums()
            binding.refreshAlbums.isRefreshing = false
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.albumsToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as AppCompatActivity).menuInflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete_all_albums -> {
                viewModel.deleteAllAlbums()
                showSnackBar(DELETE_ALBUMS)
                true
            }
            R.id.sort_alphabetically -> {
                adapter.sortList()
                binding.allAlbumsRv.removeAllViews()
                showSnackBar(SORT)
                true
            }
            R.id.search_album -> {
                this.findNavController().navigate(R.id.searchFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackBar(action : String) {
        Snackbar.make(this.requireView(),action, Snackbar.LENGTH_LONG).show()
    }

    private fun initObservers() {

        //This object is waiting for clicked album and clear value
        viewModel.fullAlbumDescription.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    AllAlbumsFragmentDirections.actionAllAlbumsFragmentToOneAlbumFragment(it)
                )
                viewModel.showFullAlbumComplete()
            }
        })

        //When view model get albums that trigger adapter to populate it
        viewModel.allAlbumsProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.customSubmitList(it)
            }
        })

        viewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            bindProgressBar(binding.allAlbumsPb, it)
            bindEmptyList(binding.allAlbumsTv, it)
        })
    }

    private fun initRecyclerView() {
        adapter = AllAlbumsRecyclerViewAdapter(AllAlbumsRecyclerViewAdapter.OnAlbumClickListener {
            viewModel.showFullAlbum(it)
        })
        binding.allAlbumsRv.adapter = adapter
    }

}
