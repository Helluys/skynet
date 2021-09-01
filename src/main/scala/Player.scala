import math._
import scala.util._
import scala.io.StdIn._
import scala.collection.mutable.ListBuffer
import java.util.LinkedList
import scala.collection.mutable.MutableList

object Player extends App {

    private def init() = {
        // n: the total number of nodes in the level, including the gateways
        // l: the number of links
        // e: the number of exit gateways
        val Array(n, l, e) = (readLine split " ").map (_.toInt)
        
        val links = new MutableList[(Int, Int)]()
        for(i <- 0 until l) {
            val Array(n1, n2) = (readLine split " ").map (_.toInt)
            links += ((n1, n2))
        }
        val gates = 0 until e map {i => readLine.toInt}
        
        new Graph(n, links.toList, gates)
    }

    private def solve(si: Int, graph: Graph) : (Int, Int) = {
        (0, 1)
    }
    
    val graph = init()
    Console.err.println(graph)
    
    while(!Thread.interrupted()) {
        // The index of the node on which the Skynet agent is positioned this turn
        val si = readLine.toInt
        Console.err.println(s"Agent at $si")

        val link = solve(si, graph)
        graph.cut(link)

        println(link._1 + " " + link._2)
    }
}