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
import kotlinx.android.synthetic.main.item_add_image.*
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

        // start 버튼 눌렸을 때
        srtBtn.setOnClickListener{
            doTask("https://pixabay.com/images/search/cat/")
            imageList.layoutManager = GridLayoutManager(activity, 2)
        }
        // next 버튼 눌렸을 때
        nextBtn.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_gallery_add_image_to_navigation_gallery)
        }

        // for test
        imageList.layoutManager = GridLayoutManager(activity, 2)


    }


    fun doTask(url: String) {
        var documentTitle : String = ""
        var itemList : ArrayList<AddImageData> = arrayListOf()

        try {
            var doc = Jsoup.connect(url).get()
            var elements = doc.select("div.container--3NC_b")

            for (e in elements) {
                var imgUrl = e.select("a.link--h3bPW").attr("src")
                Log.d("TTT", imgUrl)

                var item = AddImageData(imgUrl)
                itemList.add(item)
            }
            documentTitle = doc.title()
        } catch (e: Exception) {
        }

        imageList.adapter = AddImageAdapter(itemList)

    }


}