package com.zntkr.secondtry.view

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.secondtry.R
import com.zntkr.secondtry.adapter.ItemAdapter
import com.zntkr.secondtry.viewmodel.ItemViewModel
import com.zntkr.secondtry.viewmodel.MyFactory
import kotlinx.android.synthetic.main.activity_details.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {



    // values and variables
    private lateinit var viewModel: ItemViewModel
    private lateinit var itemAdapter: ItemAdapter
    var job: Job? = null
    private val editSearch by lazy { findViewById<EditText>(R.id.et_search) }
    private val recyView by lazy { findViewById<RecyclerView>(R.id.recyView) }
    private val movieButton by lazy { findViewById<Button>(R.id.movieButton) }
    private val musicButton by lazy { findViewById<Button>(R.id.musicButton) }
    private val appButton by lazy { findViewById<Button>(R.id.appsButton) }
    private val podcastButton by lazy { findViewById<Button>(R.id.podcastButton) }
    lateinit var kind: CharSequence

    // time delay for search
    companion object {
        const val searhTimeDelay = 500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Fixing backgrounds for buttons
        musicButton.setBackgroundColor(Color.WHITE)
        movieButton.setBackgroundColor(Color.WHITE)
        podcastButton.setBackgroundColor(Color.WHITE)
        appButton.setBackgroundColor(Color.WHITE)

        // Function calls
        setRecyView()
        initViewModel()

        // random items for start
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected) {
            viewModel.ApiCallForItem("na","movie")
            viewModel.ApiCallForItem("na","music")
            viewModel.ApiCallForItem("na","software")
            viewModel.ApiCallForItem("na","podcast")
        }

        //Button calls to filter search results
        movieButton.setOnClickListener {
            editSearch.text.clear()
            kind = "movie"
            movieButton.setBackgroundColor(Color.GREEN)
            musicButton.setBackgroundColor(Color.WHITE)
            appButton.setBackgroundColor(Color.WHITE)
            podcastButton.setBackgroundColor(Color.WHITE)
            searchQuery()
        }
        musicButton.setOnClickListener {
            editSearch.text.clear()
            kind = "song"
            movieButton.setBackgroundColor(Color.WHITE)
            musicButton.setBackgroundColor(Color.GREEN)
            appButton.setBackgroundColor(Color.WHITE)
            podcastButton.setBackgroundColor(Color.WHITE)
            searchQuery()
        }
        appButton.setOnClickListener {
            editSearch.text.clear()
            kind = "software"
            movieButton.setBackgroundColor(Color.WHITE)
            musicButton.setBackgroundColor(Color.WHITE)
            appButton.setBackgroundColor(Color.GREEN)
            podcastButton.setBackgroundColor(Color.WHITE)
            searchQuery()
        }
        podcastButton.setOnClickListener {
            editSearch.text.clear()
            kind = "podcast"
            movieButton.setBackgroundColor(Color.WHITE)
            musicButton.setBackgroundColor(Color.WHITE)
            appButton.setBackgroundColor(Color.WHITE)
            podcastButton.setBackgroundColor(Color.GREEN)
            searchQuery()
        }
    }

    // setting recycler view
    private fun setRecyView() {
        itemAdapter = ItemAdapter(this)
        recyView.apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, MyFactory(application)).get(ItemViewModel::class.java)

        viewModel.getAllItems().observe(this) {
            itemAdapter.submitList(it)
        }

        viewModel.filterItemsLiveData.observe(this) {
            itemAdapter.submitList(it)
        }
        viewModel.filterItemsKindLive.observe(this) {
            itemAdapter.submitList(it)
        }

        viewModel.getItems().observe(this) {
            itemAdapter.submitList(it)
        }
    }

    // searching
    private fun searchQuery() {
        // checking internet connection
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        editSearch.addTextChangedListener {
            if (isConnected && !it.isNullOrEmpty() && it.count() >= 2) {
                job?.cancel()
                job = MainScope().launch {
                 delay(searhTimeDelay)
                    viewModel.ApiCallForItem(it.toString(), kind)
                }
            } else if (!isConnected && !it.isNullOrEmpty() && it.count() >= 2) {
                viewModel.getItemsByKind(kind.toString())
                viewModel.getItemsByArtistName(it.toString())

            }
        }
    }
}