package game.item

import game.item.ArmorPart.ArmorPart
import game.item.ElementType.ElementType
import game.util.Procedural

/** Factory for procedural name generation
  * Created by nol on 17/11/17.
  */
object NameFactory {

  private def pickRandomWeaponNature: String = {
    Procedural.pickRandom("sword", "hammer", "axe").get
  }

  private def pickRandomCharmNature: String = {
    Procedural.pickRandom("talisman", "relic", "charm").get
  }

  private def pickRandomMaterialNature: String = {
    Procedural.pickRandom("orb", "spike", "scale", "plate").get
  }

  private def pickRandomProtectionAdjective: String = {
    Procedural.pickRandom("shielded", "reinforced", "solid", "hardened").get
  }

  private def pickRandomDamageAdjective: String = {
    Procedural.pickRandom("bladed", "sharp").get
  }

  private def pickRandomElementAdjective(elementType: ElementType): String = elementType match {
    case ElementType.FIRE => Procedural.pickRandom("burning").get
    case ElementType.WATER => Procedural.pickRandom("flowing").get
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

  def getRandomMaterialDescription(resultDescription: DescriptionBuilder): DescriptionBuilder = {
    val description = new DescriptionBuilder
    description.addNature(pickRandomMaterialNature)
    resultDescription.getElement match {
      case Some(element) => description.addElement(element)
      case None =>
    }
    if (resultDescription.getAdjectives.nonEmpty) description.addAdjective(Procedural.pickRandomFromSeq(resultDescription.getAdjectives).get)
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
