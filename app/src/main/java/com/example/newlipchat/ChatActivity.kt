package com.example.newlipchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newlipchat.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text
import java.lang.reflect.GenericArrayType

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    //바인딩객체
    private lateinit var binding: ActivityChatBinding

    lateinit var mAuth: FirebaseAuth //인증객체
    lateinit var mDbRef: DatabaseReference //DB객체

    private lateinit var receiverRoom: String //받는 대화방
    private lateinit var senderRoom: String //보낸 대화방

    private lateinit var face_id: String //받는 대화방

    private lateinit var messageList: ArrayList<Result>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //초기화
        messageList = ArrayList()
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        //recyclerview
        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerview.adapter = messageAdapter


        //넘어온 데이터 변수에 담기
//        Log.d("==================================================","=================================================")
        face_id = intent.getStringExtra("current_face_id").toString()
        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uId").toString()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        //접속자(보낸사람)의 Uid를 담는 변수
        val senderUid = mAuth.currentUser?.uid

        //보낸이방
        senderRoom = receiverUid + senderUid

        //받는이방
        receiverRoom = senderUid + receiverUid

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = face_id

        //메세지 전송 버튼 이벤트, 전송버튼을 클릭하면 입력한메세지는 DB에 저장이 되고 저장된 메세지가 화면에 보여진다
        //

        binding.sendBtn.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            val messageObject = Message(message, senderUid)

            //데이터 저장
            mDbRef.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    //저장 성공하면
                    mDbRef.child("chats").child(receiverRoom).child("message").push()
                        .setValue(messageObject)
                }
            //입력값 초기화
            binding.messageEdit.setText("")

        }

        //메시지 가져오기
        mDbRef.child("result").child(face_id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshat in snapshot.children) {
//
                        Log.d("1", "1=================================================")
                        val message = postSnapshat.getValue(Result::class.java)
                        Log.d("2", "2=================================================")
                        messageList.add(message!!)
                        Log.d("3", "3=================================================")
                    }
                    //적용
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }
}