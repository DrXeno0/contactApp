package com.nocturnal.allozin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nocturnal.allozin.model.Contact
import com.nocturnal.allozin.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val originalContactList = mutableListOf<Contact>()
    private val contactRepository = ContactRepository(application.contentResolver)
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        loadContacts()
    }


    private fun loadContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val contactList = contactRepository.getContacts()
            _contacts.postValue(contactList)
        }
    }


    fun filterContacts(query: String) {
        _contacts.value = if (query.isEmpty()) {
            originalContactList
        } else {
            originalContactList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}