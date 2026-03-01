package com.example.viikkotehtava3_mobile_programming.domain

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: String,
    val done: Boolean
)