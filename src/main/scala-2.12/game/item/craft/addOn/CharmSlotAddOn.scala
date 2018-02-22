package game.item.craft.addOn

import game.item.{AbstractDecorator, DefaultDecorator, ItemType}
import game.util.Procedural

/** Provides charm slot.
  * Created by nol on 21/12/17.
  */
case class CharmSlotAddOn(decorator: AbstractDecorator = DefaultDecorator.getInstance) extends DefaultAddOn("charmed") {
  override val name: String = "charmed"

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  override def decorate(level: Int, itemType: ItemType): ItemType = {
    decorator.decorateWithCharmSlot(itemType, getRandomSlot)
  }
}
