package game.util

import scala.collection.mutable

/** Default looping random pool backed by a mutable buffer.
  * Created by nol on 05/12/17.
  */
class DefaultLoopingRandomPool[T](values: Seq[T]) extends LoopingRandomPool[T] {
  private var pool: mutable.Buffer[T] = mutable.Buffer(values: _*)

  override def next: T = {
    Procedural.pickRandomFromSeq(pool) match {
      case Some(v) =>
        pool -= v
        v
      case None =>
        pool = mutable.Buffer(values.tail: _*)
        values.head
    }
  }
}
