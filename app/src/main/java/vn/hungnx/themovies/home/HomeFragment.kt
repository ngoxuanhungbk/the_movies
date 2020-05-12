package vn.hungnx.themovies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.hungnx.themovies.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){
    private lateinit var viewDataBinding:FragmentHomeBinding
    private val viewModel:HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentHomeBinding.inflate(inflater,container,false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            print("$it")
        })
    }

    private fun setupAdapter(){
        val adapter = HomePagerAdapter(childFragmentManager)
        viewDataBinding.viewPager.adapter = adapter
        viewDataBinding.tabLayout.setupWithViewPager(viewDataBinding.viewPager)
    }
}