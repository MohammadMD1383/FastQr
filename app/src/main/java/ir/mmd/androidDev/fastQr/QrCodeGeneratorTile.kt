package ir.mmd.androidDev.fastQr

import android.content.Intent
import android.service.quicksettings.TileService

class QrCodeGeneratorTile : TileService() {
	override fun onClick() {
		startActivity(
			Intent(this, FloatingQrCodeActivity::class.java).apply {
				flags = Intent.FLAG_ACTIVITY_NEW_TASK
			}
		)
	}
}