import java.util.LinkedList
import scala.collection.mutable

class Graph (val n: Int, val links : List[(Int, Int)], val gates: Seq[Int]) {
    def path(from: Int, to: Int, closed: List[Int] = List()) : List[Int] = {
        if (from == to)
            List(to)
        else {
            val newClosed = from :: closed
            from :: connectedNodes(from)
                .filterNot(newClosed.contains(_))
                .map(path(_, to, newClosed))
                .filter(_.last == to)
                .reduceOption((s1, s2) => if (s1.size < s2.size) s1 else s2).getOrElse(List())
        }
    }

    def distancesToGates () : Map[Int, Int] = {
        val dists = distances(gates.apply(0), 0, mutable.Map())
        1 until gates.size map {node => distances(gates.apply(node), 0, dists)}
        dists.toMap
    }

    private def distances(from: Int, curDist: Int, dists: mutable.Map[Int, Int]) : mutable.Map[Int, Int] = {
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
        links.drop(links.indexOf(link))
    }

    override def toString(): String = links.map({case (i1, i2) => i1 + "-" + i2}).mkString(", ")
}
