package com.example.week1wls.ui.Gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import com.example.week1wls.databinding.FragmentGalleryBinding
import kotlinx.android.synthetic.main.fragment_gallery.*

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
        // 버튼 눌렸을 때
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

    fun findMyTag(input: String){
        val count = GalleryAdapter().dataList.size
        for(i: Int in 1..count){
            if (GalleryAdapter().dataList[i].tag == input){
                srhBtn.text = "find"
            }
        }

    }
}