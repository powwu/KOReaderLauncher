package sh.powwu.koreaderlauncher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import sh.powwu.koreaderlauncher.ui.theme.KOReaderLauncherTheme
import android.os.CountDownTimer
import android.widget.Toast
import android.net.Uri
import android.provider.Settings

private lateinit var timer: CountDownTimer


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KOReaderLauncherTheme {
                CreateButton(modifier = Modifier.fillMaxSize(), this)
            }
        }
        timerToRedirect(this)
    }

}

fun openHomeScreenSettings(context: ComponentActivity) {
    try {
        // For newer Android versions
        val intent = Intent(Settings.ACTION_HOME_SETTINGS)
        context.startActivity(intent)
    } catch (e: Exception) {
        try {
            // Fallback for some devices
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${context.packageName}")
            context.startActivity(intent)
        } catch (e: Exception) {
            // Last resort - open main settings
            context.startActivity(Intent(Settings.ACTION_SETTINGS))
            Toast.makeText(
                context,
                "Navigate to Apps > Default apps > Home app",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

fun launchApp(context: ComponentActivity) {
    try {
        val intent = Intent()
        intent.setClassName(
            "org.koreader.launcher.fdroid",
            "org.koreader.launcher.MainActivity"
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    android.os.Process.killProcess(android.os.Process.myPid())
}

fun timerToRedirect(context: ComponentActivity) {
    timer = object : CountDownTimer(5000, 5000) {
        override fun onTick(millisUntilFinished: Long) {} // Blank aka do nothing :)
        override fun onFinish() {
            launchApp(context)
        }
    }.start()
}

@Composable
fun CreateButton(modifier: Modifier = Modifier, context: ComponentActivity) {
    Button(
        onClick = { openHomeScreenSettings(context) },
        modifier = modifier,
        enabled = true,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Text(text = "Opening KOReader...")
    }
}