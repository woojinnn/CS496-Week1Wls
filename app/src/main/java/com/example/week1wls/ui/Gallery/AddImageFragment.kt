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
        //set adapter for image List\

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


        fun goToSite(view: View, i: Int){
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(addImageadapter.data[i].pageURL))
            startActivity(intent)
        }

        // 사진 터치 (다른 이벤트)
        /*
        addImageadapter.setOnItemClickListener(object : AddImageAdapter.OnItemClickListener {
            override fun onItemClick(v: View, imageData: AddImageData, pos: Int) {
                val newImgData = AddImageData(imgData.pageURL, imgData.tags, imgData.imageURL, imgData.views, imgData.downloads, imgData.likes)
                val spEditor = profileCache.edit()
                val imgDataStr = gson.toJson(newImgData, AddImageData::class.java)
                spEditor.putString("profileCache", imgDataStr)
                spEditor.commit()
                //setImage()

                addImageadapter.data.clear()
                addImageadapter.notifyDataSetChanged()
            }

        })
        */
    }
    /*
    private fun setImage() {
        //getImageInfo()

        setImageTexts()
    }

    //get image information (image, tag)
    private fun getImageInfo() {
        val imageStr = profileCache.getString("profileCache", "")
        imgData = gson.fromJson(imageStr, AddImageData::class.java)
    }
    private fun setImageTexts() {
        addtag.text = imgData.tags
        Glide.with(requireContext()).load(imgData.imageURL).into(addimage)
    }
    */
     // start 버튼 눌렸을 때
        //srtBtn.setOnClickListener {
        //doTask("https://pixabay.com/images/search/cat/")

        //pickFromGallery()

        //imageList.apply {
        //   layoutManager = GridLayoutManager(activity, 2)
        //   adapter = AddImageAdapter(List)
        //}

        //imageList.layoutManager = GridLayoutManager(activity, 2)
        //}


    override fun onDestroyView() {
        super.onDestroyView()
    }
}

    /*
    private fun pickFromGallery() {
        var writePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED)
        { // 권한 없어서 요청
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_STORAGE_PERMISSION)
        } else { // 권한 있음
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        //val intent = Intent(Intent.ACTION_PICK)
        //intent.type = "image/*"
        //intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        //startActivityForResult(intent, PICK_IMAGE)
        //registerForActivityResult(ActivityResultContracts.StartActivityForResult())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return
            }
            val selectedImage: Uri? = data.data
            //addimage = view.findViewById(R.id.addimage)
            // into 뒤 null 문제..
            //Glide.with(requireContext()).load(selectedImage).into(requireView().findViewById(R.id.addimage))
            //List.add(AddImageData(addimage))
            //AddImageAdapter(List)
            addimage.setImageURI(selectedImage)
            List.add(AddImageData(addimage))
        }
    }



    fun doTask(url: String) {
        var documentTitle : String = ""
        var itemList : ArrayList<AddImageData> = arrayListOf()
        var count = 0

        //async 동작
        Thread(Runnable {

            var doc = Jsoup.connect(url).get()
            var elements = doc.select("div.container--3NC_b")

            for (e in elements) {
                ++count
                var imgUrl = e.select("a.link--h3bPW").attr("src")
                Log.d("TTT", imgUrl)

                var item = AddImageData(imgUrl)
                itemList.add(item)
                if(count >= 10) break
            }
            documentTitle = doc.title()

        }).start()

        imageList.adapter = AddImageAdapter(itemList)

        }
     */