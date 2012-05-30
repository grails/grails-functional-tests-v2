package structurededitor

/**
 * Represents goal balance
 *
 * @author Carsten Block
 * @version 1.0 - 05.04.12
 */
class Goal implements Serializable {

  Integer own
  Integer other

  public String toString() {
   return "${own}:${other}"
  }
}
