package com.example.androidfinalproject

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.databinding.FragmentDiaryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class DiaryFragment : Fragment() {
    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)

        binding.addFab.setOnClickListener {

            if (MyApplication.checkAuth()) {
                val intent = Intent(requireContext(), AddActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "인증을 먼저 해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (MyApplication.checkAuth() || MyApplication.email != null) {
            binding.mainRecyclerView.visibility = View.VISIBLE

            binding.mainRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
                ) {
                    outRect.bottom = 10
                    outRect.top = 10
                }
            })

            MyApplication.db.collection("diary")

                .get()
                .addOnSuccessListener { result ->
                    val itemList = mutableListOf<ItemData>()
                    for (document in result) {
                        val item = document.toObject(ItemData::class.java)
                        item.docId = document.id
                        itemList.add(item)
                    }
                    binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.mainRecyclerView.adapter = MyAdapter(itemList)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Firestore로부터 데이터획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
        } else {
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

