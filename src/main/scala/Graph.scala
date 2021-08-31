import java.util.LinkedList
import scala.collection.mutable

class Graph (val n: Int, val links : mutable.MutableList[(Int, Int)], val gates: Seq[Int]) {
    def distancesToGates () : Map[Int, Int] = {
        val dists = distances(gates.apply(0), 0, mutable.Map())
        1 until gates.size map {node => distances(gates.apply(node), 0, dists)}
        dists.toMap
    }

    private def distances(from: Int, curDist: Int, dists: mutable.Map[Int, Int]) : mutable.Map[Int, Int] = {
        dists.update(from, curDist)

        links
            .filter(l => l._1 == from || l._2 == from)
            .map(l => if (l._1 == from) l._2 else l._1)
            .foreach(node => {
                Console.err.println(s"At $node with $curDist, previous ${dists.get(node)}")
                dists.get(node)
                    match {
                        case Some(d) =>
                            if (curDist < d) distances(node, curDist+1, dists)
                        case None =>
                                distances(node, curDist+1, dists)
                    }
            })
        
        dists
    }

    def cut(link: (Int, Int)) {
        links.drop(links.indexOf(link))
    }

    override def toString(): String = links.map({case (i1, i2) => i1 + "-" + i2}).mkString(", ")
}
