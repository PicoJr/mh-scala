package game.item

import game.id.Identifiable

/**
  * Created by nol on 21/11/17.
  */
trait Item extends ItemType with Identifiable {

  type Id = Identifiable.Id

  def getItemTypeId: Id

}
