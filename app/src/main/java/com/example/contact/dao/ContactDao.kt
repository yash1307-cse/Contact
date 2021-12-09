package com.example.contact.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contact.data.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contacts_table")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts_table WHERE phone_no = :q")
    suspend fun getOneContact(q:String): Contact
}