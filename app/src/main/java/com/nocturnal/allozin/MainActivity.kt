package com.nocturnal.allozin
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nocturnal.allozin.adapter.ContactAdapter
import com.nocturnal.allozin.model.Contact
import com.nocturnal.allozin.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {
    private var originalContactList: List<Contact> = emptyList()

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.all { it.value }
            if (allGranted) {

                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Permissions are required to use this app", Toast.LENGTH_SHORT).show()
            }
        }



    private fun checkPermissions() {
        val readContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        val callPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        if (readContactsPermission != PackageManager.PERMISSION_GRANTED || callPhonePermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CALL_PHONE
                )
            )
        } else {

            Toast.makeText(this, "All permissions are granted", Toast.LENGTH_SHORT).show()
        }
    }


    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter
    private var pendingCallContact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()

        val contactRecyclerView = findViewById<RecyclerView>(R.id.contactRecyclerView)
        contactRecyclerView.layoutManager = LinearLayoutManager(this)


        contactAdapter = ContactAdapter(emptyList(), ::onCallClick, ::onSmsClick)
        contactRecyclerView.adapter = contactAdapter



        contactViewModel.contacts.observe(this) { contacts ->
            contactAdapter = ContactAdapter(contacts, ::onCallClick, ::onSmsClick)
            contactRecyclerView.adapter = contactAdapter
        }



        findViewById<ImageButton>(R.id.sort).setOnClickListener {
            sortContacts()
        }


        findViewById<ImageButton>(R.id.search).setOnClickListener {
            showSearchDialog()
        }
    }


    private fun onCallClick(contact: Contact) {
        pendingCallContact = contact
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            initiateCall(contact)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }


    private fun onSmsClick(contact: Contact) {
        val smsIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("smsto:${contact.phoneNumber}")
        }
        startActivity(smsIntent)
    }
    private fun initiateCall(contact: Contact) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:${contact.phoneNumber}")
        }
        startActivity(callIntent)
    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            pendingCallContact?.let { initiateCall(it) }
        } else {
            Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // Set up the SearchView to listen for user input
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { contactViewModel.filterContacts(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { contactViewModel.filterContacts(it) }
                return true
            }
        })

        return true
    }
    private fun sortContacts() {
        val sortedList = originalContactList.sortedBy { it.name }
        contactAdapter = ContactAdapter(sortedList, ::onCallClick, ::onSmsClick)
        findViewById<RecyclerView>(R.id.contactRecyclerView).adapter = contactAdapter
    }


    private fun showSearchDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Search Contacts")


        val input = EditText(this)
        builder.setView(input)


        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterContacts(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        builder.setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }


    private fun filterContacts(query: String) {
        val filteredList = originalContactList.filter { it.name.contains(query, ignoreCase = true) }
        contactAdapter = ContactAdapter(filteredList, ::onCallClick, ::onSmsClick)
        findViewById<RecyclerView>(R.id.contactRecyclerView).adapter = contactAdapter
    }
}
