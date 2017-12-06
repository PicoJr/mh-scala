package dot

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import game.description.DefaultDescription
import game.item.craft.{Crafts, DefaultCraftFactory}
import game.item.{ArmorPart, ItemType}

import scalax.collection.Graph
import scalax.collection.edge.LDiEdge
import scalax.collection.io.dot._


/** Generates dot files for items categories from crafts
  * Created by nol on 19/11/17.
  */
object DotGeneration extends App {
  val defaultDescription = new DefaultDescription()
  val crafts = new DefaultCraftFactory().generateCraft
  val weapons = generateGraphFor((i1, i2, _) => i1.isWeapon || i2.isWeapon, crafts)
  val armorsHead = generateGraphFor((i1, i2, _) => i1.isArmorPartRequired(ArmorPart.HEAD) || i2.isArmorPartRequired(ArmorPart.HEAD), crafts)
  val armorsBody = generateGraphFor((i1, i2, _) => i1.isArmorPartRequired(ArmorPart.BODY) || i2.isArmorPartRequired(ArmorPart.BODY), crafts)
  val armorsArms = generateGraphFor((i1, i2, _) => i1.isArmorPartRequired(ArmorPart.ARMS) || i2.isArmorPartRequired(ArmorPart.ARMS), crafts)
  val armorsLegs = generateGraphFor((i1, i2, _) => i1.isArmorPartRequired(ArmorPart.LEGS) || i2.isArmorPartRequired(ArmorPart.LEGS), crafts)
  val charms = generateGraphFor((i1, i2, _) => i1.isCharm || i2.isCharm, crafts)
  val dotRoot = DotRootGraph(directed = true, id = Some(Id("Crafts")))

  val destination = "res/"

  val weaponsDot = weapons.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get(destination + "weapons.dot"), weaponsDot.getBytes(StandardCharsets.UTF_8))

  val armorsHeadDot = armorsHead.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get(destination + "armorsHead.dot"), armorsHeadDot.getBytes(StandardCharsets.UTF_8))

  val armorsBodyDot = armorsBody.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get(destination + "armorsBody.dot"), armorsBodyDot.getBytes(StandardCharsets.UTF_8))

  val armorsArmsDot = armorsArms.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get(destination + "armorsArms.dot"), armorsArmsDot.getBytes(StandardCharsets.UTF_8))

  val armorsLegsDot = armorsHead.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get(destination + "armorsLegs.dot"), armorsHeadDot.getBytes(StandardCharsets.UTF_8))

  val charmsDot = charms.toDot(dotRoot, edgeTransformer)
  Files.write(Paths.get(destination + "charms.dot"), charmsDot.getBytes(StandardCharsets.UTF_8))

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
