package com.ty.dyzs

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.graphics.Path
import android.os.Build
import android.util.Log
import android.util.SparseArray
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ScreenUtils
import kotlin.concurrent.thread


/**
 *    author : androidty
 *    date   : 2/14/22 2:20 PM
 *    desc   :
 */
class DouYinService : AccessibilityService() {
    var scrolled = true
    override fun onInterrupt() {
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        try {
            if (event == null || event.packageName == null) {
                return
            }

            Log.d("tytest", "onAccessibilityEvent: ${event.packageName}")
            if (event.packageName == "com.ss.android.ugc.aweme" && scrolled) {
                scrolled = false
                val nodeInfo = event.source
//                if (nodeInfo != null && "android.support.v4.ViewPager" == nodeInfo.className) {
//                    var thread = thread {
//                        while (true){
                var delay = kotlin.random.Random.nextInt(3000)
                Thread.sleep((2000 + delay).toLong())
//                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                MainActivityToUp()

                Log.d("tytest", "onAccessibilityEvent: $delay")

//                        }
//                    }
//                    thread.start()
//                }
            }
        } catch (e: Exception) {
            Log.d("tytest", "onAccessibilityEvent: ${e.printStackTrace()}")
        }
    }

    fun startThread(event: AccessibilityEvent) {

    }

    //    上划主页面
    @RequiresApi(Build.VERSION_CODES.N)
    private fun MainActivityToUp() {
        val x: Int = ScreenUtils.getScreenWidth() / 2
        val y: Int = ScreenUtils.getAppScreenHeight() / 2
        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())
        path.lineTo(x.toFloat(), 0f)
        val builder = GestureDescription.Builder()
        builder.addStroke(StrokeDescription(path, 100L, 50L))
        val gesture = builder.build()
        dispatchGesture(gesture, object : AccessibilityService.GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription) {
                super.onCompleted(gestureDescription)
                Log.e("tytest", "onCompleted: 主页上滑.")
                scrolled = true
            }

            override fun onCancelled(gestureDescription: GestureDescription) {
                super.onCancelled(gestureDescription)
            }
        }, null)
    }
}