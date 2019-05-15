package br.edu.ifpr.stiehl.todolist.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tasks")
data class Task(
//    @SerializedName("title")
    var title: String,
    var description: String,
    var done: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun equals(other: Any?) =
        other != null && (this === other || (this.id != 0L && this.id == (other as Task).id))
}