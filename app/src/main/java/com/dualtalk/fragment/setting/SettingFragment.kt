package com.dualtalk.fragment.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.fragment.all_chat.AllChatViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: SettingFragmentViewModel

    private lateinit var btnChooseImageAvartar:Button
    private lateinit var UserAvartar : ShapeableImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(SettingFragmentViewModel::class.java)
        val mview =  inflater.inflate(R.layout.fragment_setting, container, false)

        //xử lý hiển thị ảnh
        UserAvartar = mview.findViewById(R.id.avartar)
        btnChooseImageAvartar = mview.findViewById(R.id.chonanhdaidien)
        val Useravartar = registerForActivityResult(ActivityResultContracts.GetContent() , ActivityResultCallback {
            UserAvartar.setImageURI(it)
            viewModel.UpLoadAvartarToClound(it)
        })
        btnChooseImageAvartar.setOnClickListener {
            Useravartar.launch("image/*")
        }








        viewModel.uiState.observe(viewLifecycleOwner){
            if(it == SettingFragmentViewModel.ChooseImageAvatar.Success){
                Toast.makeText(mview.context , "Up anh thanh cong" , Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(mview.context , "Up anh ko thanh cong" , Toast.LENGTH_SHORT).show()
            }
        }


        return mview
    }

}