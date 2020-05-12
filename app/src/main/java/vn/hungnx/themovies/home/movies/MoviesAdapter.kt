package vn.hungnx.themovies.home.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import vn.hungnx.themovies.base.LifecycleViewHolder
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.databinding.RowMovieBinding

class MoviesAdapter(diffCallback: MovieDiffCallback, private val viewModel: MoviesViewModel) :
    PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(diffCallback) {

    class ViewHolder private constructor(private val binding: RowMovieBinding) :
        LifecycleViewHolder(binding.root) {

        private var observer:Observer<List<Int>>? = null

        fun bindData(item: Movie, viewModel: MoviesViewModel) {
            binding.viewmodel = viewModel
            observer?.let {
                viewModel.favoriteMovies.removeObserver(it)
            }
            observer = Observer {
                item.isFavorite = it.contains(item.id)
                binding.movie = item
            }
            viewModel.favoriteMovies.observe(this,this.observer!!)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position)!!, viewModel)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetached()
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title && oldItem.overview == newItem.overview && oldItem.isFavorite == newItem.isFavorite
    }

}