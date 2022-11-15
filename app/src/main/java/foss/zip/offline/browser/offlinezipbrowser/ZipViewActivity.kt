package foss.zip.offline.browser.offlinezipbrowser

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.core.net.toUri
import java.io.File
import java.util.zip.ZipFile

class ZipViewActivity : WebActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val webView = findViewById<WebView>(R.id.webview)
        val file = File(intent.getStringExtra(ZipConstants.FILE_NAME) ?: return)
        val name = file.toUri().lastPathSegment
        title = name
        val zip = ZipFile(file)
        webView.webViewClient = ZipAssetLoader(zip)
        webviewSetup(webView)
        webView.loadUrl("https://${name}.androidplatform.net/index.html")
    }

    companion object {
        fun startZipActivity (context: Activity, path: String) {
            val intent = Intent(context, ZipViewActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            intent.putExtra(ZipConstants.FILE_NAME, path)
            context.startActivity(intent)
            context.finish()
        }
    }
}