package br.edu.ifpr.stiehl.todolist.datasource

import androidx.room.Room
import br.edu.ifpr.stiehl.todolist.app.TodoApplication
import br.edu.ifpr.stiehl.todolist.db.AppDatabase
import br.edu.ifpr.stiehl.todolist.db.dao.TaskDao
import br.edu.ifpr.stiehl.todolist.entities.Task
import io.reactivex.Completable

class TaskLocalDataSource : TaskDataSource {


    var taskDao: TaskDao

    init {
        val db =
            Room.databaseBuilder(
                TodoApplication.appContext,
                AppDatabase::class.java,
                "todo.db"
            )
                .allowMainThreadQueries()
                .build()
        taskDao = db.taskDao()
    }

    override fun getAll() = taskDao.getAll()
    override fun insert(task: Task) = taskDao.insert(task)
    override fun update(task: Task) = taskDao.update(task)
    override fun remove(task: Task) = taskDao.remove(task)
    override fun insertAll(tasks: List<Task>) = taskDao.insertAll(tasks)
    override fun removeAll() = taskDao.removeAll()
}