package ir.mmd.androidDev.fastQr.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import ir.mmd.androidDev.fastQr.ui.theme.FastQrTheme
import ir.mmd.androidDev.fastQr.ui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun QrCode(contents: String, size: Dp, modifier: Modifier = Modifier) {
	val sizeInPx = LocalDensity.current.run { size.toPx().roundToInt() }
	val background = MaterialTheme.colorScheme.surface.toArgb()
	val foreground = MaterialTheme.colorScheme.onSurface.toArgb()
	
	val bitmap = remember {
		try {
			val matrix = QRCodeWriter().encode(contents, BarcodeFormat.QR_CODE, sizeInPx, sizeInPx)
			val pixels = IntArray(sizeInPx * sizeInPx)
			
			for (y in 0 until sizeInPx) {
				val offset = y * sizeInPx
				for (x in 0 until sizeInPx) {
					pixels[offset + x] = if (matrix[x, y]) foreground else background
				}
			}
			
			Bitmap.createBitmap(sizeInPx, sizeInPx, Bitmap.Config.ARGB_8888).apply {
				setPixels(pixels, 0, sizeInPx, 0, 0, sizeInPx, sizeInPx)
			}.asImageBitmap()
		} catch (e: Exception) {
			null
		}
	}
	
	val shape = remember { RoundedCornerShape(16.dp) }
	
	if (bitmap != null) {
		Image(
			bitmap = bitmap,
			contentDescription = contents,
			modifier = Modifier.clip(shape)
		)
	} else {
		Surface(
			shape = shape,
			modifier = modifier.wrapContentSize()
		) {
			Text(
				text = "Failed to render QrCode",
				style = Typography.titleLarge,
				modifier = modifier.padding(16.dp)
			)
		}
	}
}

@Preview
@Composable
private fun LightPreview() {
	FastQrTheme(darkTheme = false) {
		QrCode("https://github.com/MohammadMD1383/ScreenTimeoutQuickAction", 100.dp)
	}
}

@Preview
@Composable
private fun DarkPreview() {
	FastQrTheme(darkTheme = true) {
		QrCode("https://github.com/MohammadMD1383/ScreenTimeoutQuickAction", 100.dp)
	}
}
