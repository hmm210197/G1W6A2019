package com.npmt.g1w6.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.npmt.g1w6.R
import com.npmt.g1w6.TaskDetailActivity
import com.npmt.g1w6.room.Task
import com.npmt.g1w6.room.UserDatabase
import kotlinx.android.synthetic.main.row_task.view.*

class Adapter_Task (private val list_task : ArrayList<Task>) : RecyclerView.Adapter<Adapter_Task.Task_Holder>() {

    //  inflat one row into parent view (recycler view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Task_Holder{
        return Task_Holder(LayoutInflater.from(parent.context).inflate(R.layout.row_task,parent,false))
    }

    //  bind one by one row data
    override fun onBindViewHolder(holder: Task_Holder, position: Int){
        val task = list_task[position]
        holder.bindTask(task)
    }

    //  count item of list data
    override fun getItemCount() = list_task.size

    // holder
    class Task_Holder(v: View):RecyclerView.ViewHolder(v), View.OnClickListener{

        private val view : View = v
        private var task : Task? = null

        init{v.setOnClickListener(this)}

        //  click a task
        override fun onClick(v: View?) {
            val context = itemView.context
            val taskIntent = Intent(context,TaskDetailActivity::class.java)
            val b = Bundle()
            b.putParcelable("task",task)
            taskIntent.putExtras(b)
            context.startActivity(taskIntent)
        }

        fun bindTask(task : Task){
            this.task = task
            val userDb = UserDatabase.invoke(view.context)
            val userDAO = userDb.userDAO()
            view.title_task.text = task.decription
            val uid = task.user_uid
            if(uid!=null) {
                view.task_assign.text = userDAO.findById(uid).name
            }else{
                view.task_assign.text = "Do not assigned for any one"
            }

        }

    }

}