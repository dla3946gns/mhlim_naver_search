package com.example.mhlim_search.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Process
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mhlim_search.R

/**
 * 홈에서 뒤로가기 클릭 시 토스트 노출시키는 Handler
 *
 * @author Myeong Hoon Lim on 2023-02-08
 */
class BackPressFinishHandler(private val activity: Activity) {

    private val BACK_KEY_DURATION: Long = 3000
    private var backKeyPressedTime: Long = 0

    private lateinit var toast: Toast

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_DURATION) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_DURATION) {
            finishApplication()
            cancelToast()
        }
    }

    fun showGuide() {
        toast = Toast.makeText(activity, activity.resources.getString(R.string.str_back_press), Toast.LENGTH_SHORT)
        toast.show()
    }

    fun cancelToast() {
        toast.cancel()
    }

    private fun finishApplication() {
        ActivityCompat.finishAffinity(activity)

        Process.killProcess(Process.myPid())
        val activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        activityManager.killBackgroundProcesses(activity.packageName)
    }
}