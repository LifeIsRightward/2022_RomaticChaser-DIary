package com.example.kotlinex1.Fragments.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryBinding.inflate(layoutInflater)

        // Adapter 초기화
        adapter = DiaryAdapter()
        adapter.onCardClickListener = {
            Toast.makeText(requireContext(), "Card click position $it", Toast.LENGTH_SHORT).show()
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
            adapter.data.add(
                DiaryVO(
                    R.drawable.ic_launcher_background,
                    "title${adapter.data.size}",
                    "content${adapter.data.size}"
                )
            )
            adapter.notifyItemInserted(position)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

