package com.example.recovery.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.model.entity.FavMovie
import com.example.recovery.data.model.movie.Movie
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.ui.search.repo.SearchRepo
import com.example.recovery.ui.search.viewmodel.SearchViewModel
import com.example.recovery.ui.search.viewmodel.SearchViewModelFactory
import com.example.recovery.utilites.ClickHandler
import com.example.recovery.utilites.Connection
import com.example.recovery.utilites.GridAdapter


class SearchFragment : Fragment(),ClickHandler {

    private lateinit var recyclerView :RecyclerView
    private lateinit var searchView :SearchView
    private lateinit var emptyText :TextView


    private lateinit var searchViewModel: SearchViewModel
    private lateinit var gridAdapter: GridAdapter
    private var movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchViewModelFactory = SearchViewModelFactory(SearchRepo(ApiClient))
        searchViewModel = ViewModelProvider(this, searchViewModelFactory).get(SearchViewModel::class.java)

    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)



        // initialize the views
        initialize(view)



        if(Connection.isNetworkAvailable(requireContext())) {

            // if there is network get popular movies to show
            searchViewModel.getPopularMovies()
            searchView.visibility = View.VISIBLE
            emptyText.text = "No results found."
        }

        else{
            // set the text to no internet and hide the recycle view
            searchView.visibility = View.GONE
            emptyText.text = "No internet connection, please try again later."
            emptyText.visibility = View.VISIBLE
        }



        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty())
                    searchViewModel.getMovies(newText)
                else
                    searchViewModel.getPopularMovies()
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    searchViewModel.getMovies(query)
                }
                searchView.clearFocus()
                return true
            }

        })




        searchViewModel.movies.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                movies.clear()
                movies.addAll(it)
                // remove empty results text
                recyclerView.visibility = View.VISIBLE
                emptyText.visibility = View.GONE
            }
            else{
                movies.clear()
                // show empty results text
                recyclerView.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
            }
            gridAdapter.notifyDataSetChanged()

        }

        return view
    }

    private fun initialize(view: View) {
        recyclerView = view.findViewById(R.id.search_rv)
        searchView = view.findViewById(R.id.searchView)
        emptyText = view.findViewById(R.id.empty_result)

        gridAdapter=GridAdapter(movies,this)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = gridAdapter
    }

    override fun onMovieClick(movie:Movie) {
       val action=SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
           FavMovie(movie.backdrop_path,movie.poster_path,movie.original_title,movie.original_language,movie.runtime
               ,movie.vote_average,movie.overview,movie.release_date,movie.id)
       )
        findNavController().navigate(action)
    }

}