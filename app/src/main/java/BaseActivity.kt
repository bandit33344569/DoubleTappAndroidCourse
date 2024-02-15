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
        Log.d(javaClass.simpleName, "OnCreate")
        writeToFile("onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(javaClass.simpleName, "onStart")
        writeToFile("onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.simpleName, "onResume")
        writeToFile("onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(javaClass.simpleName, "onPause")
        writeToFile("onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(javaClass.simpleName, "onStop")
        writeToFile("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(javaClass.simpleName, "onDestroy")
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