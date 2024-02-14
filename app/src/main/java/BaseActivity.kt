import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

open class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        writeToFile("onCreate")
    }

    override fun onStart() {
        super.onStart()
        writeToFile("onStart")
    }

    override fun onResume() {
        super.onResume()
        writeToFile("onResume")
    }

    override fun onPause() {
        super.onPause()
        writeToFile("onPause")
    }

    override fun onStop() {
        super.onStop()
        writeToFile("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        writeToFile("onDestroy")
    }

    private fun writeToFile(data: String) {
        try {
            val logFile = File(getExternalFilesDir(null), "${javaClass.simpleName}.txt")
            val writer = BufferedWriter(FileWriter(logFile, true))
            writer.write("$data\n")
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}