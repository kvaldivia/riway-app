package me.kennyvaldivia.riway.base

import android.app.Application
import android.content.Context
import me.kennyvaldivia.riway.alarm.UpcomingAlarmContract

interface MVPContract {

    interface View {
        fun updateView()
        fun getPresenter(): Presenter
    }

    abstract class Presenter {
        protected var view: UpcomingAlarmContract.View? = null
        protected var ctx: Context? = null
        protected var app: Application? = null

        open fun bind(view: UpcomingAlarmContract.View, context: Context, application: Application) {
            this.view = view
            this.ctx = context
            this.app = application
        }

        fun unbind() {
            this.view = null
            this.ctx = null
        }

        abstract fun updateView()

    }
}