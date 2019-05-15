package br.edu.ifpr.stiehl.todolist.db.dao

import androidx.room.*
import br.edu.ifpr.stiehl.todolist.entities.Task
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAll(): Single<List<Task>>

    @Insert
    fun insert(task: Task): Single<Long>

    @Update
    fun update(task: Task): Completable

    @Delete
    fun remove(task: Task): Completable
}