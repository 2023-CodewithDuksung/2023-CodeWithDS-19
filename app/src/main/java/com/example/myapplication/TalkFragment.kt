package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentTalkBinding
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TalkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TalkFragment : Fragment() {

    lateinit var binding: FragmentTalkBinding
    //lateinit var adapter: TalkRecyclerViewAdapter

    //lateinit var mAuth: FirebaseAuth
    //lateinit var mDbRef: DatabaseReference

    lateinit var talkList: ArrayList<TalkModel>



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentTalkBinding.inflate(layoutInflater)



        return binding.root
    }

    //화면에 보이기 직전 실행
//    override fun onStart() {
//        super.onStart()
//        if(MyApplication.checkAuth()){
//            MyApplication.db.collection("Talks")
//                .get()
//                .addOnSuccessListener { result ->
//                    val itemList = mutableListOf<TalkModel>()
//                    for(document in result){
//                        val item = document.toObject(TalkModel::class.java)
//                        item.chatId = document.id
//                        itemList.add(item)
//                    }
//                    val adapter = ListRecyclerViewAdapter(requireContext(), itemList)
//                    binding.noticListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//                    binding.noticListRecyclerView.adapter = adapter
//                    Log.d("ToyProject", "${itemList}")
//
//                    //recyclerview 아이템 클릭 이벤트 처리
//                    adapter.setOnItemClickListener(object: ListRecyclerViewAdapter.OnItemClickListener {
//                        override fun onItemClick(v: View, position: Int) {
//                            Log.d("ToyProject", "${itemList[position].docId} 클릭")
//                        }
//                    })
//                }
//                .addOnFailureListener{
//                    Toast.makeText(requireContext(), "데이터 획득 실패", Toast.LENGTH_SHORT).show()
//                }
//        }
//
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TalkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TalkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}




