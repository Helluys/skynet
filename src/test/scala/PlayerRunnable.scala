import java.io.InputStream
import java.io.OutputStream

class PlayerRunnable(val in: InputStream, val out: OutputStream) extends Runnable {
    def run(): Unit = {
      Console.withIn(in) {
        Console.withOut(out) {
            Player.main(Array())
        }
      }
    }
}