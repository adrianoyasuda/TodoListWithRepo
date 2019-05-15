package br.edu.ifpr.stiehl.todolist.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifpr.stiehl.aula7_sqlite.ui.TaskAdapterListener
import br.edu.ifpr.stiehl.todolist.R
import br.edu.ifpr.stiehl.todolist.datasource.TaskRepository
import br.edu.ifpr.stiehl.todolist.entities.Task
import br.edu.ifpr.stiehl.todolist.ui.TaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.content_todo.*


class TodoActivity : AppCompatActivity(), TaskAdapterListener {

    lateinit var adapter: TaskAdapter
    lateinit var taskRepository: TaskRepository
    lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(toolbar)

        configureRepository()
        loadTasks()

        btAdd.setOnClickListener {
            val position = adapter.createTask()
            listTask.scrollToPosition(position)
        }

        swipeTasks.setOnRefreshListener {
            loadTasks()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun configureRepository() {
        disposables = CompositeDisposable()
        taskRepository = TaskRepository()
    }

    fun loadTasks() {
        swipeTasks.isRefreshing = true

        taskRepository
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tasks ->
                swipeTasks.isRefreshing = false
                if (tasks != null)
                    loadRecyclerView(tasks)
            }
            .addTo(disposables)
    }

    private fun loadRecyclerView(tasks: List<Task>) {
        adapter = TaskAdapter(tasks.toMutableList(), this)
        listTask.adapter = adapter
        listTask.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun taskSaved(task: Task) {
        if (task.id == 0L)
            taskRepository
                .insert(task)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { id -> task.id = id }
                .addTo(disposables)
        else
            taskRepository
                .update(task)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposables)
    }

    override fun taskRemoved(task: Task) {
        taskRepository
            .remove(task)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(disposables)
    }

}