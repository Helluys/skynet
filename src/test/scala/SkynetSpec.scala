import org.scalatest.flatspec.AnyFlatSpec
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream

class SkynetSpec extends AnyFlatSpec {
  "Main" should "cut link 1-2 in simple case" in {
    val init =
      """|3 2 1
         |0 1
         |1 2
         |2
      """.stripMargin
      val in = new PipedOutputStream()
      val out = new ByteArrayOutputStream()
      val playerThread = new Thread { Player.main(Array()) }
      Console.withIn(new PipedInputStream(in)) {
        Console.withOut(out) {
          in.write(init.getBytes())

          playerThread.start()
        }
      }
  }
}