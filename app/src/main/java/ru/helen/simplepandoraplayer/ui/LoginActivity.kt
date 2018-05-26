package ru.helen.simplepandoraplayer.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

import ru.helen.simplepandoraplayer.App

import ru.helen.simplepandoraplayer.R
import ru.helen.simplepandoraplayer.repository.Storage
import ru.helen.simplepandoraplayer.ui.station.StationActivity
import ru.helen.simplepandoraplayer.viewmodel.LoginModel
import ru.helen.simplepandoraplayer.viewmodel.ViewModelFactory
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: LoginModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        App.instance.appComponent.inject(this)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(LoginModel::class.java)

        viewModel.partnerUser.observe(this, Observer { responce -> viewModel.login(responce!!, name.text.toString(), password.text.toString()) })
        viewModel.user.observe(this, Observer { responce ->
            Storage.user = responce!!
            startActivity(Intent(this, StationActivity::class.java))
        })

        login.setOnClickListener {
            viewModel.partnerLogin()
        }
    }


    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }


}
