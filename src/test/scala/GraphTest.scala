import org.scalatest.flatspec.AnyFlatSpec

class GraphTest extends AnyFlatSpec {
    val links = List((0,1), (1,4), (0,2), (2,3), (3,4))

  " Graph" should "find shortest path" in {
        assert(new Graph(5, links, Seq(4))
                .path(0, 4).get
                    == List(0, 1, 4))
        assert(new Graph(5, (0, 4) :: links, Seq(4))
                .path(0, 4).get
                    == List(0, 4))
        assert(new Graph(5, links.updated(1, (1, 3)), Seq(4))
                .path(0, 4).get.size
                    == 4)
    }

    "Graph" should "find all paths" in {
        assert(new Graph(5, links, Seq(4))
                .paths(0, 4).size
                    == 2)
        assert(new Graph(5, (0, 4) :: links, Seq(4))
                .paths(0, 4).size
                    == 3)
        assert(new Graph(5, links.updated(1, (1, 3)), Seq(4))
                .paths(0, 4).size
                    == 2)
    }
}
