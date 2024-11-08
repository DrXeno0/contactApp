package com.nocturnal.allozin.model

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val profileImage: String // This will store the first letter of the name as a placeholder
)