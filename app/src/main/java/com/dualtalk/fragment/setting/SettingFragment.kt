package com.dualtalk.fragment.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dualtalk.R
import com.dualtalk.activity.editinfouser.EditInfoUserActivity
import com.dualtalk.activity.login.LoginActivity
import com.dualtalk.common.CurrentUser
import com.dualtalk.service.NewMessageService
import com.google.android.material.imageview.ShapeableImageView

class SettingFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var viewModel: SettingFragmentViewModel
    private lateinit var txtusername: TextView
    private lateinit var btnEditInfoUser: Button
    private lateinit var UserAvartar: ShapeableImageView

//    val useravartar = registerForActivityResult(ActivityResultContracts.GetContent()) {
//        UserAvartar.setImageURI(it)
//        viewModel.UpLoadAvartarToClound(it)
//    }
//    useravartar.launch("image/*")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(SettingFragmentViewModel::class.java)
        val mview = inflater.inflate(R.layout.fragment_setting, container, false)

        val mlogout = mview.findViewById<Button>(R.id.logout)
        mlogout.setOnClickListener {
            logout()
        }
        //xử lý hiển thị ảnh , thông tin người dùng
        UserAvartar = mview.findViewById(R.id.avartar)
        Glide.with(requireContext()).load(CurrentUser.imgUrl).into(UserAvartar)

        btnEditInfoUser = mview.findViewById(R.id.chinhsuathongtin)
        txtusername = mview.findViewById(R.id.txtUsername)

        txtusername.text = CurrentUser.fullName

        btnEditInfoUser.setOnClickListener {
            val intent = Intent(activity, EditInfoUserActivity::class.java)
            startActivity(intent)
        }

        viewModel.uiState.observe(viewLifecycleOwner) {}

        return mview
    }

    private fun getInfo(view: View, UserAvartar: ShapeableImageView, txtusername: TextView) {
        Glide.with(view).load(viewModel.infouser.imgUrl).into(UserAvartar)
        txtusername.text = viewModel.infouser.fullName
    }

    private fun logout() {
        sharedPreferences =
            activity?.getSharedPreferences("share", Context.MODE_PRIVATE) ?: sharedPreferences
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        //tắt service hiện thông báo khi đăng xuất
        val intent = Intent(requireActivity(), NewMessageService::class.java)
        requireActivity().stopService(intent)

        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finish()
    }
}