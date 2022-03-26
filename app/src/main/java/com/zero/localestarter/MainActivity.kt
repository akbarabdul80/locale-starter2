package com.zero.localestarter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.zero.localestarter.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var mNumberFormat: NumberFormat = NumberFormat.getInstance()
    private var mPrice = 0.10
    var mIdExchangeRate = 14000.0
    var mEgExchangeRate = 18.5
    private var mInputQuantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            fab.setOnClickListener {
                showHelp()
            }
            val myDate = Date()
            val expirationDate = myDate.time + TimeUnit.DAYS.toMillis(5)
            myDate.time = expirationDate
            binding.date.text = DateFormat.getDateInstance().format(myDate)

            val deviceLocale = Locale.getDefault().country

            when {
                deviceLocale === "ID" -> {
                    mPrice *= mIdExchangeRate
                }
                deviceLocale === "EG" -> {
                    mPrice *= mEgExchangeRate
                }
                else -> {
                    mNumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
                }
            }


            binding.quantity.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val imm: InputMethodManager = textView.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    imm.hideSoftInputFromWindow(textView.windowToken, 0)
                    binding.quantity
                    if (textView.text.toString() != "") {
                        try {
                            mInputQuantity = mNumberFormat.parse(textView.text.toString()).toInt()
                        } catch (e: ParseException) {
                            textView.error = getText(R.string.enter_number)
                            e.printStackTrace()
                        }

                        val formattedQuantity: String = mNumberFormat.format(mInputQuantity)
                        textView.text = formattedQuantity
                    }
                }
                false

            }
        }
    }

    private fun showHelp() {
        val helpIntent = Intent(this, HelpActivity::class.java)
        startActivity(helpIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_help) {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
            return true
        } else if (id == R.id.action_language) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}