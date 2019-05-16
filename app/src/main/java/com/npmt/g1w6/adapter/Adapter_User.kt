package com.npmt.g1w6.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.npmt.g1w6.R
import com.npmt.g1w6.room.User
import com.npmt.g1w6.room.UserDatabase
import kotlinx.android.synthetic.main.row_user.view.*

class Adapter_User (private val list_user : ArrayList<User>) : RecyclerView.Adapter<Adapter_User.User_Holder>() {

    //  inflat one row into parent view (recycler view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): User_Holder{
        return User_Holder(LayoutInflater.from(parent.context).inflate(R.layout.row_user,parent,false))
    }

    //  bind one by one row data
    override fun onBindViewHolder(holder: User_Holder, position: Int){
        val user = list_user[position]
        holder.bindUser(user)
    }

    //  count item of list data
    override fun getItemCount() = list_user.size

    // holder
    class User_Holder(v: View): RecyclerView.ViewHolder(v){

        private val view : View = v
        private var user : User? = null

        fun bindUser(user : User){
            this.user = user
            view.userName.text = user.name
            view.btnDeleteUser.setOnClickListener(View.OnClickListener {
                val userDb = UserDatabase.invoke(view.context)
                val userDAO = userDb.userDAO()
                userDAO.deleteUser(user)
                Toast.makeText(view.context,"Delete success user "+user.name,Toast.LENGTH_SHORT).show()
            })
            //onclick one item
//            view.setOnClickListener(View.OnClickListener {
//                Toast.makeText(view.context,"Success2",Toast.LENGTH_SHORT).show()
//            })
        }

    }
}