package com.example.week1wls.ui.healthcare

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class ApiFoodInfo(private val keyName: String) : Thread() {
    lateinit var foodList: MutableList<FoodData>

    override fun run() {
        super.run()
        foodList = getData()
    }

    private fun getData(): MutableList<FoodData> {
        // site url
        val base_url =
            "http://openapi.foodsafetykorea.go.kr/api/6abc28c56a754c01843f/I2790/json/1/100/DESC_KOR="
        val site = base_url + "\"${keyName}\""

        val url = URL(site)
        val conn = url.openConnection()
        val input = conn.getInputStream()
        val isr = InputStreamReader(input)
        val br = BufferedReader(isr)

        var str: String? = null
        val buf = StringBuffer()

        do {
            str = br.readLine()

            if (str != null) {
                buf.append(str)
            }
        } while (str != null)

        val root = JSONObject(buf.toString()).getJSONObject("I2790")

        if (JSON_Parse(root, "total_count") == "0") {
            return mutableListOf<FoodData>()
        }

        val items = root.getJSONArray("row")

        // return value
        val foodArr = mutableListOf<FoodData>()

        for (i in 0 until items.length()) {
            val jObject = items.getJSONObject(i)

            val foodName = JSON_Parse(jObject, "DESC_KOR")
            if (foodName == "no data") {
                continue
            }

            val servingWt = getDouble(JSON_Parse(jObject, "SERVING_SIZE"))
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

            val nutrList = listOf(
                nutrCont1,
                nutrCont2,
                nutrCont3,
                nutrCont4,
                nutrCont5,
                nutrCont6,
                nutrCont7,
                nutrCont8,
                nutrCont9
            )
            if (nutrList.size == nutrList.filterNotNull().size) {
                val foodData = FoodData(
                    foodName,
                    servingWt!!,
                    nutrCont1!!,
                    nutrCont2!!,
                    nutrCont3!!,
                    nutrCont4!!,
                    nutrCont5!!,
                    nutrCont6!!,
                    nutrCont7!!,
                    nutrCont8!!,
                    nutrCont9!!,
                    makerName,
                    0.toFloat()
                )
                foodArr.add(foodData)
            }
        }

        return foodArr
    }

    private fun getDouble(num: String): Double? {
        return try {
            num.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }

    // get data
    private fun JSON_Parse(obj: JSONObject, data: String): String {
        return try {
            obj.getString(data)
        } catch (e: Exception) {
            "no data"
        }
    }
}