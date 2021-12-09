package com.example.contact.repository

import androidx.lifecycle.LiveData
import com.example.contact.dao.ContactDao
import com.example.contact.data.Contact
import com.example.contact.database.ContactDatabase

class ContactsRepository(private val contactDao: ContactDao) {

    val contactDatabase: ContactDatabase = ContactDatabase.INSTANCE!!
    val getAllContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    suspend fun getOneContact(contact: Contact) =
        contactDatabase.getContactDao().getOneContact(contact.phone_no)
}