package com.example.week1wls.ui.Gallery

import android.Manifest
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
import com.example.week1wls.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_add_image.*
import kotlinx.android.synthetic.main.item_add_image.*
import org.jsoup.Jsoup
import java.lang.Exception
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.CalendarContract.Attendees.query
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.MediaStore.Video.query
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentResolverCompat.query
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1wls.ui.contact.ContactAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.item_add_image.view.*


class AddImageFragment : Fragment() {

    private lateinit var gson: Gson
    private lateinit var imgData: AddImageData
    lateinit var input: String
    private lateinit var addImageadapter: AddImageAdapter
    //private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var List: MutableList<AddImageData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gson = GsonBuilder().create()

        val view = inflater.inflate(R.layout.fragment_add_image, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set adapter for image List

        ImageList.layoutManager = GridLayoutManager(requireContext(), 2)
        addImageadapter = AddImageAdapter(requireContext())
        ImageList.adapter = addImageadapter

        //setImage()
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

        addImageadapter.setOnItemClickListener(object: AddImageAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: AddImageData, pos: Int) {
                val bundle = Bundle()
                bundle.putString("url", data.pageURL)
                val webViewFrag = WebViewFragment()
                webViewFrag.arguments = bundle

                val action = AddImageFragmentDirections.actionNavigationGalleryAddImageToNavigationGalleryWebview(data.pageURL)
                findNavController().navigate(action)
            }
        })


        fun goToSite(view: View, i: Int){
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(addImageadapter.data[i].pageURL))
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}