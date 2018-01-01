package dot

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import game.id.DefaultIdSupplier
import game.item._
import game.item.craft.bonus.DamageBonus
import game.item.craft.nature.{Armor, Charm, Weapon}
import game.item.craft.{Crafts, DefaultCraftFactory, DefaultCrafts}
import game.item.element.{FIRE, WATER}
import game.item.status.NEUTRAL


/** Generates dot files for items categories from crafts
  * Created by nol on 19/11/17.
  */
class DotGeneration[TItemType <: ItemType](crafts: Crafts[TItemType], formatter: Formatter[TItemType]) {


  def saveDot(p: (TItemType, TItemType, TItemType) => Boolean, path: String): Unit = {
    val dot = generateDot(p, crafts)
    Files.write(Paths.get(path), dot.getBytes(StandardCharsets.UTF_8))
  }

  private def generateDot(p: (TItemType, TItemType, TItemType) => Boolean, crafts: Crafts[TItemType]): String = {
    val dot = new StringBuilder("digraph Crafts {\n")
    for (recipe <- crafts.getRecipes) {
      recipe match {
        case ((i1, i2), result) =>
          if (p(i1, i2, result)) {
            dot.append(formatter.format(i1))
            dot.append(" -> ")
            dot.append(formatter.format(result))
            dot.append(" [").append(formatter.getLabel(i2)).append(" ]\n")
            dot.append(formatter.format(i1)).append("[").append(formatter.getImage(i1)).append("]\n")
          }
      }
    }
    dot.append("}")
    dot.toString()
  }
}

private class Formatter[TItemType <: ItemType] {

  def format(i: TItemType): String = "\"" + i.getName + "\""

  def getImage(i: TItemType): String = {
    val image = new StringBuilder
    image.append("width=0.5 height=0.5 fixedsize=true image = ").append("\"icons/sword.png\"").append(", label = \"\"")
    image.toString()
  }

  def getLabel(material: TItemType): String = {
    val label = new StringBuilder
    var content = "\"" + material.getName + "\""
    if (material.getName.contains("burning")) {
      content = """<<table><tr><td fixedsize="true" width="16" height="16"><img src="icons/fire.png" /></td></tr></table>>"""
    }
    label.append("label = ").append(content)
    label.toString()
  }
}

object DotGeneration extends App {
  /* Factories */
  private val itemTypeFactory = new DefaultItemTypeFactory(new DefaultIdSupplier)
  /* Game state */
  private val natureTypes = Seq(Weapon[ItemType](itemTypeFactory), Charm[ItemType](itemTypeFactory), Armor[ItemType](ArmorPart.HEAD, itemTypeFactory), Armor[ItemType](ArmorPart.BODY, itemTypeFactory), Armor[ItemType](ArmorPart.ARMS, itemTypeFactory), Armor[ItemType](ArmorPart.LEGS, itemTypeFactory))
  // private val elementTypes = Seq(FIRE, WATER, ELECTRIC, NORMAL)
  private val elementTypes = Seq(FIRE, WATER)
  // private val statusTypes = Seq(STUN, SLEEP, NEUTRAL)
  private val statusTypes = Seq(NEUTRAL)
  // private val bonusTypes = Seq(DamageBonus, ProtectionBonus)
  private val bonusTypes = Seq(DamageBonus)
  private val crafts = new DefaultCraftFactory[ItemType](bonusTypes, elementTypes, natureTypes, statusTypes, new DefaultDecorator(), itemTypeFactory).generateCraft(new DefaultCrafts[ItemType])
  val dotGeneration = new DotGeneration[ItemType](crafts, new Formatter[ItemType])
  dotGeneration.saveDot((i1, i2, _) => i1.isWeapon || i2.isWeapon, "res/weapons.dot")
}



