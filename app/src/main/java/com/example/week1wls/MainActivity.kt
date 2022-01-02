package com.example.week1wls

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1wls.databinding.ActivityMainBinding
import com.example.week1wls.ui.Gallery.AddImageAdapter
import com.example.week1wls.ui.Gallery.AddImageFragment
import com.example.week1wls.ui.Gallery.GalleryAdapter
import com.example.week1wls.ui.Gallery.GalleryData
import kotlinx.android.synthetic.main.item_add_image.*
import kotlinx.android.synthetic.main.item_gallery.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(

//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
//                R.id.navigation_healthcare_main
//            )
            navController.graph
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
    /*
    // addImageFragment 에서 image 선택 시 발생 이벤트
    fun chooseImage(view: View){
        // 선택 시 border 색 변화, 체크 표시 등의 UI 변화 필요
        //var image = ImageView(this)
        //image.setImageResource(this.)
        //image = view.findViewById(R.id.addimage)
        var inputTag = EditText(this)
        inputTag.setHint("원하는 Tag 입력해라...")
        inputTag.inputType = InputType.TYPE_CLASS_TEXT

        var inputMemo = EditText(this)
        inputMemo.setHint("원하는 Memo 입력해라...")
        inputMemo.inputType = InputType.TYPE_CLASS_TEXT

        //var img = this.
        val builder = AlertDialog.Builder(this)
        builder.setTitle("tag 입력")
            .setView(inputTag)
            .setView(inputMemo)
            .setPositiveButton("저장", { dialog, id ->
                val tag = inputTag.text.toString()
                val memo = inputMemo.text.toString()
                // 개망했네....
                var item = GalleryData(R.drawable.cat1, "tag", "좃댐")
                GalleryAdapter().dataList.add(item)
                //GalleryAdapter().dataList.add(GalleryData(R.drawable.cat1, "tag", "좃댐"))
            })
            .setNegativeButton("취소", { dialog, id ->
                //
            })
        builder.show()
        GalleryAdapter()
    }

    // GalleryFragment 에서 image 선택 시 발생 이벤트
    fun showTagMemo(){
        var image = ImageView(this)
        // view -> index 받는거 제발
        image.setImageResource(GalleryAdapter().dataList[0].img)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("고양이...보고싶단")
            .setView(image)
            .setPositiveButton("수정", { dialog, id ->
                // tag + memo 수정 이벤트
            })
        builder.show()
    }

     */

    // for pie chart

}