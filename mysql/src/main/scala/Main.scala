package org.mysql

import java.sql.{DriverManager, Connection, Statement, ResultSet,SQLException}

object Main {
  def main(args: Array[String]) :Unit = {
    println("Hello World by Scala")
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      var con = 
        DriverManager.getConnection("jdbc:mysql://" + sys.env("MYSQL_PORT_3306_TCP_ADDR") +
          "/"+ sys.env("MYSQL_ENV_MYSQL_DATABASE") + "?" + 
          "user="+ sys.env("MYSQL_ENV_MYSQL_USER") + 
          "&password=" + sys.env("MYSQL_ENV_MYSQL_PASSWORD")
          );
      try {
        var stmt = con.createStatement()
        var rs = stmt.executeQuery("SELECT * FROM test")
        while (rs.next()){
          print(rs.getString(1) + " ")
          print(rs.getString(2) + " ")
          print(rs.getString(3))
        }
        stmt.close()
      } catch {
        case e:SQLException => println("Database error "+e)

        case e => {
          println("Some other exception type:")
          e.printStackTrace()
        }
        } finally {
          con.close()
        }
      } catch {
        case e:SQLException => println("Database error "+e)
        case e => {
          println("Some other exception type:")
          e.printStackTrace()
        }
      }
  }
}

