package game.dot

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import game.description.DefaultDescription
import game.item.ItemType
import game.item.craft.{CraftPrototype, Crafts}

import scalax.collection.Graph
import scalax.collection.edge.LDiEdge
import scalax.collection.io.dot._


/** Generates 3 dot files (weapons, armors, charms) from crafts
  * Created by nol on 19/11/17.
  */
object DotGeneration extends App {
  val defaultDescription = new DefaultDescription()
  val crafts = CraftPrototype.generateCraft
  val weapons = generateGraphFor((i1, i2, _) => i1.isWeapon || i2.isWeapon, crafts)
  val armors = generateGraphFor((i1, i2, _) => i1.isArmor || i2.isArmor, crafts)
  val charms = generateGraphFor((i1, i2, _) => i1.isCharm || i2.isCharm, crafts)
  val dotRoot = DotRootGraph(directed = true, id = Some(Id("Crafts")))

  val weaponsDot = weapons.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get("weapons.dot"), weaponsDot.getBytes(StandardCharsets.UTF_8))

  val armorsDot = armors.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get("armors.dot"), armorsDot.getBytes(StandardCharsets.UTF_8))

  val charmsDot = charms.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get("charms.dot"), charmsDot.getBytes(StandardCharsets.UTF_8))

  def generateGraphFor(p: (ItemType, ItemType, ItemType) => Boolean, crafts: Crafts): Graph[String, LDiEdge] = {
    var g = Graph[String, LDiEdge]()
    for (recipe <- crafts.getRecipes) {
      recipe match {
        case ((i1, i2), result) =>
          if (p(i1, i2, result)) {
            val i1Name = defaultDescription.descriptionItemType(i1)
            val i2Name = defaultDescription.descriptionItemType(i2)
            val resultName = defaultDescription.descriptionItemType(result)
            g = g + LDiEdge(i1Name, resultName)(i2Name)
          }
      }
    }
    g
  }

  def edgeTransformer(innerEdge: Graph[String, LDiEdge]#EdgeT): Option[(DotGraph, DotEdgeStmt)] = {
    innerEdge.edge match {
      case LDiEdge(source, target, label) =>
        Some((dotRoot, DotEdgeStmt(NodeId(source.toString()), NodeId(target.toString()), List(DotAttr(Id("label"), Id(label.toString))))))
    }
  }
}
