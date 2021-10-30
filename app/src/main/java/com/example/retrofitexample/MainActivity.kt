package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view_result)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: JSONPlaceHolderApi = retrofit.create(JSONPlaceHolderApi::class.java)

        //Launching Coroutine
        GlobalScope.launch {

            withContext(Dispatchers.IO){

                val response: Response<List<Post>> = api.getPosts()

                if(!response.isSuccessful){
                    withContext(Dispatchers.Main){
                        textView.text = response.errorBody().toString()
                    }
                    return@withContext
                }

                if(response.body() != null){
                    for(post in response.body()!!){
                        val content = "userId: ${post.userId} \n id: ${post.id} \n title: ${post.title} \n body: ${post.text} \n \n"
                        withContext(Dispatchers.Main){
                            textView.append(content)
                        }

                    }
                }
            }
        }


        //Using callback
       /* result.enqueue(object: Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(!response.isSuccessful){
                    textView.text = response.errorBody().toString()
                    return
                }

                val posts: List<Post>? = response.body()

                if (posts != null) {
                    for(post in posts){

                        val content = "userId: ${post.userId} \n id: ${post.id} \n title: ${post.title} \n body: ${post.text} \n \n"
                        textView.append(content)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                textView.text = t.message
            }
        })*/
    }
}