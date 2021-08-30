import java.util.LinkedList
import scala.collection.mutable

class Graph (val n: Int, val links : mutable.MutableList[(Int, Int)], val gates: Seq[Int]) {
    def distances (from: Int) : Map[Int, Int] = {
        distancesStep(from, 0,  mutable.Map())
    }

    private def distancesStep(from: Int, curDist: Int, dists: mutable.Map[Int, Int]) : Map[Int, Int] = {
        dists.update(from, curDist)

        links
            .filter(l => l._1 == from || l._2 == from)
            .map(l => if (l._1 == from) l._2 else l._1)
            .foreach(node => {
                Console.err.println(s"At $node with $curDist, previous ${dists.get(node)}")
                dists.get(node)
                    match {
                        case Some(d) =>
                            if (curDist < d) distancesStep(node, curDist+1, dists)
                        case None =>
                                distancesStep(node, curDist+1, dists)
                    }
            })
        
        dists.toMap
    }

    def cut(link: (Int, Int)) {
        links.drop(links.indexOf(link))
    }

    override def toString(): String = links.map({case (i1, i2) => i1 + "-" + i2}).mkString(", ")
}
