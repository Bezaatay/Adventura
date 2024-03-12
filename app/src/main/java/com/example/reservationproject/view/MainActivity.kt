package com.example.reservationproject.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.reservationproject.R
import com.example.reservationproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        (this as AppCompatActivity).supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)

        val layoutFlight: LinearLayout = dialog.findViewById(R.id.layoutFlight)
        val layoutHotel: LinearLayout = dialog.findViewById(R.id.layoutHotel)
        val layoutTour: LinearLayout = dialog.findViewById(R.id.layoutTour)
        val cancelButton: ImageView = dialog.findViewById(R.id.cancelButton)

        layoutFlight.setOnClickListener {
            dialog.dismiss()
            sendArgument("flight")
        }


        layoutHotel.setOnClickListener {
            dialog.dismiss()
            sendArgument("hotel")
        }

        layoutTour.setOnClickListener {
            dialog.dismiss()
            sendArgument("tour")
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun sendArgument(arg: String) {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val bundle = Bundle()
        bundle.putString("param", arg)

        navController.navigate(R.id.navigation_home, bundle)
    }
}