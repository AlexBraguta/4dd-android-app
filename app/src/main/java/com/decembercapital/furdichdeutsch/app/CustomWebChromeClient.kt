package com.decembercapital.furdichdeutsch.app

import android.webkit.WebChromeClient
import android.webkit.WebView

class CustomWebChromeClient : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        // You can use this to show a progress bar
        // For example: progressBar.progress = newProgress
    }

    // This method is called when the page title is received
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        // You can update the activity title here if needed
        // (view?.context as? Activity)?.title = title
    }
}
