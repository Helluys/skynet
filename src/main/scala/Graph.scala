import java.util.LinkedList
import scala.collection.mutable

class Graph (val n: Int, var links : List[(Int, Int)], val gates: Seq[Int]) {
    def path(from: Int, to: Int, closed: List[Int] = List()) : Option[List[Int]]
        = paths(from, to, closed).reduceOption((p1, p2) => if (p1.size < p2.size) p1 else p2)

    def paths(from: Int, to: Int, closed: List[Int] = List()) : List[List[Int]] = {
        if (from == to)
            List(List(to))
        else 
            connectedNodes(from)
                .filterNot(closed.contains(_))
                .flatMap(paths(_, to, from :: closed))
                .map(path => from :: path)
                .filter(_.lastOption.exists(_ == to))
    }

    def distancesToGates () : Map[Int, Int] = {
        val dists = distances(gates.apply(0), 0, mutable.Map())
        1 until gates.size map {node => distances(gates.apply(node), 0, dists)}
        dists.toMap
    }

    private def distances(from: Int, curDist: Int, dists: mutable.Map[Int, Int])
     : mutable.Map[Int, Int] = {
        dists.update(from, curDist)

        connectedNodes(from)
            .foreach(node => {
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

    def connectedNodes(node: Int) = links
            .filter(l => l._1 == node || l._2 == node)
            .map(l => if (l._1 == node) l._2 else l._1)
            .toList

    def cut(link: (Int, Int)) {
        links = links.filterNot(_ == link)
    }

    override def toString(): String = links.map({case (i1, i2) => i1 + "-" + i2}).mkString(", ")
}
