package com.dualtalk.activity.editinfouser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.databinding.ActivityEditInfoUserBinding

class EditInfoUserActivity : AppCompatActivity() {
    private lateinit var dataBiding : ActivityEditInfoUserBinding
    private lateinit var viewModel: EditInfoUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBiding = DataBindingUtil.setContentView(this , R.layout.activity_edit_info_user)
        dataBiding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(EditInfoUserViewModel::class.java)

        dataBiding.edtEditInfoBtn.setOnClickListener {
            viewModel.UpdateInfoUser(dataBiding.edtEditInfoFullname.text.toString())
        }

        viewModel.updateInfoState.observe(this){
            if(it == EditInfoUserViewModel.UpdateInfoState.Success){
                Toast.makeText(this ,"Update success" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}