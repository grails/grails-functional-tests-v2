package structurededitor

class Game {

  String name
  Goal goal

  static constraints = {
    name(blank: false, unique: true, bindable: true)
    goal(nullable: false, bindable: true)
  }

  public String toString() {
    return "${name} ${goal.own}"
  }

  static mapping = {
    goal type: GoalHibernateUserType, {
      column name: "own_goals"
      column name: "other_goals"
    }
  }
}
