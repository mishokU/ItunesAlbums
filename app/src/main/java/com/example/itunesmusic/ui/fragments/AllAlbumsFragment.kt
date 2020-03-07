package com.example.itunesmusic.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.itunesmusic.R
import com.example.itunesmusic.databinding.FragmentAllAlbumsBinding
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.example.itunesmusic.domain.viewModelFactories.AlbumsViewModelFactory
import com.example.itunesmusic.ui.adapters.AllAlbumsRecyclerViewAdapter


class AllAlbumsFragment : Fragment() {

    private lateinit var binding : FragmentAllAlbumsBinding
    private lateinit var viewModel: AlbumsViewModel
    private lateinit var adapter : AllAlbumsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAlbumsBinding.inflate(inflater)

        initViewModel()
        initObservers()
        initRecyclerView()

        return binding.root
    }

    private fun initObservers() {

        adapter = AllAlbumsRecyclerViewAdapter(AllAlbumsRecyclerViewAdapter.OnAlbumClickListener {
            it?.let {
                viewModel.showFullAlbum(it)
            }
        })

        viewModel.fullAlbumDescription.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(R.id.oneAlbumFragment)
            viewModel.showFullAlbumComplete()
        })

        viewModel.allAlbumsProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initRecyclerView() {
        binding.allAlbumsRv.adapter = adapter

    }

    private fun initViewModel() {
        val application = requireNotNull(activity).application
        val viewModelFactory = AlbumsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AlbumsViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }


}
