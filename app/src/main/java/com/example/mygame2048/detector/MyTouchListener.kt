package com.example.mygame2048.detector

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.mygame2048.model.SideEnum
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private var movedSideListener : ((SideEnum) -> Unit)? = null
    private val detector = GestureDetector(context, MyGestureDetector())
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        detector.onTouchEvent(event)
        return true
    }
    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener(){
        override fun onFling(
            start: MotionEvent?,
            end: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (start == null) return true
            if (abs(start.x - end.x) < 200 && abs(start.y - end.y) < 200) return true

            if (abs(start.x- end.x) > abs(start.y-end.y)){
                if (start.x > end.x){
                    movedSideListener?.invoke(SideEnum.LEFT)
                }else{
                    movedSideListener?.invoke(SideEnum.RIGHT)
                }
            }else{
                if (start.y > end.y){
                    movedSideListener?.invoke(SideEnum.UP)
                }else{
                    movedSideListener?.invoke(SideEnum.DOWN)
                }
            }
            return true
        }
    }
    fun setMovedSideListener(listener: (SideEnum) -> Unit){
        movedSideListener = listener
    }
}