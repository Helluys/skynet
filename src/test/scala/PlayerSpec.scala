import org.scalatest.flatspec.AnyFlatSpec
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import org.scalatest.BeforeAndAfter
import java.io.BufferedReader
import java.io.InputStreamReader
import org.scalatest.BeforeAndAfterAll

class PlayerSpec extends AnyFlatSpec with BeforeAndAfter with BeforeAndAfterAll {

  var in = new PipedOutputStream()
  var inPipe = new PipedInputStream(in)
  var out = new PipedInputStream()
  var outPipe = new PipedOutputStream(out)
  var reader = new BufferedReader(new InputStreamReader(out))
  var playerThread = new Thread (new PlayerRunnable(inPipe, outPipe))

  before {
    in = new PipedOutputStream()
    inPipe = new PipedInputStream(in)
    out = new PipedInputStream()
    outPipe = new PipedOutputStream(out)
    reader = new BufferedReader(new InputStreamReader(out))
    playerThread = new Thread (new PlayerRunnable(inPipe, outPipe))
    playerThread.start()
  }

  after {
    in.close()
    inPipe.close()
    out.close()
    outPipe.close()
    reader.close()
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
    putLine("1")
    assert(getLine() == "1 2")
  }
}