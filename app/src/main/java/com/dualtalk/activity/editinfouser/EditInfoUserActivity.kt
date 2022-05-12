package com.dualtalk.activity.editinfouser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.databinding.ActivityEditInfoUserBinding

class EditInfoUserActivity : AppCompatActivity() {
    private lateinit var dataBiding : ActivityEditInfoUserBinding
    private lateinit var viewModel: EditInfoUserViewModel
    val useravartar = registerForActivityResult(ActivityResultContracts.GetContent()) {
        dataBiding.edtEditInfoImg.setImageURI(it)
        viewModel.UpLoadAvartarToClound(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBiding = DataBindingUtil.setContentView(this , R.layout.activity_edit_info_user)
        dataBiding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(EditInfoUserViewModel::class.java)

        dataBiding.edtEditInfoBtn.setOnClickListener {
            viewModel.UpdateInfoUser(dataBiding.edtEditInfoFullname.text.toString())
            finish()
        }

        dataBiding.edtEditInfoImg.setOnClickListener{
            useravartar.launch("image/*")
        }

        viewModel.updateInfoState.observe(this){
            if(it == EditInfoUserViewModel.UpdateInfoState.Success){
                Toast.makeText(this ,"Update success!!" , Toast.LENGTH_SHORT).show()
            }
            if(it == EditInfoUserViewModel.UpdateInfoState.UpLoadImageSucess){
                Toast.makeText(this ,"Your avartar link is : ${viewModel.urlUserAvartar}" , Toast.LENGTH_SHORT).show()
            }
            if(it == EditInfoUserViewModel.UpdateInfoState.Fail){
                Toast.makeText(this ,"Update fail!!" , Toast.LENGTH_SHORT).show()
            }
            if(it == EditInfoUserViewModel.UpdateInfoState.UpLoadImageFail){
                Toast.makeText(this ,"Update Img fail!!" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}