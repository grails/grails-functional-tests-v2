package structurededitor

import org.hibernate.usertype.UserType

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Sample Hibernate UserType for Goal class
 *
 * @author Carsten Block
 * @version 1.0 - 05.04.12
 */
class GoalHibernateUserType implements UserType {
  private static final SQL_TYPES = [Types.INTEGER, Types.INTEGER] as int[]

  int[] sqlTypes() { SQL_TYPES }

  Class returnedClass() { return Goal }

  boolean equals(Object x, Object y)  { x.equals(y) }

  int hashCode(Object o) { o.hashCode() }

  public void nullSafeSet(PreparedStatement st, Object value, int index) {
    Goal goal = (Goal) value
    st.setInt(index, goal.own)
    st.setInt(index+1, goal.other)
  }

  public Object nullSafeGet(ResultSet rs, String[] names, Object owner) {
    return new Goal(own: rs.getInt(names[0]), other: rs.getInt(names[1]))
  }

  Object deepCopy(Object o) { o }

  boolean isMutable() { return false }

  Serializable disassemble(Object o) { return o }

  Object assemble(Serializable cached, Object owner) { cached }

  Object replace(Object original, Object target, Object owner) { original }
}
