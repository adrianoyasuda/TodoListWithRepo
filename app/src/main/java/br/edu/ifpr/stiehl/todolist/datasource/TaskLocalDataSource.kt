package br.edu.ifpr.stiehl.todolist.datasource

import androidx.room.Room
import br.edu.ifpr.stiehl.todolist.app.TodoApplication
import br.edu.ifpr.stiehl.todolist.db.AppDatabase
import br.edu.ifpr.stiehl.todolist.db.dao.TaskDao
import br.edu.ifpr.stiehl.todolist.entities.Task
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

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

    override fun getAll(): Single<List<Task>> {
        return taskDao.getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun insert(task: Task): Single<Long> {
        return taskDao.insert(task)
            .subscribeOn(Schedulers.io())
    }

    override fun update(task: Task): Completable {
        return taskDao.update(task)
            .subscribeOn(Schedulers.io())
    }

    override fun remove(task: Task): Completable {
        return taskDao.remove(task)
            .subscribeOn(Schedulers.io())
    }
}