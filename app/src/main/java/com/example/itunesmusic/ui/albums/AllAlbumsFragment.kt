package com.example.itunesmusic.ui.albums

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.itunesmusic.R
import com.example.itunesmusic.databinding.FragmentAllAlbumsBinding
import com.example.itunesmusic.di.utils.Injectable
import com.example.itunesmusic.di.utils.injectViewModel
import com.example.itunesmusic.domain.converters.*
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class AllAlbumsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel : AlbumsViewModel

    private lateinit var binding : FragmentAllAlbumsBinding
    private lateinit var adapter : AllAlbumsRecyclerViewAdapter

//    private val viewModel: AlbumsViewModel by lazy {
//        val application = requireNotNull(this.activity).application
//        val viewModelFactory = AlbumsViewModelFactory(application)
//        ViewModelProvider(this, viewModelFactory).get(AlbumsViewModel::class.java)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.allAlbumsProperty.observe(viewLifecycleOwner, Observer { albums ->
            albums.apply {
                adapter.customSubmitList(albums)
                bindProgressBar(binding.allAlbumsPb, NetworkStatus.DONE)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAlbumsBinding.inflate(inflater)

        viewModel = injectViewModel(viewModelFactory)

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
                showSnackBar(resources.getString(R.string.delete_all_albums_from_db))
                true
            }
            R.id.delete_all_songs ->{
                viewModel.deleteAllSongs()
                showSnackBar(resources.getString(R.string.delete_all_songs_from_db))
                true
            }
            R.id.sort_alphabetically -> {
                adapter.sortList()
                binding.allAlbumsRv.removeAllViews()
                showSnackBar(resources.getString(R.string.sort_albums_by_name))
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
        observeFullDescription()
        observeAllAlbums()
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        viewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            bindProgressBar(binding.allAlbumsPb, it)
            bindEmptyList(binding.albumsToolbar, it)
        })
    }

    private fun observeAllAlbums() {
        viewModel.allAlbumsProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.customSubmitList(it)
            }
        })
    }

    private fun observeFullDescription() {
        viewModel.fullAlbumDescription.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    AllAlbumsFragmentDirections.actionAllAlbumsFragmentToOneAlbumFragment(
                        it
                    )
                )
                viewModel.showFullAlbumComplete()
            }
        })
    }

    private fun initRecyclerView() {
        adapter = AllAlbumsRecyclerViewAdapter(
            AllAlbumsRecyclerViewAdapter.OnAlbumClickListener {
                viewModel.showFullAlbum(it)
            })
        binding.allAlbumsRv.adapter = adapter
    }

}
