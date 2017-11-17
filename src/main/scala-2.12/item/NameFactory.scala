package item

import item.ArmorPart.ArmorPart
import item.ElementType.ElementType

import scala.util.Random

/**
  * Created by nol on 17/11/17.
  */
object NameFactory {

  private def pickRandom[T](seq: Seq[T]): Option[T] = {
    seq.lift(Random.nextInt(seq.size))
  }

  private def pickRandomWeaponNature: String = {
    pickRandom(Seq("sword", "hammer", "axe")).get
  }

  private def pickRandomCharmNature: String = {
    pickRandom(Seq("talisman", "relic", "charm")).get
  }

  private def pickRandomProtectionAdjective: String = {
    pickRandom(Seq("shielded", "reinforced", "solid", "hardened")).get
  }

  private def pickRandomDamageAdjective: String = {
    pickRandom(Seq("bladed", "sharp")).get
  }

  private def pickRandomElementAdjective(elementType: ElementType): String = elementType match {
    case ElementType.FIRE => pickRandom(Seq("burning")).get
    case ElementType.WATER => pickRandom(Seq("flowing")).get
    case _ => ""
  }

  private def getArmorNature(armorPart: ArmorPart): String = armorPart match {
    case ArmorPart.HEAD => "helmet"
    case ArmorPart.BODY => "plastron"
    case ArmorPart.ARMS => "sleaves"
    case ArmorPart.LEGS => "greaves"
    case _ => ""
  }

  def getRandomWeaponDescription(weapon: ItemType): DescriptionBuilder = {
    val description = new DescriptionBuilder
    description.addNature(pickRandomWeaponNature)
    if (weapon.hasArmor) description.addAdjective(pickRandomProtectionAdjective)
    if (weapon.hasElementType) description.addAdjective(pickRandomElementAdjective(weapon.getElementType))
    description
  }

  def getRandomArmorDescription(armor: ItemType): DescriptionBuilder = {
    val description = new DescriptionBuilder
    armor.getSlotTypeRequirement match {
      case ARMOR_SLOT(armorPart) => description.addNature(getArmorNature(armorPart))
      case _ =>
    }
    if (armor.hasDamage) description.addAdjective(pickRandomDamageAdjective)
    if (armor.hasElementType) description.addAdjective(pickRandomElementAdjective(armor.getElementType))
    description
  }

  def getRandomCharmDescription(charm: ItemType): DescriptionBuilder = {
    val description = new DescriptionBuilder
    description.addNature(pickRandomCharmNature)
    if (charm.hasArmor) description.addAdjective(pickRandomProtectionAdjective)
    if (charm.hasDamage) description.addAdjective(pickRandomDamageAdjective)
    if (charm.hasElementType) description.addAdjective(pickRandomElementAdjective(charm.getElementType))
    description
  }

  class DescriptionBuilder {
    var nature: String = "undefined"
    var element: Option[String] = Option.empty
    var adjectives: Seq[String] = Seq.empty

    def getDescription: String = {
      val description = new StringBuilder()
      for (adjective <- adjectives) {
        description.append(adjective).append(" ")
      }
      element match {
        case Some(e) => description.append(e).append(" ")
        case None =>
      }
      description.append(nature)
      description.toString()
    }

    def getNature: String = nature

    def getElement: Option[String] = element

    def getAdjectives: Seq[String] = adjectives

    def addNature(nature: String): DescriptionBuilder = {
      this.nature = nature
      this
    }

    def addAdjective(adjective: String): DescriptionBuilder = {
      this.adjectives = adjectives :+ adjective
      this
    }

    def addElement(element: String): DescriptionBuilder = {
      this.element = Option(element)
      this
    }
  }

}
