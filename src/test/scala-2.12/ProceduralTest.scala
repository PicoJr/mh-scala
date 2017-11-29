import game.util.Procedural
import org.scalatest.FlatSpec

/**
  * Created by nol on 29/11/17.
  */
class ProceduralTest extends FlatSpec {

  "PickRandomFromSeq" should "return empty when supplied an empty seq" in {
    assert(Procedural.pickRandomFromSeq(Seq.empty).isEmpty)
  }

  "PickRandomFromSeq" should "return Some(_) when supplied an non empty seq" in {
    assert(Procedural.pickRandomFromSeq(Seq(1, 2)).nonEmpty)
  }

  "TakeRandomFromSeq" should "return exactly n elements when supplied at least n" in {
    val n = 2
    assert(Procedural.takeRandomFromSeq(Seq(1, 2), n).size == n)
    assert(Procedural.takeRandomFromSeq(Seq(1, 2, 3), n).size == n)
  }

  "TakeRandomFromSeq" should "return all elements when supplied less than n" in {
    val n = 4
    assert(Procedural.takeRandomFromSeq(Seq(1, 2), n).size == 2)
    assert(Procedural.takeRandomFromSeq(Seq(1, 2, 3), n).size == 3)
  }

}
