package br.edu.ifpr.stiehl.todolist.datasource

import br.edu.ifpr.stiehl.todolist.entities.Task
import br.edu.ifpr.stiehl.todolist.network.TasksService
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TaskRemoteDataSource: TaskDataSource {
    var service: TasksService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.20.23.189:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create<TasksService>(TasksService::class.java)
    }

    override fun getAll(): Single<List<Task>> {
        return service.getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun insert(task: Task): Single<Long> {
        return service.createTask(task.title, task.description, task.done)
            .map { task.id }
            .subscribeOn(Schedulers.io())
    }

    override fun update(task: Task): Completable {
        return service.updateTask(task.id, task.title, task.description, task.done)
            .subscribeOn(Schedulers.io())
    }

    override fun remove(task: Task): Completable {
        return service.deleteTask(task.id)
            .subscribeOn(Schedulers.io())
    }
}