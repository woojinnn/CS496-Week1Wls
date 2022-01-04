package com.example.week1wls.ui.contact

import android.Manifest
import android.content.Intent
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1wls.R
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment : Fragment() {
    private val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)
    private lateinit var adapter:ContactAdapter
    private var list = mutableListOf<ContactItem>()
    var searchText = ""
    private var sortText = "asc"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAndStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun startProcess() {
        setList()
        setAddContactListener()
        setSearchListener()
        setRadioListener()
    }

    private fun setList() {
        list.addAll(getPhoneNumbers(sortText, searchText))
        adapter = ContactAdapter(list, requireContext())
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }

    private fun setAddContactListener() {
        // add contact button
        addContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        changeList()
    }

    private fun setSearchListener() {
        editSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                changeList()
            }
        })
    }

    private fun setRadioListener() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radioAsc -> sortText = "asc"
                R.id.radioDsc -> sortText = "desc"
            }
            changeList()
        }
    }

    fun changeList() {
        val newList = getPhoneNumbers(sortText, searchText)
        this.list.clear()
        this.list.addAll(newList)
        this.adapter.notifyDataSetChanged()
    }

    private fun getPhoneNumbers(sort:String, searchName:String?) : List<ContactItem> {
        // return value
        val list = mutableListOf<ContactItem>()

        // 1. Phone Number Uri
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        // 2.1 전화번호에서 가져올 컬럼 정의
        val projections = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        // 2.2 조건 정의
        var where:String? = null
        var values:Array<String>? = null
        // searchName에 값이 있을 때만 검색을 사용한다
        if(searchName?.isNotEmpty() ?: false) {
            where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ?"
            values = arrayOf("%$searchName%")
        }
        // 2.3 정렬쿼리 사용
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        // 3. 테이블에서 주소록 데이터 쿼리
        val cursor = requireActivity().contentResolver.query(phoneUri, projections, where, values, optionSort)

        // 4. 반복문으로 아이디와 이름을 가져오면서 전화번호 조회 쿼리를 한번 더 돌린다.
        while(cursor?.moveToNext()?:false) {
            val id = cursor?.getString(0)   // CONTACT_ID
            val name = cursor?.getString(1) // DISPLAY_NAME
            var number = cursor?.getString(2)   // NUMBER

            // 개별 전화번호 데이터 생성
            val phone = ContactItem(id, name, number)
            // 결과목록에 더하기
            list.add(phone)
        }

        // 결과목록 반환
        return list
    }

    // 권한처리 코드
    private fun checkAndStart() {
        if( isLower23() || isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 99)
        }
    }
    private fun isLower23() : Boolean{
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermitted():Boolean {
        for(perm in permissions) {
            if(checkSelfPermission(requireContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // May be deleted
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 99) {
            var check = true
            for(grant in grantResults) {
                if(grant != PackageManager.PERMISSION_GRANTED) {
                    check = false
                    break
                }
            }
            if(check) startProcess()
            else {
                Toast.makeText(requireContext(), "권한 승인을 하셔야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }
}