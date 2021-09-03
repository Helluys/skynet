import java.util.LinkedList
import scala.collection.mutable.Queue
import scala.collection.mutable.HashMap
import scala.collection.mutable

class Graph (val n: Int, var links : List[(Int, Int)], val gates: Seq[Int]) {
    def path(from: Int, to: Int) : Option[List[Int]] = {
        val ancestors = new HashMap[Int, Int]()
        val queue = new Queue[Int]()
        queue.enqueue(from)

        var path = Option.empty[List[Int]]
        while(path.isEmpty)
            path = pathStep(from, to, queue, ancestors)
        path
    }

    private def pathStep(from: Int, to: Int, queue: Queue[Int], ancestors: mutable.Map[Int, Int]) : Option[List[Int]] = {
        queue.dequeueFirst(i => true) match {
            case Some(n) => {
                if (n == to) Option.apply(walkBack(n, ancestors.toMap))
                else {
                    val neighbours = connected(n).filterNot(ancestors.contains(_)).filterNot(_ == from)
                    neighbours.foreach(queue.enqueue(_))
                    neighbours.foreach(ancestors.update(_, n))
                    Option.empty
                }
            }
            case None => Option.empty
        }
    }

    private def walkBack(node: Int, ancestors: Map[Int, Int]) : List[Int] = {
        var path = List(node)
        while (ancestors.contains(path.head)) 
            path = ancestors.apply(path.head) :: path
        path
    }

    def connected(node: Int) = links
            .filter(l => l._1 == node || l._2 == node)
            .map(l => if (l._1 == node) l._2 else l._1)
            .toList

    def cut(link: (Int, Int)) {
        links = links.filterNot(_ == link)
    }

    override def toString(): String = links.map({case (i1, i2) => i1 + "-" + i2}).mkString(", ")
}
