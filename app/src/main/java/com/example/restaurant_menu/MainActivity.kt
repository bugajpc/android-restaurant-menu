package com.example.restaurant_menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//https://bestmeals.pl/menu-catering-dla-firm-jakiego-jeszcze-nie-znasz/
class MainActivity : AppCompatActivity() {
    private lateinit var coursesRecyclerView: RecyclerView
    private val db = Firebase.firestore
    private var courseList = mutableListOf<Course>()
    private lateinit var coursesAdapter: CoursesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coursesRef = db.collection("courses")

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        coursesRecyclerView = findViewById(R.id.courses_recyclerView)

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
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreTag", "Error getting documents: ", exception)
            }

        swipeRefreshLayout.setOnRefreshListener {
            courseList.clear()
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
                }
                .addOnFailureListener { exception ->
                    Log.d("FirestoreTag", "Error getting documents: ", exception)
                }
            Log.d("main", "Refreshed")
            swipeRefreshLayout.isRefreshing = false
        }

    }
}