package com.sanin.tv.others
import android.annotation.SuppressLint
import android.content.Context
import com.sanin.tv.okHttpClient
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream
@GlideModule
class SaninTVGlideApp : AppGlideModule() {    
@SuppressLint("CheckResult")    
override fun applyOptions(context: Context, builder: GlideBuilder) {        
        s
val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MiB        builder.apply {            
        s
}

override fun registerComponents(context: Context, glide: Glide, registry: Registry) {        
        r
}
