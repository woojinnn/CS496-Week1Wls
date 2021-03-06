package com.example.week1wls.ui.Gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week1wls.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_add_image.*


class AddImageFragment : Fragment() {

    private lateinit var gson: Gson
    private lateinit var imgData: AddImageData
    lateinit var input: String
    private lateinit var addImageadapter: AddImageAdapter
    lateinit var List: MutableList<AddImageData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gson = GsonBuilder().create()

        return inflater.inflate(R.layout.fragment_add_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set adapter for image List

        ImageList.layoutManager = GridLayoutManager(requireContext(), 1)
        addImageadapter = AddImageAdapter(requireContext())
        ImageList.adapter = addImageadapter

        /* input 받기 */
        // 이미지 검색
        srhQBtn.setOnClickListener {
            input = inputQ.text.toString()

            val thread = ApiImageInfo(input)
            thread.start()
            thread.join()
            List = thread.imageList
            Log.d("Images", List.toString())
            // 출력 완료
            addImageadapter.data = List
            addImageadapter.notifyDataSetChanged()

            inputQ.setText("")
        }

        addImageadapter.setOnItemClickListener(object : AddImageAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: AddImageData, pos: Int) {
                val bundle = Bundle()
                bundle.putString("url", data.pageURL)
                val webViewFrag = WebViewFragment()
                webViewFrag.arguments = bundle

                val action =
                    AddImageFragmentDirections.actionNavigationGalleryAddImageToNavigationGalleryWebview(
                        data.pageURL
                    )
                findNavController().navigate(action)
            }
        })

    }

}