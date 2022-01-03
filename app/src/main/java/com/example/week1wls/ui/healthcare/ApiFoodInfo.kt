package com.example.week1wls.ui.healthcare

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder

class ApiFoodInfo(private val keyName: String): Thread() {
    lateinit var foodList: List<FoodData>

    override fun run() {
        super.run()
        foodList = getData()
    }

    private fun getData(): List<FoodData> {
        // site url
        val base_url = "http://openapi.foodsafetykorea.go.kr/api/6abc28c56a754c01843f/I2790/json/1/100/DESC_KOR="
        val site = base_url + "\"${keyName}\""

        val url = URL(site)
        val conn = url.openConnection()
        val input = conn.getInputStream()
        val isr = InputStreamReader(input)
        // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
        val br = BufferedReader(isr)

        // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
        var str: String? = null
        val buf = StringBuffer()

        do{
            str = br.readLine()

            if(str!=null){
                buf.append(str)
            }
        }while (str!=null)

        // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴
        val root = JSONObject(buf.toString())
        val items = root.getJSONObject("I2790").getJSONArray("row")

        // return value
        val foodArr = mutableListOf<FoodData>()

        for (i in 0 until items.length()) {
            val jObject = items.getJSONObject(i)

            val foodName = JSON_Parse(jObject, "DESC_KOR")
            if (foodName == "no data") {
                continue
            }

            val servingWt = JSON_Parse(jObject, "SERVING_SIZE").toDouble()
            val nutrCont1 = getDouble(JSON_Parse(jObject, "NUTR_CONT1"))
            val nutrCont2 = getDouble(JSON_Parse(jObject, "NUTR_CONT2"))
            val nutrCont3 = getDouble(JSON_Parse(jObject, "NUTR_CONT3"))
            val nutrCont4 = getDouble(JSON_Parse(jObject, "NUTR_CONT4"))
            val nutrCont5 = getDouble(JSON_Parse(jObject, "NUTR_CONT5"))
            val nutrCont6 = getDouble(JSON_Parse(jObject, "NUTR_CONT6"))
            val nutrCont7 = getDouble(JSON_Parse(jObject, "NUTR_CONT7"))
            val nutrCont8 = getDouble(JSON_Parse(jObject, "NUTR_CONT8"))
            val nutrCont9 = getDouble(JSON_Parse(jObject, "NUTR_CONT9"))
            val makerName = JSON_Parse(jObject, "MAKER_NAME")

            val foodData = FoodData(foodName, servingWt, nutrCont1, nutrCont2, nutrCont3, nutrCont4, nutrCont5, nutrCont6, nutrCont7, nutrCont8, nutrCont9, makerName)
            foodArr.add(foodData)
        }

        return foodArr
    }

    fun getDouble(num: String): Double? {
        return try{
            num.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }

    // 함수를 통해 데이터를 불러온다.
    private fun JSON_Parse(obj: JSONObject, data: String): String {
        // 원하는 정보를 불러와 리턴받고 없는 정보는 캐치하여 "없습니다."로 리턴받는다.
        return try {
            obj.getString(data)
        } catch (e: Exception) {
            "no data"
        }
    }
}