package com.example.reservationproject.manager

import android.content.Context
import android.content.SharedPreferences

class AppPref(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Login", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()


    companion object {
        private const val mail_KEY = "mail"
        private const val name_KEY = "name"
        private const val surname_KEY = "surname"
        private const val password_KEY = "password"
        private const val isChecked_KEY = "isChecked"
        private const val token_KEY = "token"
    }

    fun setIsChecked(isChecked: Boolean) {
        editor.apply {
            putBoolean(isChecked_KEY, isChecked)
            apply()
        }
    }

    fun userData(username: String?, password: String, isChecked: Boolean) {
        editor.apply {
            putString(mail_KEY, username)
            putString(password_KEY, password)
            putBoolean(isChecked_KEY, isChecked)
            apply()
        }
    }

    fun saveToken(token: String) {
        editor.apply {
            putString(token_KEY, token)
            apply()
        }
    }
  fun saveMail(mail: String) {
        editor.apply {
            putString(mail_KEY, mail)
            apply()
        }
    }

    fun saveNameAndSurname(name: String, surname: String) {
        editor.apply {
            putString(name_KEY, name)
            putString(surname_KEY, surname)
            apply()
        }
    }

    fun getNameAndSurname(): String {
        val n = sharedPreferences.getString(name_KEY, null)
        val sn = sharedPreferences.getString(surname_KEY, null)
        return "$n $sn"
    }

    fun getName(): String? {
        return sharedPreferences.getString(name_KEY, null)

    }

    fun getSurname(): String? {
        return sharedPreferences.getString(surname_KEY, null)

    }

    fun clearToken() {
        editor.apply {
            editor.remove(token_KEY)
            apply()
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(token_KEY, null)
    }


    fun clearData() {
        editor.apply {
            editor.remove(mail_KEY)
            editor.remove(password_KEY)
            editor.remove(name_KEY)
            editor.remove(surname_KEY)
            editor.putBoolean(isChecked_KEY, false)
            apply()
        }
    }

    fun getIsChecked(): Boolean {
        return sharedPreferences.getBoolean(isChecked_KEY, false)
    }

    fun getMail(): String? {
        return sharedPreferences.getString(mail_KEY, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(password_KEY, null)
    }
}
