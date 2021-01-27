package me.kennyvaldivia.riway

import androidx.navigation.NavController

interface BaseView {
    fun suggestedAction()
    fun getActionButtonResourceId(): Int
}