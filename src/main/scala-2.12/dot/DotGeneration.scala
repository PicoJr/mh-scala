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
import game.util.DefaultLoopingRandomPool


/** Generates dot files for items categories from crafts
  * Created by nol on 19/11/17.
  */
class DotGeneration[TItemType <: ItemType](crafts: Crafts[TItemType]) {


  def saveDot(p: (TItemType, TItemType, TItemType) => Boolean, path: String, formatter: Formatter[TItemType]): Unit = {
    val dot = generateDot(p, crafts, formatter)
    Files.write(Paths.get(path), dot.getBytes(StandardCharsets.UTF_8))
  }

  private def generateDot(p: (TItemType, TItemType, TItemType) => Boolean, crafts: Crafts[TItemType], formatter: Formatter[TItemType]): String = {
    val dot = new StringBuilder("digraph Crafts {\n")
    for (recipe <- crafts.getRecipes) {
      recipe match {
        case ((i1, i2), result) =>
          if (p(i1, i2, result)) {
            dot.append(formatter.format(i1))
            dot.append(" -> ")
            dot.append(formatter.format(result))
            dot.append(" [").append(formatter.getLabel(i2)).append("]\n")
            dot.append(formatter.format(i1)).append("[").append(formatter.getImage(i1)).append("]\n")
            dot.append(formatter.format(result)).append("[").append(formatter.getImage(result)).append("]\n")
          }
      }
    }
    dot.append("}")
    dot.toString()
  }
}

class Formatter[TItemType <: ItemType](icons: Seq[String]) {

  val iconPool = new DefaultLoopingRandomPool[String](icons)

  def format(i: TItemType): String = "\"" + i.getName + "\""

  def getImage(i: TItemType): String = {
    val image = new StringBuilder
    image.append("width=0.4 height=0.4 fixedsize=true image = ").append("\"icons/").append(iconPool.next).append("\"").append(", label = \"\"")
    image.toString()
  }

  def getLabel(material: TItemType): String = {
    "label = \"" + material.getName + "\""
  }
}

object DotGeneration extends App {
  /* Factories */
  private val decorator = new DefaultDecorator()
  private val itemTypeFactory = new DefaultItemTypeFactory(new DefaultIdSupplier)
  /* Game state */
  private val natureTypes = Seq(Weapon[ItemType](decorator, itemTypeFactory), Charm[ItemType](decorator, itemTypeFactory), Armor[ItemType](ArmorPart.HEAD, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.BODY, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.ARMS, decorator, itemTypeFactory), Armor[ItemType](ArmorPart.LEGS, decorator, itemTypeFactory))
  private val elementTypes = Seq(FIRE, WATER, ELECTRIC, NORMAL)
  private val statusTypes = Seq(STUN, SLEEP, NEUTRAL)
  private val bonusTypes = Seq(DamageBonus, ProtectionBonus)
  private val crafts = new DefaultCraftFactory[ItemType](bonusTypes, elementTypes, natureTypes, statusTypes, decorator, itemTypeFactory).generateCraft(new DefaultCrafts[ItemType])
  /* weapons */
  val iconFiles = new java.io.File("res/icons").listFiles.filter(_.getName.endsWith(".png"))
  val formatter = new Formatter[ItemType](iconFiles.map(f => f.getName))
  val dotGeneration = new DotGeneration[ItemType](crafts)
  dotGeneration.saveDot((i1, i2, _) => i1.isWeapon || i2.isWeapon, "res/weapons.dot", formatter)
}



