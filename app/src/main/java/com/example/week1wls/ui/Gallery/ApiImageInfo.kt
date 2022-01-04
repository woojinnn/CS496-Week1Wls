package com.example.week1wls.ui.Gallery

import android.util.Log
import com.example.week1wls.ui.healthcare.FoodData
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class ApiImageInfo(private val keyName: String): Thread() {
    lateinit var imageList: MutableList<AddImageData>

    override fun run(){
        super.run()
        imageList = getData()
    }

    private fun getData(): MutableList<AddImageData> {
        // site url
        val base_url = "https://pixabay.com/api/?key=25068713-f2c388dc6c7920371c0c886a7&q="
        val site = base_url + "\"${keyName}\"" + "&image_type=photo"

        val url = URL(site)
        val conn = url.openConnection()
        val input = conn.getInputStream()
        val isr = InputStreamReader(input)
        val br = BufferedReader(isr)

        var str: String? = null
        val buf = StringBuffer()

        do{
            str = br.readLine()

            if(str!=null){
                buf.append(str)
            }
        } while(str != null)

        val root = JSONObject(buf.toString())

        val items = root.getJSONArray("hits")

        // return value
        val imageArr = mutableListOf<AddImageData>()

        for (i in 0 until 20) {
            val jObject = items.getJSONObject(i)
            Log.d("item", jObject.toString())

            val imageID = JSON_Parse(jObject, "id")
            if (imageID == "no data") {
                continue
            }

            val pageURL = JSON_Parse(jObject, "pageURL")
            val tags = JSON_Parse(jObject, "tags")
            val imageURL = JSON_Parse(jObject, "largeImageURL")
            val views = JSON_Parse(jObject, "views").toInt()
            val downloads = JSON_Parse(jObject, "downloads").toInt()
            val likes = JSON_Parse(jObject, "likes").toInt()

            val infoList = listOf(pageURL, tags, imageURL, views, downloads, likes)
            if(infoList.size == infoList.filterNotNull().size) {
                val imgData = AddImageData(pageURL, tags, imageURL, views, downloads, likes)
                imageArr.add(imgData)
            }
        }

        return imageArr
    }

    // parse json
    private fun JSON_Parse(obj: JSONObject, data: String): String {
        return try {
            obj.getString(data)
        } catch (e: Exception) {
            "no data"
        }
    }

}