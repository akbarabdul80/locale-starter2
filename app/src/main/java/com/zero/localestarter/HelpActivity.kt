package com.zero.localestarter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zero.localestarter.databinding.ActivityHelpBinding


class HelpActivity : AppCompatActivity() {
    private val TAG = HelpActivity::class.java.simpleName
    private val binding: ActivityHelpBinding by lazy { ActivityHelpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            val phoneNumber = getString(R.string.support_phone)
            callSupportCenter(phoneNumber)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun callSupportCenter(phoneNumber: String) {
        // Format the phone number for dialing.
        val formattedNumber = String.format("tel: %s", phoneNumber)
        // Create the intent.
        val dialIntent = Intent(Intent.ACTION_DIAL)
        // Set the formatted phone number as data for the intent.
        dialIntent.data = Uri.parse(formattedNumber)
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(packageManager) != null) {
            startActivity(dialIntent)
        } else {
            Log.e(TAG, getString(R.string.dial_log_message))
        }
    }
}