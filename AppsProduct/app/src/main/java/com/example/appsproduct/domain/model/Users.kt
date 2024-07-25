package com.example.appsproduct.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appsproduct.domain.model.Address
import com.example.appsproduct.domain.model.Bank

@Entity(tableName = "user")
data class Users(
    @PrimaryKey
    val id: Int = 0,
    val address: Address = Address(),
    val bank: Bank = Bank(),
    val birthDate: String = "",
    val bloodGroup: String = "",
    val email: String = "",
    val firstName: String = "",
    val image: String = "",
    val lastName: String = "",
    val phone: String = "",
    val role: String = "",
    val university: String = "",
    val username: String = "",
)