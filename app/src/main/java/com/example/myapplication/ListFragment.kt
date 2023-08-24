package com.example.myapplication

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentListBinding
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentListBinding

    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.inflate(inflater, container, false)

        //플로팅버튼
        binding.pButton.setOnClickListener {
            val intent = Intent(requireContext(), PostActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        if(MyApplication.checkAuth()){
            MyApplication.db.collection("notices")
                //.orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    val itemList = mutableListOf<NoticeModel>()
                    for(document in result){
                        val item = document.toObject(NoticeModel::class.java)
                        item.docId = document.id
                        itemList.add(item)
                    }
                    val adapter = ListRecyclerViewAdapter(requireContext(), itemList)
                    binding.noticListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.noticListRecyclerView.adapter = adapter
                    Log.d("ToyProject", "${itemList}")

                    //recyclerview 아이템 클릭 이벤트 처리
                    adapter.setOnItemClickListener(object: ListRecyclerViewAdapter.OnItemClickListener {
                        override fun onItemClick(v: View, position: Int) {
                            val data = itemList[position]

                            Log.d("ToyProject", "${data.docId} 클릭")

                            val intent = Intent(requireContext(), NoticeDetailActivity::class.java)

                            intent.putExtra("host", data.host)
                            intent.putExtra("title", data.title)
                            intent.putExtra("departure", data.departure)
                            intent.putExtra("destination", data.destination)
                            intent.putExtra("currentDay", data.currentDay)
                            intent.putExtra("meetingTime", data.meetingTime)
                            intent.putExtra("recruitment", data.recruitment)
                            intent.putExtra("recruited", data.recruited)
                            intent.putExtra("context", data.context)
                            intent.putExtra("taxiOrWalk", data.taxiOrWalk)

                            intent.putExtra("myDocId", itemList[position].docId)
                            intent.putExtra("myChatId", itemList[position].chatId)

                            startActivity(intent)
                        }
                    })
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "데이터 획득 실패", Toast.LENGTH_SHORT).show()
                }
        }

    }


}