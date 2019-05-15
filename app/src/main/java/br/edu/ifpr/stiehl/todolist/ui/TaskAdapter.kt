package br.edu.ifpr.stiehl.todolist.ui

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifpr.stiehl.aula7_sqlite.ui.TaskAdapterListener
import br.edu.ifpr.stiehl.todolist.R
import br.edu.ifpr.stiehl.todolist.entities.Task
import kotlinx.android.synthetic.main.item_task_edit.view.*
import kotlinx.android.synthetic.main.item_task_show.view.*

class TaskAdapter(var tasks: MutableList<Task>, var listener: TaskAdapterListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var taskEditing: Task? = null

    fun createTask(): Int {
        val position = 0
        val task = Task("", "", false)
        tasks.add(position, task)
        taskEditing = task
        notifyItemInserted(position)
        return position
    }

    override fun getItemCount() = tasks.size

    override fun getItemViewType(position: Int) =
        if (tasks[position] === taskEditing)
            R.layout.item_task_edit
        else
            R.layout.item_task_show

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.fillUI(tasks[position])
    }

    fun notify(task: Task, clear: Boolean = false) {
        val position = tasks.indexOf(task)
        taskEditing = if (clear) null else task
        notifyItemChanged(position)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillUI(task: Task) {
            if (task === taskEditing) {
                itemView.txtTitle.setText(task.title)
                itemView.txtDescription.setText(task.description)
                itemView.btSave.setOnClickListener {
                    task.title = itemView.txtTitle.text.toString()
                    task.description = itemView.txtDescription.text.toString()
                    listener.taskSaved(task)
                    notify(task, true)
                }
                itemView.btDelete.setOnClickListener {
                    val position = tasks.indexOf(task)
                    notifyItemRemoved(position)
                    listener.taskRemoved(task)
                    tasks.remove(task)
                    taskEditing = null
                }
            } else {
                itemView.lblTitle.text = task.title

                if (task.done) {
                    itemView.lblTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    itemView.btShare.visibility = View.VISIBLE
                } else {
                    itemView.lblTitle.paintFlags = 0
                    itemView.btShare.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    notify(task)
                }
                itemView.setOnLongClickListener {
                    task.done = !task.done
                    listener.taskSaved(task)
                    notify(task, true)
                    true
                }
                itemView.btShare.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    with(shareIntent) {
                        type = "text/plain"
                        val message = "${itemView.context.getString(R.string.share_message)} ${task.title}"
                        putExtra(Intent.EXTRA_TEXT, message)
                    }
                    itemView.context.startActivity(shareIntent)
                }
            }
        }

    }
}