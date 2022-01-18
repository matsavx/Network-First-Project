package com.example.networkproject

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.net.URL
import com.example.networkproject.utils.NetworkUtils

class MainActivity : AppCompatActivity() {

    lateinit var searchField: EditText
    lateinit var searchBtn: Button
    lateinit var result: TextView
    val networkUtils: NetworkUtils = NetworkUtils()
    lateinit var generatedUrl: URL
//    lateinit var response: String
    lateinit var img: ImageView

    class VKQueryTask constructor(_result:TextView, _img:ImageView): AsyncTask<URL, Void, String>() {
        lateinit var result: TextView
        lateinit var img: ImageView
        init {
            result = _result
            img = _img
        }

        override fun doInBackground(vararg urls: URL): String {
            val networkUtils: NetworkUtils = NetworkUtils()
            return networkUtils.getResponseFromUrl(urls[0]).toString()
        }

        override fun onPostExecute(response: String) {
            val photoUrl: String = response.substring(response.indexOf("https"), response.length - 4)
            Picasso.get()
                .load(photoUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(img)
            val firstName: String = response.substring(response.indexOf("first_name")+13, response.indexOf("last_name")-3)
            val lastName: String = response.substring(response.indexOf("last_name")+12, response.indexOf("can_access_closed")-3)
            result.setText(firstName + " " + lastName)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchField = findViewById(R.id.et_search_field)
        searchBtn = findViewById(R.id.b_search_vk)
        result = findViewById(R.id.tv_result)
        img = findViewById(R.id.avatar_id)
        searchBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                generatedUrl = networkUtils.generateUrl(searchField.text.toString())
//                response = networkUtils.getResponseFromUrl(generatedUrl).toString()
//                result.setText(response)
                VKQueryTask(result, img).execute(generatedUrl)
            }
        })
    }
}