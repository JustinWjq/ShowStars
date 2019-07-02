### 你有Punchline吗？
最近项目需要做评价控件，就花了点时间写了写，只完成了我项目的基本要求，还有很多可以自己扩展的，很简单的，就可以自己实现了
![实现的效果](https://user-gold-cdn.xitu.io/2019/7/2/16bb0a8abee19f65?w=440&h=782&f=gif&s=1082510)
#### 自定义参数
starImageSize |  单个star的大小
-|-
 isClickable | 点击状态
 showFillStarcount | 设置显示黄色star的数量
starEmptyDrawable | 设置显示灰色star的图片
starFillDrawable | 设置显示黄色star的图片 
OnRatingListener | 点击星星监听
setTextList | 设置满意度文案
```
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

```

