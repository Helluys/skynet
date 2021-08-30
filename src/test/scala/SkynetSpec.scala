import org.scalatest.flatspec.AnyFlatSpec
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import org.scalatest.BeforeAndAfter
import java.io.BufferedReader
import java.io.InputStreamReader

class SkynetSpec extends AnyFlatSpec with BeforeAndAfter {

  val in = new PipedOutputStream()
  val inPipe = new PipedInputStream(in)
  val out = new PipedInputStream()
  val outPipe = new PipedOutputStream(out)
  val reader = new BufferedReader(new InputStreamReader(out))
  val playerThread = new Thread (new PlayerRunnable(inPipe, outPipe))

  before {
      playerThread.start()
  }

  after {
    in.close()
    inPipe.close()
    out.close()
    outPipe.close()
    reader.close()
    playerThread.interrupt()
  }

  def putLine(line: String) = {
    in.write(line.getBytes())
    in.write('\n')
    in.flush()
  }

  def putLines(lines: Array[String]) =
    lines map putLine

  def getLine() =
    reader.readLine()

  "Main" should "cut link 1-2 in simple case" in {
    putLines(Array("3 2 1", "0 1", "1 2", "2"))
    Thread.sleep(1000)
    putLine("1")
    assert(getLine() == "1 2")
  }
}