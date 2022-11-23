package com.example.kotlinex1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.example.kotlinex1.databinding.ActivityWriteBinding
import com.example.kotlinex1.db.DiaryDAO
import com.example.kotlinex1.nav_Fragments.diary.DiaryVO
import com.example.kotlinex1.tool.ImageManager
import com.google.android.material.snackbar.Snackbar


private const val TAG_WRITE = "write_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_SUMMARY = "summary_fragment"

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var dao: DiaryDAO
    private var diary: DiaryVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = DiaryDAO.newInstance(this)

        initData()
        initEditMode()

        binding.imgWrite.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getImage()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            getImage()
        } else {
            Snackbar.make(binding.root, "이미지를 첨부하려면 권한을 허용해야 합니다.", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        imageActivityResult.launch(intent)
    }

    private val imageActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val uri = it.data?.data
        if (uri != null) {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }

            binding.imgWrite.setImageBitmap(bitmap)

        }

    }

    private fun initData() {
        val _id = intent?.getIntExtra("diaryId", -1) ?: return

        val diary = DiaryDAO.newInstance(this).select(_id)
        this.diary = diary

    }

    private fun initEditMode() {
        diary?.let {
            binding.txtWriteTitle.setText(it.title)
            binding.txtWriteContent.setText(it.content)
            it.image?.let { image ->
                binding.imgWrite.setImageBitmap(ImageManager(this).read(image))
            }
        }
    }

    private fun saveDiary() {
        val title = binding.txtWriteTitle.text.toString().trim()
        val content = binding.txtWriteContent.text.toString().trim()
        val imageManager = ImageManager(this)
        val bitmap = binding.imgWrite.drawable as? BitmapDrawable
        var path: String? = null
        bitmap?.let {
            path = imageManager.save(bitmap.bitmap)
        }

        val vo = DiaryVO(-1, path, title, content, System.currentTimeMillis())
        dao.insert(vo)
    }

    private fun updateDiary() {
        diary?.let {
            it.title = binding.txtWriteTitle.text.toString().trim()
            it.content = binding.txtWriteContent.text.toString().trim()
            val imageManager = ImageManager(this)
            val bitmap = binding.imgWrite.drawable as? BitmapDrawable
            var path: String? = null
            bitmap?.let {
                path = imageManager.save(bitmap.bitmap)
            }
            it.image = path
            dao.update(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.diary == null) {
            saveDiary()
        } else {
            updateDiary()
        }
    }
}






