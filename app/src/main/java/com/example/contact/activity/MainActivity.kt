package com.example.contact.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.ContactAdapter
import com.example.contact.OnItemClick
import com.example.contact.R
import com.example.contact.data.Contact
import com.example.contact.view_model.ContactViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnItemClick {

    lateinit var contactsViewModel: ContactViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var contactAdapter: ContactAdapter
    lateinit var addContactBtn: FloatingActionButton
    lateinit var context: Context
    lateinit var bottomSheetDialog: BottomSheetDialog
    private val PHONE_CALL_CODE: Int = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomSheetDialog = BottomSheetDialog(this)

        givePermissionForCall(android.Manifest.permission.CALL_PHONE, PHONE_CALL_CODE)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addContactBtn = findViewById(R.id.addContactBtn)

        context = this

        contactAdapter = ContactAdapter(this, this)

        recyclerView.adapter = contactAdapter

        val itemDecoration = DividerItemDecoration(
            this@MainActivity,
            DividerItemDecoration.VERTICAL
        )
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        recyclerView.addItemDecoration(
            itemDecoration
        )


        contactsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(application)
        ).get(ContactViewModel::class.java)

        contactsViewModel.allContact.observe(this, Observer {
            contactAdapter.updateContactList(it)
        })

        addContactBtn.setOnClickListener {

           val view = layoutInflater.inflate(R.layout.add_contact_dialog, null)

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

            val submitBtn: Button = view.findViewById(R.id.submitContact)
            val addContactNameET: EditText = view.findViewById(R.id.addContactNameET)
            val addContactPhoneNo: EditText = view.findViewById(R.id.addContactNoET)

            submitBtn.setOnClickListener {

                val getContactName = addContactNameET.text.toString()
                val getContactNo = addContactPhoneNo.text.toString()

                when (getContactName.isEmpty() && getContactNo.isEmpty()) {
                    true -> {
                        Toast.makeText(this, " An error occurred", Toast.LENGTH_SHORT).show()
                    }

                    false -> {
                        contactsViewModel.insertContact(Contact(getContactName, getContactNo))
                        bottomSheetDialog.dismiss()
                    }
                }
            }
        }
    }

    override fun onItemClick(contact: Contact) {
        contactsViewModel.deleteContact(contact)
    }

    override fun callContact(contact: Contact) {
        contactsViewModel.getOneContact(contact)
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + contact.phone_no)
        startActivity(callIntent)
    }

    private fun givePermissionForCall(permission: String, requestCode: Int) {
        when (ContextCompat.checkSelfPermission(
            this@MainActivity,
            permission
        ) == PackageManager.PERMISSION_DENIED) {
            true -> {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(permission),
                    requestCode
                )

            }
            false -> {
                Toast.makeText(this@MainActivity, "Permission granted already", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode == PHONE_CALL_CODE) {
            true -> {
                when (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    true -> {
                        Toast.makeText(this@MainActivity, "Permission granted", Toast.LENGTH_SHORT)
                            .show()
                    }
                    false -> {
                        Toast.makeText(this@MainActivity, "Permission denied", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}