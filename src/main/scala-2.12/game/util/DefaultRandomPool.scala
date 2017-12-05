package game.util

import scala.collection.mutable

/** Random Pool backed up by a mutable buffer.
  * Created by nol on 29/11/17.
  */
class DefaultRandomPool[T](values: Seq[T]) extends RandomPool[T] {
  private var pool: mutable.Buffer[T] = mutable.Buffer(values: _*)

  override def next: Option[T] = {
    Procedural.pickRandomFromSeq(pool) match {
      case Some(v) =>
        pool -= v
        Some(v)
      case None => None
    }
  }
}
