package com.example.kotlinex1.nav_Fragments.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinex1.WriteActivity
import com.example.kotlinex1.databinding.FragmentDiaryBinding
import java.util.*

class DiaryFragment private constructor() : Fragment() {

    companion object {
        fun newInstance() = DiaryFragment()
    }
    private var _binding: FragmentDiaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: DiaryAdapter

    private lateinit var pref: SharedPreferences

    override fun onResume() {
        super.onResume()

        initData()

    }

    private fun initData() {
        adapter.data.clear()
        pref.getStringSet("diaries", setOf())?.let {
            val array = it.toTypedArray()
            Arrays.sort(array)
            array.forEach { item -> adapter.data.add(DiaryVO.convertFromJson(item)) }
        }
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryBinding.inflate(layoutInflater)

        pref = requireContext().getSharedPreferences("diary", 0)

        // Adapter 초기화
        adapter = DiaryAdapter()
        adapter.onCardClickListener = {
            Toast.makeText(requireContext(), "Card click position $it", Toast.LENGTH_SHORT).show()
            //인텐트 => 액티비티를 넘겨줘

            val intent = Intent(this.requireContext(), WriteActivity::class.java)
            intent.putExtra("data", adapter.data[it].toString())
            startActivity(intent)
            //일기를 작성
        }
        adapter.onAction1ClickListener = { v, p ->
            adapter.data.removeAt(p)
            adapter.notifyDataSetChanged()
        }
        adapter.onAction2ClickListener = { v, p ->
            Toast.makeText(requireContext(), "action2 click position $p", Toast.LENGTH_SHORT).show()
        }
        //
        binding.rvDiary.adapter = adapter
        binding.rvDiary.layoutManager = LinearLayoutManager(requireContext())

        binding.fabDiary.setOnClickListener {
            val position = adapter.data.size
            // sharedpreference
            // ArrayList<DiaryVO>
            // Set<String>
            adapter.data.add(
                DiaryVO(
                        0,
                    "새 일기 ${adapter.data.size + 1}",
                    //"날짜${adapter.data.size}"
                    ""
                )
            )

            // Set<DiaryVO> Set<String>
            val save = arrayListOf<String>()
            for (d in adapter.data) {
                save.add(d.toString())
            }

            val edit = pref.edit()
            edit.putStringSet("diaries", save.toSet())
            edit.apply()
            adapter.notifyItemInserted(position)
        }

        binding.fabDiary.setOnLongClickListener {
            val edit = pref.edit()
            edit.remove("diaries")
            edit.apply()
            initData()
            true
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

