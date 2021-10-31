package com.zntkr.secondtry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zntkr.secondtry.R
import com.zntkr.secondtry.data.Result
import com.zntkr.secondtry.view.DetailsActivity

class ItemAdapter(private val context: Context)
    : ListAdapter<Result, ItemAdapter.ItemsHolder>(
    itemCallback) {

  companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.artistName == newItem.artistName
            }
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemsHolder {
        val v : View = LayoutInflater.from(context).inflate(
            R.layout.list_item,
            parent, false)
        return ItemsHolder(v)
    }

    override fun onBindViewHolder(holder:ItemsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //Writing results to home screen
    class ItemsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val collectionName : TextView = itemView.findViewById(R.id.tvName)
        private val collectionPrice : TextView = itemView.findViewById(R.id.tvPrice)
        private val releaseDate : TextView = itemView.findViewById(R.id.tvDate)
        private val image : ImageView = itemView.findViewById(R.id.image)
        private val cardView : CardView =itemView.findViewById(R.id.oneItem)

        fun bind(result: Result) {
            collectionName.text = result.collectionName
            collectionPrice.text = result.collectionPrice.toString()
            releaseDate.text = result.releaseDate

            Glide.with(itemView.context).load(result.artworkUrl100).into(image)

            cardView.setOnClickListener {
                itemView.context.startActivity(DetailsActivity.sendData(it.context, result))
            }
        }
    }
}