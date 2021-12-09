package com.example.contact.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contact.data.Contact
import com.example.contact.database.ContactDatabase
import com.example.contact.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    val contactsRepository: ContactsRepository
    val allContact: LiveData<List<Contact>>

    init {
        val dao = ContactDatabase.getDatabase(application).getContactDao()
        contactsRepository = ContactsRepository(dao)
        allContact = contactsRepository.getAllContacts
    }

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.insertContact(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.deleteContact(contact)
    }

    fun getOneContact(contact: Contact) = viewModelScope.launch {
        contactsRepository.getOneContact(contact)
    }

}