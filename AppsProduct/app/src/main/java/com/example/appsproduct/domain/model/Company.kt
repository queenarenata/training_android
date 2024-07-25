package com.example.appsproduct.domain.model

import com.example.appsproduct.domain.model.Address

data class Company(
    val address: Address = Address(),
    val department: String = "",
    val name: String = "",
    val title: String = ""
)