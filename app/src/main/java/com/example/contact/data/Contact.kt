package com.example.contact.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
class Contact(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone_no") val phone_no: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}