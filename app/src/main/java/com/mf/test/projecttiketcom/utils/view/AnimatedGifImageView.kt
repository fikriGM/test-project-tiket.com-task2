package com.mf.test.projecttiketcom.utils.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Movie
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

/*
* Created by Fikri on 20/09/2020
* */

class AnimatedGifImageView: AppCompatImageView {
    internal var animatedGifImage = false
    private var `is`: InputStream? = null
    private var mMovie: Movie? = null
    private var mMovieStart:Long = 0
    private var mType = TYPE.FIT_CENTER
    internal var p: Paint? = null
    private var mScaleH = 1f
    private var mScaleW = 1f
    private var mMeasuredMovieWidth:Int = 0
    private var mMeasuredMovieHeight:Int = 0
    private var mLeft:Float = 0.toFloat()
    private var mTop:Float = 0.toFloat()
    enum class TYPE {
        FIT_CENTER, STREACH_TO_FIT, AS_IS
    }
    constructor(context: Context, attrs: AttributeSet,
                defStyle:Int) : super(context, attrs, defStyle) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context) : super(context) {}
    fun setAnimatedGif(rawResourceId:Int, streachType:TYPE) {
        setImageBitmap(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mType = streachType
        animatedGifImage = true
        `is` = getContext().getResources().openRawResource(rawResourceId)
        try
        {
            mMovie = Movie.decodeStream(`is`)
        }
        catch (e:Exception) {
            e.printStackTrace()
            val array = streamToBytes(`is`!!)
            mMovie = Movie.decodeByteArray(array, 0, array.size)
        }
        p = Paint()
    }
    @Throws(FileNotFoundException::class)
    fun setAnimatedGif(filePath:String, streachType:TYPE) {
        setImageBitmap(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mType = streachType
        animatedGifImage = true
        val `is`: InputStream
        try
        {
            mMovie = Movie.decodeFile(filePath)
        }
        catch (e:Exception) {
            e.printStackTrace()
            `is` = FileInputStream(filePath)
            val array = streamToBytes(`is`)
            mMovie = Movie.decodeByteArray(array, 0, array.size)
        }
        p = Paint()
    }
    fun setAnimatedGif(byteArray:ByteArray, streachType:TYPE) {
        setImageBitmap(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        mType = streachType
        animatedGifImage = true
        try
        {
            mMovie = Movie.decodeByteArray(byteArray, 0, byteArray.size)
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
        p = Paint()
    }
    override fun setImageResource(resId:Int) {
        animatedGifImage = false
        super.setImageResource(resId)
    }

    override fun setImageURI(uri: Uri?) {
        animatedGifImage = false
        super.setImageURI(uri)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        animatedGifImage = false
        super.setImageDrawable(drawable)
    }

    private fun streamToBytes(`is`: InputStream):ByteArray {
        val os = ByteArrayOutputStream(1024)
        val buffer = ByteArray(1024)
        val len:Int
        try
        {
            while ((`is`.read(buffer)) >= 0)
            {
                os.write(buffer, 0, `is`.read(buffer))
            }
        }
        catch (e:java.io.IOException) {}
        return os.toByteArray()
    }

    override fun onMeasure(widthMeasureSpec:Int, heightMeasureSpec:Int) {
        if (mMovie != null)
        {
            val movieWidth = mMovie!!.width()
            val movieHeight = mMovie!!.height()
            /*
                   * Calculate horizontal scaling
                   */
            val measureModeWidth = MeasureSpec.getMode(widthMeasureSpec)
            var scaleW = 1f
            var scaleH = 1f
            if (measureModeWidth != MeasureSpec.UNSPECIFIED)
            {
                val maximumWidth = MeasureSpec.getSize(widthMeasureSpec)
                if (movieWidth > maximumWidth)
                {
                    scaleW = movieWidth.toFloat() / maximumWidth.toFloat()
                }
                else
                {
                    scaleW = maximumWidth.toFloat() / movieWidth.toFloat()
                }
            }
            /*
                   * calculate vertical scaling
                   */
            val measureModeHeight = MeasureSpec.getMode(heightMeasureSpec)
            if (measureModeHeight != MeasureSpec.UNSPECIFIED)
            {
                val maximumHeight = MeasureSpec.getSize(heightMeasureSpec)
                if (movieHeight > maximumHeight)
                {
                    scaleH = movieHeight.toFloat() / maximumHeight.toFloat()
                }
                else
                {
                    scaleH = maximumHeight.toFloat() / movieHeight.toFloat()
                }
            }
            /*
                   * calculate overall scale
                   */
            when (mType) {
                AnimatedGifImageView.TYPE.FIT_CENTER -> {
                    mScaleW = Math.min(scaleH, scaleW)
                    mScaleH = mScaleW
                }
                AnimatedGifImageView.TYPE.AS_IS -> {
                    mScaleW = 1f
                    mScaleH = mScaleW
                }
                AnimatedGifImageView.TYPE.STREACH_TO_FIT -> {
                    mScaleH = scaleH
                    mScaleW = scaleW
                }
            }
            mMeasuredMovieWidth = (movieWidth * mScaleW).toInt()
            mMeasuredMovieHeight = (movieHeight * mScaleH).toInt()
            setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight)
        }
        else
        {
            setMeasuredDimension(getSuggestedMinimumWidth(),
                getSuggestedMinimumHeight())
        }
    }

    override fun onLayout(changed:Boolean, l:Int, t:Int, r:Int, b:Int) {
        super.onLayout(changed, l, t, r, b)
        mLeft = (getWidth() - mMeasuredMovieWidth) / 2f
        mTop = (getHeight() - mMeasuredMovieHeight) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (animatedGifImage)
        {
            val now = android.os.SystemClock.uptimeMillis()
            if (mMovieStart == 0L)
            { // first time
                mMovieStart = now
            }
            if (mMovie != null)
            {
                p?.setAntiAlias(true)
                var dur = mMovie!!.duration()
                if (dur == 0)
                {
                    dur = 1000
                }
                val relTime = ((now - mMovieStart) % dur).toInt()
                mMovie!!.setTime(relTime)
                //canvas.save(Canvas.MATRIX_SAVE_FLAG);
                canvas.save()
                canvas.scale(mScaleW, mScaleH)
                mMovie!!.draw(canvas, mLeft / mScaleW, mTop / mScaleH)
                canvas.restore()
                invalidate()
            }
        }
    }
}