package com.example.viikkotehtava1_mobile_programming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.viikkotehtava1_mobile_programming.domain.Task
import com.example.viikkotehtava1_mobile_programming.domain.MyTask

class TaskViewModel : ViewModel() {

    private val allTasks = MyTask.toMutableList()

    var tasks by mutableStateOf(listOf<Task>())
        private set

    init {
        tasks = allTasks.toList()
    }

    fun addTask(task: Task){
        allTasks.add(task)
        tasks = allTasks.toList()
    }

    fun toggleDone(id: Int){
        allTasks.replaceAll { task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
        tasks = allTasks.toList()
    }

    fun removeTask (id: Int){
        allTasks.removeAll { it.id == id }
        tasks = allTasks.toList()
    }

    fun filterByDone(done: Boolean){
        tasks = allTasks.filter { it.done == done }.toList()
    }

    fun sortByDueDate(){
        tasks = allTasks.sortedBy { it.dueDate }.toList()
    }

    fun showAllTasks() {
        tasks = allTasks.toList()
    }
}

