package com.dualtalk.activity.searchuser

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel

class SearchUserViewModel: ViewModel() , Observable {
    lateinit var mlist : ArrayList<MUser>





    fun getListUser() : List<MUser> {
        mlist = ArrayList()
        mlist.add(MUser("NguyenTienDat@gmail.com","Nguyễn Tiến Đạt","https://taimienphi.vn/tmp/cf/aut/mAKI-top-anh-dai-dien-dep-chat-1.jpg"))
        mlist.add(MUser("NguyenQuangPhuc@gmail.com","Nguyễn Quang Phúc","https://taimienphi.vn/tmp/cf/aut/mAKI-top-anh-dai-dien-dep-chat-1.jpg"))
        mlist.add(MUser("TranVanDai@gmail.com","Trần Văn Đại","https://taimienphi.vn/tmp/cf/aut/mAKI-top-anh-dai-dien-dep-chat-1.jpg"))
        mlist.add(MUser("LeMinhDuc@gmail.com","Lê Minh Đức","https://taimienphi.vn/tmp/cf/aut/mAKI-top-anh-dai-dien-dep-chat-1.jpg"))
        mlist.add(MUser("PhungTienLong@gmail.com","Phùng Tiến Long","https://taimienphi.vn/tmp/cf/aut/mAKI-top-anh-dai-dien-dep-chat-1.jpg"))

        return mlist.toList()
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}