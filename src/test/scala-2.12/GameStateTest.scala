import game.GameState
import org.scalatest.FlatSpec

/**
  * Created by nol on 15/11/17.
  */
class GameStateTest extends FlatSpec {

  private val gameState: GameState = GameState.createNewGameState

  "GameState" should "have quests" in {
    assert(gameState.getQuests.nonEmpty)
  }

  "All quests" should "provide loot" in {
    assert(gameState.getQuests.forall(q => q.createLoot.nonEmpty))
  }

}
