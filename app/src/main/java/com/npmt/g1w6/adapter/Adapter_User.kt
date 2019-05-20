package com.npmt.g1w6.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.npmt.g1w6.R
import com.npmt.g1w6.room.TaskDatabase
import com.npmt.g1w6.room.User
import com.npmt.g1w6.room.UserDatabase
import kotlinx.android.synthetic.main.row_user.view.*

class Adapter_User (private val list_user : ArrayList<User>) : RecyclerView.Adapter<Adapter_User.User_Holder>() {

    lateinit var parent: ViewGroup

    //  inflat one row into parent view (recycler view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): User_Holder{
        this.parent = parent
        return User_Holder(LayoutInflater.from(parent.context).inflate(R.layout.row_user,parent,false))
    }

    //  bind one by one row data
    override fun onBindViewHolder(holder: User_Holder, position: Int){
        val user = list_user[position]
        holder.bindUser(user)
    }

    //  count item of list data
    override fun getItemCount() = list_user.size

    fun removeAt(position: Int) {
        list_user.removeAt(position)
        notifyItemRemoved(position)
    }

    // holder
    inner class User_Holder(v: View): RecyclerView.ViewHolder(v){

        private val view : View = v
        private var user : User? = null

        fun bindUser(user : User){

            val taskDb = TaskDatabase.invoke(view.context)
            val taskDAO = taskDb.taskDAO()
            this.user = user
            view.userName.text = user.name
            user.id?.let{ view.numTaskAssigned.text = "Numbers task assigned: "+taskDAO.getUId(it).size }

            //onclick one item
//            view.setOnClickListener(View.OnClickListener {
//                Toast.makeText(view.context,"Success2",Toast.LENGTH_SHORT).show()
//            })
        }

        fun delUser(){
            val userDb = UserDatabase.invoke(view.context)
            val userDAO = userDb.userDAO()
            user?.let{userDAO.deleteUser(it)}
        }

    }
}