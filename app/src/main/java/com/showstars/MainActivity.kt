package com.showstars

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var evaluateList = ArrayList<String>().apply {
        add("非常不满意")
        add("不满意")
        add("还行")
        add("较好")
        add("非常满意")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ratingbarview2.setOnRatingListener(object : RatingBarView.OnRatingListener {
            override fun onRating(bindObject: Any?, RatingScore: Int) {

            }

        })

        ratingbarview2.setTextList(evaluateList)

    }
}
