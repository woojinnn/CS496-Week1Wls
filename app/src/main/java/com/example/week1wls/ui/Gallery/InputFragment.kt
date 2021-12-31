package com.example.week1wls.ui.Gallery

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week1wls.R
import kotlinx.android.synthetic.main.fragment_add_image.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_input.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class InputFragment : Fragment() {

    lateinit var input : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* input 받기 */
        // search 버튼 눌렸을 때
        srhBtn.setOnClickListener {
            input = inputText.text.toString()
            inputText.text = null
            findMyTag(input)
        }

        /* Crawling */
        // add 버튼 눌렸을 떄
        addBtn.setOnClickListener {
            imageList.layoutManager = GridLayoutManager(context, 2)
            doTask("https://pixabay.com/images/search/cat/")

        }
    }

    fun findMyTag(input: String){
        val count = GalleryAdapter().dataList.size
        for(i: Int in 1..count){
            if (GalleryAdapter().dataList[i].tag == input){
                // test
                srhBtn.text = "find"
                // 찐, alert 창
                val builder = AlertDialog.Builder(context)
                builder.setTitle("..")
                    .setMessage(GalleryAdapter().dataList[i].img)
                    .setPositiveButton("저장",
                        {dialog, id ->
                            // 저장 버튼 클릭 이벤트 추가
                            //addImage(tag) : tag 변수로 입력 받기

                        })
                    .setNegativeButton("취소",
                        {dialog, id ->
                            // 취소 버튼 클릭 이벤트 추가
                        })
                builder.show()
            }
        }
    }

    // 이미지 저장
    fun addImage(tag: String){

    }

    fun doTask(url: String){
        var documentTitle : String = ""
        var itemList : ArrayList<AddImageData> = arrayListOf()
        /*
        Single.fromCallable {
            try {
                // 사이트에 접속해서 HTML 문서 가져오기
                val doc = Jsoup.connect(url).get()

                // HTML 파싱해서 데이터 추출하기
                // ul.lst_detail_t1아래의 li 태그만 가져오기
                val elements : Elements = doc.select("ul.lst_detail_t1 li")
                // (여러개의) elements 처리
                run elemLoop@{
                    elements.forEachIndexed{ index, elem ->
                        // elem은 하나의 li를 전달해줌
                        var image = elem.select("div.thumb a img").attr("src")

                        // MovieItem 아이템 생성 후 추가
                        var item = AddImageData(image)
                        itemList.add(item)

                        // 10개만 가져오기
                        if (index == 9) return@elemLoop
                    }
                }

                // 올바르게 HTMl 문서 가져왔다면 title로 바꾸기
                documentTitle = doc.title()
            } catch (e : Exception) {e.printStackTrace()}

            return@fromCallable documentTitle   // subscribe 호출
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                // documentTitle 응답 성공 시
                { text ->
                    // 리사이클러뷰에 아이템 연결
                    imageList.adapter = AddImageAdapter(itemList)
                },
                // documentTitle 응답 오류 시
                { it.printStackTrace() })
        }
         */
    }

}