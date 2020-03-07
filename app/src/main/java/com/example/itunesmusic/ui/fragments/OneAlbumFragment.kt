package com.example.itunesmusic.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesmusic.R
import com.example.itunesmusic.databinding.FragmentOneAlbumBinding


class OneAlbumFragment : Fragment() {

    private lateinit var binding : FragmentOneAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOneAlbumBinding.inflate(inflater)



        return binding.root
    }

}
