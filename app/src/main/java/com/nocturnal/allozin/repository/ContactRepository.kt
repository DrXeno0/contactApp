package com.nocturnal.allozin.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.nocturnal.allozin.model.Contact

class ContactRepository(private val contentResolver: ContentResolver) {

    // Fetch contacts from the device and return a list of Contact objects
    fun getContacts(): List<Contact> {
        val contactList = mutableListOf<Contact>()

        // Query contacts using ContentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        )

        cursor?.use {
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idIndex)
                val name = cursor.getString(nameIndex)
                val phoneNumber = cursor.getString(numberIndex)
                val profileImage = name.firstOrNull()?.toString() ?: "?"

                contactList.add(Contact(id, name, phoneNumber, profileImage))
            }
        }

        return contactList
    }
}