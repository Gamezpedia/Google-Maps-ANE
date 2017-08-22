/*
 *  Copyright 2017 Tua Rua Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tuarua.googlemapsane
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.tuarua.MessageEvent
import org.greenrobot.eventbus.EventBus

// https://stackoverflow.com/questions/25824288/how-to-close-dialog-themed-activity?noredirect=1&lq=1

class PermissionActivity : Activity(), ActivityCompat.OnRequestPermissionsResultCallback {
    //inner class MessageEvent(val message: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            EventBus.getDefault().post(MessageEvent(Constants.AUTHORIZATION_GRANTED))
        } else {
            val permissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, 19001)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            19001 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EventBus.getDefault().post(MessageEvent(Constants.AUTHORIZATION_GRANTED))
            } else {
                EventBus.getDefault().post(MessageEvent(Constants.AUTHORIZATION_DENIED))
            }
        }
        finish()
    }
}
