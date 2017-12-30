package dot

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import game.id.DefaultIdSupplier
import game.item._
import game.item.craft.bonus.{DamageBonus, ProtectionBonus}
import game.item.craft.nature.{Armor, Charm, Weapon}
import game.item.craft.{Crafts, DefaultCraftFactory, DefaultCrafts}
import game.item.element.{ELECTRIC, FIRE, NORMAL, WATER}
import game.item.status.{NEUTRAL, SLEEP, STUN}

import scalax.collection.Graph
import scalax.collection.edge.LDiEdge
import scalax.collection.io.dot._


/** Generates dot files for items categories from crafts
  * Created by nol on 19/11/17.
  */
class DotGeneration[TItemType <: ItemType](crafts: Crafts[TItemType], formatter: TItemType => String) {

  val dotRoot = DotRootGraph(directed = true, id = Some(Id("Crafts")))

  private def generateDot(p: (TItemType, TItemType, TItemType) => Boolean): String = {
    val graph = generateGraphFor(p, crafts)
    graph.toDot(dotRoot, edgeTransformer)
  }

  def saveDot(p: (TItemType, TItemType, TItemType) => Boolean, path: String): Unit = {
    val dot = generateDot(p)
    Files.write(Paths.get(path), dot.getBytes(StandardCharsets.UTF_8))
  }

  private def generateGraphFor(p: (TItemType, TItemType, TItemType) => Boolean, crafts: Crafts[TItemType]): Graph[String, LDiEdge] = {
    var g = Graph[String, LDiEdge]()
    for (recipe <- crafts.getRecipes) {
      recipe match {
        case ((i1, i2), result) =>
          if (p(i1, i2, result)) {
            val i1Name = formatter(i1)
            val i2Name = formatter(i2)
            val resultName = formatter(result)
            g = g + LDiEdge(i1Name, resultName)(i2Name)
          }
      }
    }
    g
  }

  private def edgeTransformer(innerEdge: Graph[String, LDiEdge]#EdgeT): Option[(DotGraph, DotEdgeStmt)] = {
    innerEdge.edge match {
      case LDiEdge(source, target, label) =>
        Some((dotRoot, DotEdgeStmt(NodeId(source.toString()), NodeId(target.toString()), List(DotAttr(Id("label"), Id(label.toString))))))
    }
  }
}

object DotGeneration extends App {
  /* Factories */
  private val itemTypeFactory = new DefaultItemTypeFactory(new DefaultIdSupplier)
  /* Game state */
  private val natureTypes = Seq(Weapon[ItemType](itemTypeFactory), Charm[ItemType](itemTypeFactory), Armor[ItemType](ArmorPart.HEAD, itemTypeFactory), Armor[ItemType](ArmorPart.BODY, itemTypeFactory), Armor[ItemType](ArmorPart.ARMS, itemTypeFactory), Armor[ItemType](ArmorPart.LEGS, itemTypeFactory))
  private val elementTypes = Seq(FIRE, WATER, ELECTRIC, NORMAL)
  private val statusTypes = Seq(STUN, SLEEP, NEUTRAL)
  private val bonusTypes = Seq(DamageBonus, ProtectionBonus)
  private val crafts = new DefaultCraftFactory[ItemType](bonusTypes, elementTypes, natureTypes, statusTypes, new DefaultDecorator(), itemTypeFactory).generateCraft(new DefaultCrafts[ItemType])
  val formatter = (i: ItemType) => i.getName
  val dotGeneration = new DotGeneration[ItemType](crafts, formatter)
  dotGeneration.saveDot((i1, i2, _) => i1.isWeapon || i2.isWeapon, "res/weapon.dot")
}



