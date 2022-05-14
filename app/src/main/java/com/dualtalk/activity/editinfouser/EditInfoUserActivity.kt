package com.dualtalk.activity.editinfouser

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.common.CurrentUser
import com.dualtalk.databinding.ActivityEditInfoUserBinding
import com.dualtalk.loadingdialog.LoadingDialog

class EditInfoUserActivity : AppCompatActivity() {
    private lateinit var dataBiding: ActivityEditInfoUserBinding
    private lateinit var viewModel: EditInfoUserViewModel
    private lateinit var loadingDialog: LoadingDialog
    private var mUri = Uri.EMPTY
    val useravartar = registerForActivityResult(ActivityResultContracts.GetContent()) {
        dataBiding.edtEditInfoImg.setImageURI(it) // ở front end
        if(it != null){
            this.mUri = it

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBiding = DataBindingUtil.setContentView(this, R.layout.activity_edit_info_user)
        dataBiding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(EditInfoUserViewModel::class.java)
        loadingDialog = LoadingDialog(this@EditInfoUserActivity)

        dataBiding.edtEditInfoBtn.setOnClickListener {
            if(dataBiding.edtEditInfoFullname.text.toString().isNullOrBlank() && this.mUri == Uri.EMPTY){
                finish()
            }
            viewModel.UpdateInfoUser(dataBiding.edtEditInfoFullname.text.toString())

            if(this.mUri  != null){
                viewModel.UpLoadAvartarToClound(this.mUri)
            }
            loadingDialog.startLoadingDialog()
        }

        dataBiding.edtEditInfoImg.setOnClickListener {
            useravartar.launch("image/*")
        }

        viewModel.updateInfoState.observe(this) {
            if (it == EditInfoUserViewModel.UpdateInfoState.Success) {
                Toast.makeText(this, "Update success!!", Toast.LENGTH_SHORT).show()
                CurrentUser.fullName = viewModel.name
                loadingDialog.dismissDialog()
                finish()
            }
            if (it == EditInfoUserViewModel.UpdateInfoState.UpLoadImageSucess) {
                Toast.makeText(this, "Upload image success", Toast.LENGTH_SHORT).show()
                loadingDialog.dismissDialog()
                finish()
            }
            if (it == EditInfoUserViewModel.UpdateInfoState.Fail) {
                Toast.makeText(this, "Update fail!!", Toast.LENGTH_SHORT).show()
                finish()
            }
            if (it == EditInfoUserViewModel.UpdateInfoState.UpLoadImageFail) {
                Toast.makeText(this, "Update Img fail!!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}