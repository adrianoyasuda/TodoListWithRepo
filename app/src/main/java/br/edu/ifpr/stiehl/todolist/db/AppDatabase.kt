package br.edu.ifpr.stiehl.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifpr.stiehl.todolist.db.dao.TaskDao
import br.edu.ifpr.stiehl.todolist.entities.Task

@Database(entities = arrayOf(Task::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}