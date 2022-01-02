package com.example.week1wls.ui.Gallery

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import com.example.week1wls.databinding.FragmentGalleryBinding
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<GalleryAdapter.ViewHolder>? = null
    lateinit var input : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = GalleryAdapter()
        }
        /* input 받기 */
        // search 버튼 눌렸을 때
        srhBtn.setOnClickListener {
            input = inputText.text.toString()
            inputText.text = null
            findMyTag(input)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun findMyTag(input: String) {
        val count = GalleryAdapter().dataList.size
        var k : Boolean
        k = false
        for (i : Int in 0..count-1) {
            if (GalleryAdapter().dataList[i].tag == input) {
                var image = ImageView(context)
                image.setImageResource(GalleryAdapter().dataList[i].img)
                val builder = AlertDialog.Builder(context)
                builder.setTitle("찾음!!!!")
                    .setView(image)
                    .setPositiveButton("확인",
                        {dialog, id ->
                        // 확인 버튼 클릭 이벤트 추가
                        //addImage(tag) : tag 변수로 입력 받기

                        })
                    .setNegativeButton("취소",
                        {dialog, id ->
                        // 취소 버튼 클릭 이벤트 추가
                        })
                builder.show()
                k = true
                break
            }
        }

        if( !k ){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("ㅜㅠ")
                .setMessage("정확한 tag 로 검색해주세요")
            builder.show()
        }
    }

}