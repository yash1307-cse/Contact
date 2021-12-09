package com.example.contact.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contact.dao.ContactDao
import com.example.contact.data.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {

        @Volatile
        var INSTANCE: ContactDatabase? = null
        fun getDatabase(context: Context): ContactDatabase {
            when (INSTANCE == null) {
                true -> {
                    synchronized(this) {

                        INSTANCE =
                            Room.databaseBuilder(
                                context.applicationContext,
                                ContactDatabase::class.java,
                                "contacts_db"
                            ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}