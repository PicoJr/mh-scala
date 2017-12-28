package game.item.craft.addOn

import game.config.{DefaultGameConfig, GameConfig}
import game.item.{AbstractDecorator, ItemType}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
abstract class DefaultAddOn[TItemType <: ItemType](addOnName: String, val decorator: AbstractDecorator[TItemType], val gameConfig: GameConfig = DefaultGameConfig.getGameConfig) extends AddOn[TItemType] {

  override val name: String = addOnName

  protected def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  protected def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
  }
}
