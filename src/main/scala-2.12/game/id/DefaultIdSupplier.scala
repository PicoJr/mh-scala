package game.id

/**
  * Created by nol on 22/11/17.
  */
class DefaultIdSupplier extends IdSupplier {
  private var id: Long = 0

  def getNextUniqueId: Long = {
    id += 1
    id
  }
}
