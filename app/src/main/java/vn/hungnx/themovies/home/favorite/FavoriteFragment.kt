package vn.hungnx.themovies.home.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.hungnx.themovies.databinding.FragmentFavoriteBinding
import vn.hungnx.themovies.home.HomeFragmentDirections
import vn.hungnx.themovies.util.EventObserver

class FavoriteFragment : Fragment(){
    private lateinit var viewDataBinding:FragmentFavoriteBinding
    private val viewModel:FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentFavoriteBinding.inflate(inflater,container,false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupNavigation()
    }

    private fun setupAdapter(){
        val adapter = FavoriteMoviesAdapter(viewModel)
        viewDataBinding.recyclerView.adapter = adapter
        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupNavigation(){
        viewModel.openMovieEvent.observe(viewLifecycleOwner,EventObserver{
            val action = HomeFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it)
            findNavController().navigate(action)
        })
    }
}