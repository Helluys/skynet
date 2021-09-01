import org.scalatest.flatspec.AnyFlatSpec

class GraphTest extends AnyFlatSpec {
  "Graph" should "find shortest path" in {
      assert(new Graph(5, List((0,1), (1,4), (0,2), (2,3), (3,4)), Seq(4)).path(0, 4) == List(0, 1, 4))
      assert(new Graph(5, List((0,1), (1,4), (0,2), (2,3), (3,4), (0, 4)), Seq(4)).path(0, 4) == List(0, 4))
      assert(new Graph(5, List((0,1), (1,3), (0,2), (2,3), (3,4)), Seq(4)).path(0, 4).length == 4)
  }
}
