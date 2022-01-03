package com.example.week1wls.ui.healthcare

data class FoodData(
    val name: String,   // 식품이름
    val SERVING_WT: Double?, // 1회제공량 (g)
    val NUTR_CONT1: Double?, // 열량 (kcal)
    val NUTR_CONT2: Double?, //탄수화물 (g)
    val NUTR_CONT3: Double?, // 단백질 (g)
    val NUTR_CONT4: Double?, // 지방 (g)
    val NUTR_CONT5: Double?, // 당류 (g)
    val NUTR_CONT6: Double?, // 나트륨 (mg)
    val NUTR_CONT7: Double?, // 콜레스테롤 (mg)
    val NUTR_CONT8: Double?, // 포화지방산 (g)
    val NUTR_CONT9: Double?, // 트랜스지방산 (g)
    val MAKER_NAME: String
)
