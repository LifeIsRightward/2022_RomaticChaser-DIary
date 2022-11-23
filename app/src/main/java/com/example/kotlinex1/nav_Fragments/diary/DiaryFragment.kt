package com.example.kotlinex1.nav_Fragments.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinex1.WriteActivity
import com.example.kotlinex1.databinding.FragmentDiaryBinding
import com.example.kotlinex1.db.DiaryDAO
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
        Handler(Looper.getMainLooper()).postDelayed({
            val dao = DiaryDAO.newInstance(requireContext())
            adapter.data = dao.selectAll()
        }, 500)
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
        adapter.onCardClickListener = { id, p ->
            val _id = adapter.data[p]._id

            val intent = Intent(requireContext(), WriteActivity::class.java)
            intent.putExtra("diaryId", _id)
            launcher.launch(intent)
        }
        adapter.onCardLongClickListener = { id, p ->
            AlertDialog.Builder(requireContext()).apply {
                setTitle("일기 삭제")
                setMessage("이 일기를 삭제하시겠습니까?")
                setPositiveButton("삭제") { d, _ ->
                    adapter.remove(p)
                    DiaryDAO.newInstance(requireContext()).delete(id)
                    d.dismiss()
                }
                setNegativeButton("취소") { d, _ -> d.dismiss()}
                show()
            }
        }
        //
        binding.rvDiary.adapter = adapter
        binding.rvDiary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiary.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) {
                    binding.fabDiary.extend()
                } else {
                    binding.fabDiary.shrink()
                }
            }
        })


        binding.fabDiary.setOnClickListener {
            val intent = Intent(requireContext(), WriteActivity::class.java)
            launcher.launch(intent)

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

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

