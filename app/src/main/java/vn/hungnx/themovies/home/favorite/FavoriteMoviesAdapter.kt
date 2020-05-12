package vn.hungnx.themovies.home.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.databinding.RowFavoriteMovieBinding

class FavoriteMoviesAdapter(private val viewModel: FavoriteViewModel) :
    ListAdapter<Movie, FavoriteMoviesAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position),viewModel)
    }

    class ViewHolder private constructor(private val viewBinding: RowFavoriteMovieBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(movie: Movie, viewModel: FavoriteViewModel) {
            viewBinding.movie = movie
            viewBinding.viewmodel = viewModel
            viewBinding.executePendingBindings()
        }

        companion object {
            fun fromParent(parent: ViewGroup): ViewHolder {
                val binding = RowFavoriteMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }
}