package com.sanin.tv.widgets.upcoming
import android.content.Intent
import android.widget.RemoteViewsService
import com.sanin.tv.util.Logger
class UpcomingRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {        
        L
return UpcomingRemoteViewsFactory(applicationContext)    }}