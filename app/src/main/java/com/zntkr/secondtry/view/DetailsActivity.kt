package com.zntkr.secondtry.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zntkr.secondtry.R
import com.zntkr.secondtry.data.Result

class DetailsActivity : AppCompatActivity() {

    // values
    private val artist by lazy { findViewById<TextView>(R.id.detailsArtist) }
    private val item by lazy { findViewById<TextView>(R.id.detailsItem) }
    private val date by lazy { findViewById<TextView>(R.id.detailsDate) }
    private val description by lazy { findViewById<TextView>(R.id.detailsDescription) }
    private val image by lazy { findViewById<ImageView>(R.id.detailsImage) }
    private lateinit var result : Result

    // to get data from adapter
    companion object {
        const val itemsKey = "ITEMS_KEY"
        fun sendData(context: Context, result: Result): Intent {
            val intent= Intent(context, DetailsActivity::class.java)
            intent.putExtra(itemsKey,result)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        result = intent.getParcelableExtra(itemsKey)!!

        setDataToDetails()
    }
    //Writing data to details
    private fun setDataToDetails() {
        artist.text = result.artistName
        item.text = result.collectionName
        date.text = result.collectionPrice.toString()
        description.text = result.kind

        Glide.with(applicationContext).load(result.artworkUrl100).into(image)
    }
}