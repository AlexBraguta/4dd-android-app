package com.decembercapital.furdichdeutsch.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val websiteUrl = "https://4dd.ro"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        webView = findViewById(R.id.webView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        // Configure WebView settings
        setupWebView()

        // Setup swipe to refresh
        setupSwipeRefresh()

        // Load the website or handle notification intent
        handleIntent()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.apply {
            settings.apply {
                // Enable JavaScript
                javaScriptEnabled = true

                // Enable DOM storage
                domStorageEnabled = true

                // Enable database
                databaseEnabled = true

                // Enable zoom controls but hide them
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false

                // Load images automatically
                loadsImagesAutomatically = true

                // Enable mixed content (if needed)
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

                // Improve rendering
                loadWithOverviewMode = true
                useWideViewPort = true

                // Cache settings
                cacheMode = WebSettings.LOAD_DEFAULT
            }

            // Set custom WebView client
            webViewClient = CustomWebViewClient(
                onPageLoadError = { showError() },
                onPageFinished = { swipeRefreshLayout.isRefreshing = false }
            )

            // Set WebChrome client for better JavaScript support
            webChromeClient = CustomWebChromeClient()
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            webView.reload()
        }
    }

    private fun handleIntent() {
        // Check if opened from notification with a specific URL
        val notificationUrl = intent.getStringExtra("url")

        if (notificationUrl != null && notificationUrl.isNotEmpty()) {
            webView.loadUrl(notificationUrl)
        } else {
            webView.loadUrl(websiteUrl)
        }
    }

    private fun showError() {
        Toast.makeText(
            this,
            getString(R.string.error_page_load),
            Toast.LENGTH_LONG
        ).show()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up WebView
        webView.destroy()
    }
}
