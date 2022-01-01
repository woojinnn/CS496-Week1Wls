package com.example.week1wls.ui.Gallery

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1wls.R
import kotlinx.android.synthetic.main.fragment_add_image.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_input.*
import org.jsoup.Jsoup
import java.lang.Exception

class AddImageFragment : Fragment() {

    lateinit var input: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_image, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //doTask("https://pixabay.com/images/search/cat/")

        // for test
        imageList.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = AddImageAdapter()
        }
        // next 버튼 눌렸을 때
        nextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_gallery_add_image_to_navigation_gallery)
        }

    }

    /*
    fun chooseImage(view: View){

        // 선택 시 border 색 변화, 체크 표시 등의 UI 변화 필요
        var image = ImageView(context)
        //image.setImageResource(view.img)
        var inputTag : String
        val builder = AlertDialog.Builder(context)
        builder.setTitle("tag 입력")
            .setView(image)
            .setPositiveButton("저장", { dialog, which ->
                val textView : TextView = view.findViewById(R.id.inputText)
                inputTag = textView.text.toString()
                //GalleryAdapter().dataList.add(GalleryData(image, inputTag))
            })
            .setNegativeButton("취소", { dialog, which ->
                //
            })
        builder.show()
    }

     */

    /*
    fun doTask(url: String) {
        try {
            var doc = Jsoup.connect(url).get()
            var elements = doc.select("div.container--3NC_b a")

            for(e in elements){
                var imgUrl = e.absUrl("src")
                Log.d("TTT", imgUrl)
                AddImageAdapter().imageList.add(AddImageData(imgUrl))
            }

        } catch (e : Exception) {
        }

    }

     */

}