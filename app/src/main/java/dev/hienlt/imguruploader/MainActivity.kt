package dev.hienlt.imguruploader

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.esafirm.imagepicker.features.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("Đang loading...")
        btn_upload.setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        ImagePicker.create(this) // Activity or Fragment
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        progressDialog?.show()
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            var image = ImagePicker.getFirstImageOrNull(data)
            ImgurUploader.uploadImage(image?.path, object : ImgurUploader.OnImgurImageUpload {
                override fun onSuccess(imageUrl: String?) {
                    progressDialog?.dismiss()
                    textView.text = imageUrl
                }

                override fun onError(e: Exception?) {
                    progressDialog?.dismiss()
                    Toast.makeText(this@MainActivity, e?.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


}
