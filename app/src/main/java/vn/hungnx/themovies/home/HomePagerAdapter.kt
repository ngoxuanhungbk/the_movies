package vn.hungnx.themovies.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import vn.hungnx.themovies.home.favorite.FavoriteFragment
import vn.hungnx.themovies.home.movies.MoviesFragment

class HomePagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return if (position==0){
            MoviesFragment()
        }else{
            FavoriteFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position==0){
            "MoviesFragment"
        }else{
            "FavoriteFragment"
        }
    }
}