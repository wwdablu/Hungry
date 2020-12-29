package com.soumya.wwdablu.hungry.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ActivityPhotoViewerBinding

class PhotoViewerActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityPhotoViewerBinding
    private var mList: ArrayList<String>? = null
    private var mIndex: Int = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityPhotoViewerBinding.inflate(layoutInflater)

        mList = intent.getStringArrayListExtra("list")
        mIndex = intent.getIntExtra("index", 0)
        if(mList == null) {
            finish()
            return
        }

        Glide.with(mViewBinding.ivImage)
            .load(mList!![mIndex])
            .placeholder(R.drawable.default_card_bg)
            .into(mViewBinding.ivImage)

        mViewBinding.btnNext.setOnClickListener {

            if(mIndex >= mList!!.size) {
                it.visibility = View.GONE
                return@setOnClickListener
            }

            Glide.with(it.context)
                .load(mList!![mIndex])
                .placeholder(R.drawable.default_card_bg)
                .into(mViewBinding.ivImage)

            mIndex++
        }

        mViewBinding.btnPrev.setOnClickListener {

            if(mIndex < 0) {
                it.visibility = View.GONE
                return@setOnClickListener
            }

            mIndex--

            Glide.with(it.context)
                    .load(mList!![mIndex])
                    .placeholder(R.drawable.default_card_bg)
                    .into(mViewBinding.ivImage)
        }

        setContentView(mViewBinding.root)
    }
}