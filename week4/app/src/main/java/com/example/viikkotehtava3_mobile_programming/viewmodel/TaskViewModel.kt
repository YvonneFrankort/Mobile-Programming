package com.example.viikkotehtava3_mobile_programming.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikkotehtava3_mobile_programming.domain.Task
import com.example.viikkotehtava3_mobile_programming.domain.MyTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val allTasks = MyTask.toMutableList()
    
    private val _tasks = MutableStateFlow<List<Task>>(allTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(task: Task) {
        allTasks.add(task)
        _tasks.value = allTasks.toList()
    }

    fun toggleDone(id: Int) {
        allTasks.replaceAll { task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
        _tasks.value = allTasks.toList()
    }

    fun removeTask(id: Int) {
        allTasks.removeAll { it.id == id }
        _tasks.value = allTasks.toList()
    }

    fun updateTask(updated: Task) {
        allTasks.replaceAll { task ->
            if (task.id == updated.id) updated else task
        }
        _tasks.value = allTasks.toList()
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = allTasks.filter { it.done == done }
    }

    fun sortByDueDate() {
        _tasks.value = allTasks.sortedBy { it.dueDate }
    }

    fun showAllTasks() {
        _tasks.value = allTasks.toList()
    }
}
