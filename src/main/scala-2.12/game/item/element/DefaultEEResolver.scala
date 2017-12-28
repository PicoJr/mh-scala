package game.item.element

import game.config.{DefaultGameConfig, GameConfig}
import game.item.element.Effectiveness.Effectiveness

/**
  * Created by nol on 22/11/17.
  */
class DefaultEEResolver(gameConfig: GameConfig = DefaultGameConfig.getGameConfig) extends EEResolver {

  override def effectiveness(elementType: ElementType, other: ElementType): Effectiveness = (elementType, other) match {
    case (NORMAL, _) | (_, NORMAL) => Effectiveness.NORMAL
    case (FIRE, ELECTRIC) | (ELECTRIC, FIRE) => Effectiveness.NORMAL
    case (WATER, WATER) | (FIRE, FIRE) | (ELECTRIC, ELECTRIC) => Effectiveness.INEFFECTIVE
    case (WATER, ELECTRIC) => Effectiveness.INEFFECTIVE
    case (FIRE, WATER) => Effectiveness.INEFFECTIVE
    case (WATER, FIRE) => Effectiveness.EFFECTIVE
    case (ELECTRIC, WATER) => Effectiveness.EFFECTIVE
    case _ => Effectiveness.NORMAL
  }

  override def multiplier(elementType: ElementType, other: ElementType): Double = {
    Effectiveness.multiplier(effectiveness(elementType, other), gameConfig)
  }

}
