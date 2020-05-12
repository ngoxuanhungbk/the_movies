package vn.hungnx.themovies.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.row_movie.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.databinding.FragmentMoviesBinding
import vn.hungnx.themovies.home.HomeFragmentDirections
import vn.hungnx.themovies.util.EventObserver

class MoviesFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentMoviesBinding
    private val viewModel:MoviesViewModel by viewModel()
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false).apply {
            this.lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
        setupNavigation()
    }

    private fun setupAdapter() {
        moviesAdapter = MoviesAdapter(MovieDiffCallback(), viewModel)
        viewDataBinding.listView.adapter = moviesAdapter
        viewModel.itemList.observe(viewLifecycleOwner, Observer {pagedList->
            moviesAdapter.submitList(pagedList)
        })
    }

    private fun setupNavigation(){
        viewModel.openMovieEvent.observe(viewLifecycleOwner,EventObserver{
            navigateToDetail(it)
        })
    }

    private fun navigateToDetail(movie:Movie){
        val extras = FragmentNavigatorExtras(
            viewModel.viewSelected.img_cover to movie.getFullPosterPath()!!,
            viewModel.viewSelected.tv_title to movie.title
        )
        val action = HomeFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action,extras)
    }
}