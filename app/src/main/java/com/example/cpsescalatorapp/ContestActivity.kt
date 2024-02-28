package com.example.cpsescalatorapp

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup


class ContestActivity : AppCompatActivity() {
    private var clickCount = 0
    private lateinit var databaseHelper: DatabaseHelper
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 123
    private val selectedOptions = StringBuilder()
    var selectedOptionsString = " "
    var lastName = "none"
    var name = "none"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest)

        databaseHelper = DatabaseHelper(this)

        val helper = DatabaseHelper(this)
//        val name = findViewById<EditText>(R.id.first_name)
//        val lastName = findViewById<EditText>(R.id.last_name)
//        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)

        val submitBtn = findViewById<Button>(R.id.button_submit)
        val imageViewGlobalLogo = findViewById<ImageView>(R.id.imageView_globalLogo)
        val dataTest = findViewById<TextView>(R.id.textView_test)
        val option1 = findViewById<CheckBox>(R.id.option_3_1)
        val option2 = findViewById<CheckBox>(R.id.option_3_2)
        val option3 = findViewById<CheckBox>(R.id.option_3_3)
        val option4 = findViewById<CheckBox>(R.id.option_3_4)
//        val option5 = findViewById<CheckBox>(R.id.option_3_5)
//        val option6 = findViewById<CheckBox>(R.id.option_3_6)
        val answer7 = findViewById<RadioGroup>(R.id.radio_group_7)

        answer7.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
                val selectedText = selectedRadioButton.text.toString()
                if (selectedText == "Yes"){
                    name = "Yes"
                }else{
                    name = "No"
                }
                showToast(selectedText)
            }
        }

        option1.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedOptions(option1.text.toString(), isChecked)
        }
        option2.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedOptions(option2.text.toString(), isChecked)
        }
        option3.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedOptions(option3.text.toString(), isChecked)
        }
        option4.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedOptions(option4.text.toString(), isChecked)
        }
        /*option5.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedOptions(option5.text.toString(), isChecked)
        }
        option6.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedOptions(option6.text.toString(), isChecked)
        }*/

        imageViewGlobalLogo.setOnClickListener {
            clickCount++
            if (clickCount == 5) {
                saveUsersDBtoTxtFile()
                clickCount = 0 // Reset click count after saving
            }
        }

        submitBtn.setOnClickListener {
            if (selectedOptionsString != " "){
                helper.addPerson(name, lastName, selectedOptionsString)
//            helper.addPerson(name.text.toString(), lastName.text.toString(), email.text.toString(), selectedOptionsString)
                Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()
            }
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
    private fun showToast(text: String) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun updateSelectedOptions(optionText: String, isChecked: Boolean) {
        if (isChecked) {
            selectedOptions.append("$optionText\n")
        } else {
            selectedOptions.replace(selectedOptions.indexOf("$optionText\n"), selectedOptions.indexOf("$optionText\n") + optionText.length + 1, "")
        }
        updateUI()
    }

    private fun updateUI() {
        selectedOptionsString = selectedOptions.toString()
        // For testing
//        val textView = findViewById<TextView>(R.id.textView_test)
//        textView.text = selectedOptionsString
    }

    private fun saveUsersDBtoTxtFile() {
        val cursor: Cursor? = databaseHelper.getAllPersons()
        if (cursor!!.count == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
        }

        val buffer = StringBuilder()
        while (cursor.moveToNext()) {
            buffer.append("ID: ").append(cursor.getInt(0))
                .append("\nFirstName: ").append(cursor.getString(1))
                .append("\nLastName: ").append(cursor.getString(2))
                .append("\nSelectedOptions: ").append(cursor.getString(3))
                .append("\n\n")
        }

        Toast.makeText(this, buffer.toString(), Toast.LENGTH_LONG).show()
        Log.v("MainActivity", "View button clicked")

        // Testing
        if (isWriteStoragePermissionGranted()) {
            // Permission granted, save data to text file
            val content = buffer.toString()
            val fileName = "CPS_attendees.txt"
            writeToTxtFile(this, fileName, content)

            Toast.makeText(this, "Data saved to file: $fileName", Toast.LENGTH_LONG).show()
            Log.v("MainActivity", "View button clicked")
        }
    }

    fun writeToTxtFile(context: Context, fileName: String, content: String) {
        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)

            FileOutputStream(file).use { outputStream ->
                outputStream.write(content.toByteArray())
            }
            Log.v("writeToTxtFile", "File saved successfully at: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e("writeToTxtFile", "Error saving file: ${e.message}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, you can proceed with writing to file
                } else {
                    // Permission denied, inform the user
                    Toast.makeText(
                        this,
                        "Permission denied. Cannot save file.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }
    private fun isWriteStoragePermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST
            )
            return false
        }
        return true
    }
}