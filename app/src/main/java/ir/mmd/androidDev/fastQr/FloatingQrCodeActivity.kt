package ir.mmd.androidDev.fastQr

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.mmd.androidDev.fastQr.ui.components.QrCode
import ir.mmd.androidDev.fastQr.ui.theme.FastQrTheme

class FloatingQrCodeActivity : ComponentActivity() {
	private var contents by mutableStateOf(null as String?)
	private var shouldGetClipboard = false
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		when (intent.action) {
			Intent.ACTION_SEND -> {
				contents = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
			}
			
			Intent.ACTION_PROCESS_TEXT -> {
				contents = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT) ?: ""
			}
			
			else -> {
				shouldGetClipboard = true
			}
		}
		
		setContent {
			FastQrTheme {
				MainComponent(contents)
			}
		}
	}
	
	override fun onWindowFocusChanged(hasFocus: Boolean) {
		if (shouldGetClipboard) {
			val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
			contents = clipboard.primaryClip?.getItemAt(0)?.text?.toString() ?: ""
		}
		
		super.onWindowFocusChanged(hasFocus)
	}
	
}

@Composable
private fun MainComponent(contents: String?) {
	if (contents != null) {
		QrCode(contents = contents, size = LocalConfiguration.current.screenWidthDp.dp)
	} else {
		Surface(
			shape = CircleShape,
			modifier = Modifier.wrapContentSize()
		) {
			CircularProgressIndicator(
				strokeCap = StrokeCap.Round,
				modifier = Modifier.padding(24.dp)
			)
		}
	}
}

@Preview
@Composable
private fun LightPreview() {
	FastQrTheme(darkTheme = false) {
		Surface(modifier = Modifier.fillMaxSize()) {
			MainComponent("https://github.com/MohammadMD1383/ScreenTimeoutQuickAction")
		}
	}
}

@Preview
@Composable
private fun DarkPreview() {
	FastQrTheme(darkTheme = true) {
		Surface(modifier = Modifier.fillMaxSize()) {
			MainComponent("https://github.com/MohammadMD1383/ScreenTimeoutQuickAction")
		}
	}
}
