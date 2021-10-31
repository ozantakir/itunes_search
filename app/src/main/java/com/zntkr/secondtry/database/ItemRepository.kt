package com.zntkr.secondtry.database

import android.app.Application
import com.zntkr.secondtry.data.Result
import com.zntkr.secondtry.service.RetrofitInstance
import org.w3c.dom.Entity

class ItemRepository(application: Application) {
    private val itemDao: ItemDao

    init {
      val database = ItemDatabase.getDatabase(application)
      itemDao = database.itemDao()
  }
    // for database
    suspend fun insertItemsByArtistName(result: List<Result>) = itemDao.insertItemsByArtistN(result)

    suspend fun insertItemsByKind(result: List<Result>) = itemDao.insertItemsByKind(result)

    fun getItemsByArtistName(name : String) = itemDao.getItemsByArtistN("%$name%")

    fun getItemsByKindd(kind : String) = itemDao.getItemsByKind("%$kind%")

    fun getAllItem() = itemDao.getAllItems()

    // for api
    suspend fun ApiCall(search : CharSequence, kind : CharSequence) = RetrofitInstance.API.getAllItems(searchTerm = search, kind).body()



}
