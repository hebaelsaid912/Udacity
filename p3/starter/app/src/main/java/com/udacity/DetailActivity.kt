package com.udacity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        var fileName = intent.getStringExtra("file_name")
        var fileStatus = intent.getBooleanExtra("status",false)
        if(!fileName.isNullOrEmpty()){
            file_name_text.text = fileName
        }else{
            file_name_text.text = "File name not available "
        }
        if(fileStatus){
            file_status_text.text = "Success"
        }else{
            file_status_text.text = "Failed"
            file_status_text.setTextColor(Color.RED)
        }
    }

}
