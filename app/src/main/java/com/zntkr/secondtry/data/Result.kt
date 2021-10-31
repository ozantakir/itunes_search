package com.zntkr.secondtry.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Result(
    @PrimaryKey(autoGenerate = true)
    val artistId: Int? = null,
    val artistName: String? = null,
    val artworkUrl100: String? = null,
    val collectionName: String? = null,
    val collectionPrice: Double? = null,
    val kind: String? = null,
    val longDescription: String? = null,
    val releaseDate: String? = null,
    val shortDescription: String? = null,
) : Parcelable