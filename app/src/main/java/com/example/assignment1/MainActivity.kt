package com.example.assignment1

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var toggleButton: Button
    private val climber: Climber = Climber()
    private var climberScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val scoreView: TextView = findViewById(R.id.scoreId)
        val scoreTextView: TextView = findViewById(R.id.scoreId2)
        // Climb button
        val climbView: Button = findViewById(R.id.button)
        climbView.setOnClickListener {
            val afterClimbScore = climber.climb()
            scoreView.text = String.format(afterClimbScore.toString())
            scoreView.setTextColor(climber.scoreColorManagement())
            scoreTextView.setTextColor(climber.scoreColorManagement())
            Log.d("Status", "Climb up 1 step")
        }

        // Fall button
        val fallView: Button = findViewById(R.id.button2)
        fallView.setOnClickListener {
            val afterFallScore = climber.fall()
            scoreView.text = String.format(afterFallScore.toString())
            climbView.isEnabled = false  // deactivate climb
            fallView.isEnabled = false   // deactivate fall
            scoreView.setTextColor(climber.scoreColorManagement())
            scoreTextView.setTextColor(climber.scoreColorManagement())
            Log.d("Status", "Fall")
        }

        // Reset Button
        val resetView: Button = findViewById(R.id.button3)
        resetView.setOnClickListener {
            val afterResetScore = climber.reset()
            scoreView.text = String.format(afterResetScore.toString())
            climbView.isEnabled = true
            fallView.isEnabled = true
            scoreView.setTextColor(climber.scoreColorManagement())
            scoreTextView.setTextColor(climber.scoreColorManagement())
            Log.d("Status", "Reset")
        }

        // Toggle switch language button
        toggleButton = findViewById(R.id.toggleButton)

        toggleButton.setOnClickListener {
            Log.d("ButtonClick", "Changing-language button clicked")    // Log clicking button
            val newLocale = if (getSavedLanguage(this) == "en") Locale("zh") else Locale("en")
            setAppLocale(this, newLocale)
            Log.d("Status", "Language locale changed")
        }
    }

    // Function changing the locale
    private fun setAppLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("language", locale.language).apply()

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    // get saved language preference
    private fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("language", "en") ?: "en"
    }

    // Saving the score when rotation
    private fun getClimberScore(): Int {
        return climber.score
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        climberScore = getClimberScore()
        outState.putInt("score", climberScore)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("score")) {  // debug log
            Log.d("Status", "score found!")}
        else {Log.d("Status", "score not found!")}
        climberScore = savedInstanceState.getInt("score")
        climber.score = climberScore
        val scoreView: TextView = findViewById(R.id.scoreId)
        val scoreTextView: TextView = findViewById(R.id.scoreId2)
        scoreView.setTextColor(climber.scoreColorManagement())
        scoreTextView.setTextColor(climber.scoreColorManagement())
        scoreView.text = String.format(climberScore.toString())
    }
}