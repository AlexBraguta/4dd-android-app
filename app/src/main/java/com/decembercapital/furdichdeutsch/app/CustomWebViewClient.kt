package com.decembercapital.furdichdeutsch.app

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(
    private val onPageLoadError: () -> Unit = {},
    private val onPageFinished: () -> Unit = {}
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // Load all URLs within the WebView (no external browser)
        // If you want to open external links in browser, add logic here
        request?.url?.let {
            view?.loadUrl(it.toString())
        }
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        // Page started loading - you can show progress indicator here if needed
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // Page finished loading
        onPageFinished()
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        // Only show error for main frame failures
        if (request?.isForMainFrame == true) {
            onPageLoadError()
        }
    }
}
