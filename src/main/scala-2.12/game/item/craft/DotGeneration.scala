package game.item.craft

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import scalax.collection.Graph
import scalax.collection.edge.LDiEdge
import scalax.collection.io.dot._


/**
  * Created by nol on 19/11/17.
  */
object DotGeneration extends App {
  var g = Graph[String, LDiEdge]()
  val crafts = Crafts.generateCraftRecipes
  for (recipe <- crafts.getRecipes) {
    recipe match {
      case ((i1, i2), result) =>
        g = g + LDiEdge(i1.getName + "[" + i1.getLevel + "]", result.getName + "[" + result.getLevel + "]")(result.getLevel)
        g = g + LDiEdge(i2.getName + "[" + i2.getLevel + "]", result.getName + "[" + result.getLevel + "]")(result.getLevel)
    }
  }
  val dotRoot = DotRootGraph(directed = true, id = Some(Id("Crafts")))

  def edgeTransformer(innerEdge: Graph[String, LDiEdge]#EdgeT): Option[(DotGraph, DotEdgeStmt)] = {
    innerEdge.edge match {
      case LDiEdge(source, target, label) =>
        Some((dotRoot, DotEdgeStmt(NodeId(source.toString()), NodeId(target.toString()), Nil)))
    }
  }

  val dot = g.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get("crafts.dot"), dot.getBytes(StandardCharsets.UTF_8))
}
