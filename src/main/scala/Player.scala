import math._
import scala.util._
import scala.io.StdIn._
import scala.collection.mutable.ListBuffer
import java.util.LinkedList

object Player extends App {

    private def init() = {
        // n: the total number of nodes in the level, including the gateways
        // l: the number of links
        // e: the number of exit gateways
        val Array(n, l, e) = (readLine split " ").map (_.toInt)
        
        val links = new ListBuffer[(Int, Int)]()
        for(i <- 0 until l) {
            val Array(n1, n2) = (readLine split " ").map (_.toInt)
            links += ((n1, n2))
        }
        val gates = 0 until e map {i => readLine.toInt}
        
        new Graph(n, links.toList, gates)
    }

    private def pickLink(si: Int, graph: Graph) : (Int, Int) = {
        // Find all paths to gates
        val paths = graph.gates.flatMap(graph.path(si, _))

        // Get shortest paths only
        val minSize = paths.map(_.size).min
        val minPaths = paths.filter(_.size == minSize)

        // Find the link that occurs the maximum amlount of times in such paths
        val links = minPaths.flatMap(asLinks)
        val link = links.groupBy(identity).mapValues(_.size).minBy(_._2)._1

        link
    }

    private def asLinks(path: List[Int]) : Seq[(Int, Int)] = {
        (0 until path.size-1)
            .map(i => (path.apply(i), path.apply(i + 1)))
    }
    
    val graph = init()
    Console.err.println(graph)
    
    while(!Thread.interrupted()) {
        // The index of the node on which the Skynet agent is positioned this turn
        val si = readLine.toInt
        Console.err.println(s"Agent at $si")

        // Decide which link to cut
        val link = pickLink(si, graph)
        
        graph.cut(link)
        println(link._1 + " " + link._2)
    }
}
