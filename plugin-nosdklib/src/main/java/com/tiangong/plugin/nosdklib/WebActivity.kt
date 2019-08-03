package com.tiangong.plugin.nosdklib

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.*
import kotlinx.android.synthetic.main.webview.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
        initToobar()
        val url = intent.getStringExtra("url")
        loadWebView(url)
    }


    /**
     * 加载页面
     *
     * @param url
     */
    fun loadWebView(url: String?) {
        val settings = web.settings
        settings.builtInZoomControls = false
        // settings.setUseWideViewPort(true);
        // settings.setLoadWithOverviewMode(true);
        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().startSync()
        CookieManager.getInstance().removeSessionCookie()
        web.clearCache(true)
        web.clearHistory()
        settings.setJavaScriptEnabled(true)
        if (url == null || url == "") {
            return
        }
        web.loadUrl(url)
//        // 设置Web视图
//        web.setWebChromeClient(WebViewDialog(getMyActivity(), webview, pb))
        web.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.e("result", url)
                view.loadUrl(url)
                return true
            }
        })

        web.setDownloadListener(MyWebViewDownLoadListener())

    }

    override fun onDestroy() {
        if (web != null) {
            web.setVisibility(View.GONE)
        }
        super.onDestroy()
    }

    private inner class MyWebViewDownLoadListener : DownloadListener {
        override fun onDownloadStart(
                url: String, userAgent: String,
                contentDisposition: String, mimetype: String, contentLength: Long
        ) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        super.onBackPressed()
    }

    private fun initToobar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}