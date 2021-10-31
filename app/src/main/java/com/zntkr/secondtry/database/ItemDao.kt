package com.zntkr.secondtry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zntkr.secondtry.data.Result

@Dao
interface ItemDao {
    // insert by name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItemsByArtistN(result : List<Result>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItemsByKind(result : List<Result>)

    // get by name
    @Query("select * from Result where artistName LIKE :name")
    fun getItemsByArtistN(name : String) : LiveData<List<Result>>

    @Query("select * from Result where kind LIKE :kind")
    fun getItemsByKind(kind : String) : LiveData<List<Result>>

    @Query("select * from Result")
    fun getAllItems() : LiveData<List<Result>>



}