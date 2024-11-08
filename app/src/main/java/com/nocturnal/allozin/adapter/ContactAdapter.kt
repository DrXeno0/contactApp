package com.nocturnal.allozin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nocturnal.allozin.R
import com.nocturnal.allozin.model.Contact


class ContactAdapter(
    private val contacts: List<Contact>,
    private val onCallClick: (Contact) -> Unit,
    private val onSmsClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.contactNameTextView.text = contact.name
        holder.contactPhoneTextView.text = contact.phoneNumber
        holder.profileImageView.text = contact.name.firstOrNull()?.toString()?.uppercase() ?: "?"

        // Set up menu button with call and SMS options
        holder.menuButton.setOnClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.menuInflater.inflate(R.menu.contact_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_call -> onCallClick(contact)
                    R.id.action_sms -> onSmsClick(contact)
                }
                true
            }
            popupMenu.show()
        }
    }

    override fun getItemCount() = contacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: TextView = itemView.findViewById(R.id.profileImageView)
        val contactNameTextView: TextView = itemView.findViewById(R.id.contactNameTextView)
        val contactPhoneTextView: TextView = itemView.findViewById(R.id.contactPhoneTextView)
        val menuButton: ImageButton = itemView.findViewById(R.id.menuButton)

        fun bind(contact: Contact) {
            profileImageView.text = contact.profileImage
            contactNameTextView.text = contact.name
            contactPhoneTextView.text = contact.phoneNumber

            // Menu button click listener for Call and SMS options
            menuButton.setOnClickListener {
                // Show menu options (for now, just trigger the actions directly)
                onCallClick(contact)
            }
        }
    }

}