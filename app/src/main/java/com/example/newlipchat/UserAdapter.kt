//데이터베이스에 저장된 사용자 리스트를 띄워주는 클래스
//이 클래스를 통해 뷰를 전달받아 userlayout 텍스트뷰 객체를 만들 수 있음
package com.example.newlipchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context, private val userList: ArrayList<User>):
  RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    //화면설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    //데이터설정
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        //화면에 데이터 보여주기
        holder.nameText.text = currentUser.name

        //아이템 클릭 이벤트, 누르면 채팅방으로 이동
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            //넘길데이터
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uId", currentUser.uId)

            context.startActivity(intent)
        }

    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nameText: TextView = itemView.findViewById(R.id.name_text)

    }


}