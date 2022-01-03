package com.example.week1wls.ui.Gallery

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
        val site = base_url + "\"${keyName}\"" + ""

        val url = URL(site)
        val conn = url.openConnection()
        val input = conn.getInputStream()
        val isr = InputStreamReader(input)
        val br = BufferedReader(isr)

        // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
        var str: String? = null
        
    }

}