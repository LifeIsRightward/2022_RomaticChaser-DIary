package com.example.kotlinex1.nav_Fragments.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinex1.R
import com.example.kotlinex1.databinding.FragmentProfileBinding

class ProfileFragment private constructor() : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var img_Profile_default: ImageView
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val ProfileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome
        ProfileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        initImageViewProfile()


        // 저장
        binding.btnFragmentProfile.setOnClickListener {
            val name = binding.editTextFragmentName.text.toString()
            val age = binding.editTextFragmentAge.text.toString().toInt()
            // 여자이면 true, 남자이면 false
            val gender = binding.radioGroupFragment.checkedRadioButtonId == R.id.radio_fragment_female

            // - Diary
            // ---- [name].xml
            // { key : value }
            val pref = requireContext().getSharedPreferences("user", 0)
            val edit = pref.edit()
            edit.putString("name", name)
            edit.putInt("age", age)
            edit.putBoolean("isFemale", gender)

            edit.apply()
        }

        initData()


        return root
    }

    private fun initData() {
        val pref = requireContext().getSharedPreferences("user", 0)
        val name = pref.getString("name", null)
        val age = pref.getInt("age", -1)
        val isFemale = pref.getBoolean("isFemale", true)

        if (name != null && age != -1) {
            binding.editTextFragmentName.setText(name)
            binding.editTextFragmentAge.setText("$age")
            binding.radioGroupFragment.check(
                    if (isFemale) R.id.radio_fragment_female
                    else R.id.radio_fragment_male
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initImageViewProfile() {
        img_Profile_default = binding.imgProfileDefault

        img_Profile_default.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                        this.requireContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    navigateGallery()
                }

                // 갤러리 접근 권한이 없는 경우 & 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                )
            }

        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    navigateGallery()
                else
                    Toast.makeText(this.requireContext(), "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //
            }
        }
    }

    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"
        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 예외처리
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            // 2000: 이미지 컨텐츠를 가져오는 액티비티를 수행한 후 실행되는 Activity 일 때만 수행하기 위해서
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    img_Profile_default.setImageURI(selectedImageUri)
                } else {
                    Toast.makeText(this.requireContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this.requireContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this.requireContext())
                .setTitle("권한이 필요합니다.")
                .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
                .setPositiveButton("동의하기") { _, _ ->
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                }
                .setNegativeButton("취소하기") { _, _ -> }
                .create()
                .show()
    }


}