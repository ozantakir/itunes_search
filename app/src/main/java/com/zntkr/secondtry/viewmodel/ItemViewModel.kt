package com.zntkr.secondtry.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.zntkr.secondtry.data.Result
import com.zntkr.secondtry.database.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel constructor(application: Application) : AndroidViewModel(application) {

    // values and livedata
    private val repository = ItemRepository(application)
    private val nameLiveData = MutableLiveData<String>()
    private val kindLiveData = MutableLiveData<String>()
    private val itemLiveData : MutableLiveData<List<Result>> =MutableLiveData()

    val filterItemsLiveData = Transformations.switchMap(nameLiveData) {
        repository.getItemsByArtistName(it)
    }
    val filterItemsKindLive = Transformations.switchMap(kindLiveData) {
        repository.getItemsByKindd(it)
    }
    // getting items
    fun getItemsByArtistName(name : String) {
        nameLiveData.postValue(name)
    }
    fun getItemsByKind(kind : String) {
        kindLiveData.postValue((kind))
    }
    // inserting items
    private fun insertItemsByArtistName(result: List<Result>) {
        viewModelScope.launch {
            repository.insertItemsByArtistName(result)
        }
    }
    private fun insertItemsByKind(result: List<Result>) {
        viewModelScope.launch {
            repository.insertItemsByKind(result)
        }
    }

    fun getAllItems() : LiveData<List<Result>> {
        return repository.getAllItem()
    }
    fun getItems() : LiveData<List<Result>> {
        return itemLiveData
    }

    // API Call
    fun ApiCallForItem(search : CharSequence, kind : CharSequence) = viewModelScope.launch {
        val itemsResponse = repository.ApiCall(search,kind)
        if(itemsResponse != null) {
            this@ItemViewModel.insertItemsByArtistName(itemsResponse.results)
            this@ItemViewModel.insertItemsByKind(itemsResponse.results)
            this@ItemViewModel.itemLiveData.postValue(itemsResponse.results)
        }
    }
}