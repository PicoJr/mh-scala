package dot

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import game.GameDefaults
import game.item._
import game.item.craft.addOn.{BonusAddOn, ElementAddOn, StatusAddOn}
import game.item.craft.{Crafts, DefaultCraftFactory, DefaultCrafts}
import game.util.DefaultLoopingRandomPool


/** Generates dot files for items categories from crafts
  * Created by nol on 19/11/17.
  */
class DotGeneration(crafts: Crafts) {


  def saveDot(p: (ItemType, ItemType, ItemType) => Boolean, path: String, formatter: Formatter): Unit = {
    val dot = generateDot(p, crafts, formatter)
    Files.write(Paths.get(path), dot.getBytes(StandardCharsets.UTF_8))
  }

  private def generateDot(p: (ItemType, ItemType, ItemType) => Boolean, crafts: Crafts, formatter: Formatter): String = {
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

class Formatter(icons: Seq[String]) {

  val iconPool = new DefaultLoopingRandomPool[String](icons)

  def format(i: ItemType): String = "\"" + i.getName + "\""

  def getImage(i: ItemType): String = {
    val image = new StringBuilder
    image.append("width=0.4 height=0.4 fixedsize=true image = ").append("\"icons/").append(iconPool.next).append("\"").append(", label = \"\"")
    image.toString()
  }

  def getLabel(material: ItemType): String = {
    "label = \"" + material.getName + "\""
  }
}

object DotGeneration extends App {
  /* crafts */
  private val craftFactory = new DefaultCraftFactory(GameDefaults.natureTypes, GameDefaults.decorator, GameDefaults.itemTypeFactory)
  /* level 1 */
  for (element <- GameDefaults.elementTypes) {
    craftFactory.withAddOn(1, ElementAddOn(element, GameDefaults.decorator))
  }
  /* level 2 */
  for (status <- GameDefaults.statusTypes) {
    craftFactory.withAddOn(2, StatusAddOn(status, GameDefaults.decorator))
  }
  /* level 3 */
  for (bonus <- GameDefaults.bonusTypes) {
    craftFactory.withAddOn(3, BonusAddOn(bonus, GameDefaults.decorator))
  }
  /* level 4 */
  for (bonus <- GameDefaults.bonusTypes) {
    craftFactory.withAddOn(4, BonusAddOn(bonus, GameDefaults.decorator))
  }
  val crafts = craftFactory.generateCraft(new DefaultCrafts)
  /* weapons */
  val iconFiles = new java.io.File("res/icons").listFiles.filter(_.getName.endsWith(".png"))
  val formatter = new Formatter(iconFiles.map(f => f.getName))
  val dotGeneration = new DotGeneration(crafts)
  dotGeneration.saveDot((i1, i2, _) => i1.isWeapon || i2.isWeapon, "res/weapons.dot", formatter)
}



