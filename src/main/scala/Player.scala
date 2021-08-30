import math._
import scala.util._
import scala.io.StdIn._
import scala.collection.mutable.ListBuffer
import java.util.LinkedList

object Player extends App {
    // n: the total number of nodes in the level, including the gateways
    // l: the number of links
    // e: the number of exit gateways
    val Array(n, l, e) = (readLine split " ").map (_.toInt)

    val links = new LinkedList[(Int, Int)]()
    for(i <- 0 until l) {
        val Array(n1, n2) = (readLine split " ").map (_.toInt)
        links.add((n1, n2))
    }
    val gates = 0 until e map {i => readLine.toInt}

    val graph = new Graph(n, links, gates)
    Console.err.println(s"Graph with $n $l $e")
    
    // game loop
    while(!Thread.interrupted()) {
        val si = readLine.toInt // The index of the node on which the Skynet agent is positioned this turn
        Console.err.println(s"agent at $si")
        
        // Write an action using println
        // To debug: Console.err.println("Debug messages...")

        // Example: 0 1 are the indices of the nodes you wish to sever the link between
        println("0 1")
    }
}