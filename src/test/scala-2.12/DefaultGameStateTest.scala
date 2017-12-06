import game.gamestate.{DefaultGameStateFactory, GameState}
import org.scalatest.FlatSpec

/**
  * Created by nol on 15/11/17.
  */
class DefaultGameStateTest extends FlatSpec {

  private val gameState: GameState = new DefaultGameStateFactory().createGameState

  "GameState" should "have quests" in {
    assert(gameState.getQuests.nonEmpty)
  }

  "All quests" should "provide loot" in {
    assert(gameState.getQuests.forall(q => q.createLoot.nonEmpty))
  }

}
