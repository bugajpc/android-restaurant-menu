package com.example.restaurant_menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //https://bestmeals.pl/menu-catering-dla-firm-jakiego-jeszcze-nie-znasz/
    private val db = Firebase.firestore
    private var courseList = mutableListOf<Course>()
    private var coursesAdapter = CoursesAdapter(courseList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val coursesRef = db.collection("courses")

        val coursesRecyclerView: RecyclerView = findViewById(R.id.courses_recyclerView)

        coursesRecyclerView.layoutManager = LinearLayoutManager(this)

        coursesRef.get()
            .addOnSuccessListener { querySnapshot ->


                for (document in querySnapshot) {
                    val title = document.getString("title") ?: ""
                    val imageUrl = document.getString("imageUrl") ?: ""

                    val course = Course(title, imageUrl)
                    courseList.add(course)
                }
                coursesAdapter = CoursesAdapter(courseList)
                coursesRecyclerView.adapter = coursesAdapter
                // Now you have the list of Course objects (courseList).
                // You can use this list to populate your UI, e.g., in a RecyclerView.
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreTag", "Error getting documents: ", exception)
            }

    }
}