import math._
import scala.util._
import scala.io.StdIn._
import scala.collection.mutable.ListBuffer

class Node (val id: Integer, val links : List[Integer]) {
    def isLinked(other: Integer) =
        links.contains(other)
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Player extends App {
    while (true) {
        val line = readLine()
        Console.err.println(line)
    }

    // n: the total number of nodes in the level, including the gateways
    // l: the number of links
    // e: the number of exit gateways
    val Array(n, l, e) = (readLine split " ").map (_.toInt)
  
    val linksBuf = new ListBuffer[(Integer, Integer)]()
    for(i <- 0 until l) {
        // n1: N1 and N2 defines a link between these nodes
        val Array(n1, n2) = (readLine split " ").map (_.toInt)
        linksBuf.append((n1, n2))
    }

    val exitsBuf = 0 to e map {i => readLine.toInt}

    val nodes = 0 to n map {
        i => new Node(i, linksBuf
                            .filter { case (n1, n2) => n1 == i || n2 == i }
                            .map{ case (n1, n2) => if (n1 == i) n2 else n1 }
                            .toList
        )
    }

    // game loop
    while(true) {
        val si = readLine.toInt // The index of the node on which the Skynet agent is positioned this turn
        
        // Write an action using println
        // To debug: Console.err.println("Debug messages...")

        // Example: 0 1 are the indices of the nodes you wish to sever the link between
        println("0 1")
    }
}