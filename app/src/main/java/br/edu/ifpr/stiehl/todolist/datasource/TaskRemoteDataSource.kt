package br.edu.ifpr.stiehl.todolist.datasource

import br.edu.ifpr.stiehl.todolist.entities.Task
import br.edu.ifpr.stiehl.todolist.network.TasksService
import io.reactivex.Completable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TaskRemoteDataSource: TaskDataSource {


    var service: TasksService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tads2019-todo-list.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create<TasksService>(TasksService::class.java)
    }

    override fun getAll() = service.getAll()

    override fun insert(task: Task) =
        service
        .createTask(task.title, task.description, task.done)
            .map { newTask -> newTask.remoteId!! }

    override fun update(task: Task)=
        service
            .updateTask(task.remoteId!!, task.title, task.description, task.done)


    override fun remove(task: Task) =
        service
            .deleteTask(task.remoteId!!)

    override fun insertAll(tasks: List<Task>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}