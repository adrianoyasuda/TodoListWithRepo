package br.edu.ifpr.stiehl.aula7_sqlite.ui

import br.edu.ifpr.stiehl.todolist.entities.Task

interface TaskAdapterListener {
    fun taskSaved(task: Task)
    fun taskRemoved(task: Task)
}