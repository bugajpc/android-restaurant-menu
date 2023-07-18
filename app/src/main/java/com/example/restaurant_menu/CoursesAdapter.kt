package com.example.restaurant_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CoursesAdapter(var courses: MutableList<Course>) : RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {
    inner class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.courses_item, parent, false)
        return CoursesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val courseTextView: TextView = holder.itemView.findViewById(R.id.course_titile_textView)
        val courseImageView: ImageView = holder.itemView.findViewById(R.id.course_imageView)

        courseTextView.text = courses[position].title
        //change the following to courses[position].imageUrl
        Picasso.get().load(courses[position].imageUrl).into(courseImageView)
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}