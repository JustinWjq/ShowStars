package com.showstars

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView



/**
 * Created by JustinWjq
 * @date 2019/6/28.
 * description：一个简单的点星星
 */
class RatingBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {


    private var onRatingListener: OnRatingListener? = null
    private var bindObject: Any? = null

    private var starImageSize: Float = 0.toFloat()//单个star的大小

    private var starCount: Int = 0//star的总数

    private var showFillStarcount: Int = 0 //黄色的star个数

    private var starEmptyDrawable: Drawable? = null //灰色star的背景图片

    private var starFillDrawable: Drawable? = null //黄色star的背景图片

    private var mClickable: Boolean? = null //设置是否可点击

    private var tv: TextView? = null


    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView)
        starImageSize = a.getDimension(R.styleable.RatingBarView_starImageSize, 20f)
        starCount = a.getInteger(R.styleable.RatingBarView_starCount, 5)
        starEmptyDrawable = a.getDrawable(R.styleable.RatingBarView_starEmpty)
        starFillDrawable = a.getDrawable(R.styleable.RatingBarView_starFill)
        mClickable = a.getBoolean(R.styleable.RatingBarView_isClickable, true)
        showFillStarcount = a.getInteger(R.styleable.RatingBarView_showFillStarcount, 1)



        a.recycle()
    }


    fun setCurrentStart(index: Int) {
        setStar(index, LinearLayout(context))
        if (onRatingListener != null) {
            onRatingListener!!.onRating(bindObject, index)
        }
    }

    private var mTextList: ArrayList<String>? = null
    fun setTextList(textList: ArrayList<String>) {
        val starLL = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }
        val viewGrouplp = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            setMargins(0, 20, 0, 20)
        }

        //初始化设置黄色的star个数，怎么把下面的文字也同步到，不通过后面的setlist的操作

        //解决方法，看了tablayout的源码，发现是addtab 后设置 具体统一数据


        tv = TextView(context).apply {
            layoutParams = viewGrouplp
            gravity = Gravity.CENTER
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, R.color.color_ffcf4b))
            text = textList[showFillStarcount - 1]
        }

        for (i in 0 until starCount) {
            if (i < showFillStarcount) { //设置初始化黄色star个数
                starLL.addView(getStarImageView(context, true, starLL))
            } else {
                starLL.addView(getStarImageView(context, false, starLL))
            }

        }


        addView(starLL)
        addView(tv)

        mTextList = textList

    }

    private fun getStarImageView(context: Context, isFull: Boolean, starLL: LinearLayout): ImageView {

        val imageView = ImageView(context)
        val para = ViewGroup.LayoutParams(Math.round(starImageSize), Math.round(starImageSize))
        imageView.apply {
            layoutParams = para
            setPadding(0, 0, 5, 0)
            if (isFull) {
                setImageDrawable(starFillDrawable)
            } else {
                setImageDrawable(starEmptyDrawable)
            }


            maxWidth = 10
            maxHeight = 10
            setOnClickListener {

                if (mClickable!!) {
                    setStar(starLL.indexOfChild(it) + 1, starLL)
                    tv?.text = mTextList?.get(starLL.indexOfChild(it))
                    if (onRatingListener != null) {
                        onRatingListener!!.onRating(bindObject, starLL.indexOfChild(it) + 1)
                    }
                }


            }
        }

        return imageView
    }


    fun setStar(showFillCountStar: Int, starLL: LinearLayout) {


        val showCountStar = if (showFillCountStar > this.starCount) this.starCount else showFillCountStar

        for (i in 0 until showCountStar) {
            (starLL.getChildAt(i) as ImageView).setImageDrawable(starFillDrawable)
        }

        for (i in this.starCount - 1 downTo showCountStar) {
            (starLL.getChildAt(i) as ImageView).setImageDrawable(starEmptyDrawable)
        }

    }

    /**
     * 该监听器用于监听选中Tab时View的变化
     */
    interface OnRatingListener {

        fun onRating(bindObject: Any?, RatingScore: Int)

    }


    fun setStarFillDrawable(starFillDrawable: Drawable) {
        this.starFillDrawable = starFillDrawable
    }

    fun setStarEmptyDrawable(starEmptyDrawable: Drawable) {
        this.starEmptyDrawable = starEmptyDrawable
    }

    fun setStarCount(startCount: Int) {
        this.starCount = starCount
    }

    fun setStarImageSize(starImageSize: Float) {
        this.starImageSize = starImageSize
    }


    fun setBindObject(bindObject: Any) {
        this.bindObject = bindObject
    }

    fun setOnRatingListener(onRatingListener: OnRatingListener) {
        this.onRatingListener = onRatingListener
    }

    fun setmClickable(clickable: Boolean) {
        this.mClickable = clickable
    }
}
