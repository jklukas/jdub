jdub
====

*A damn simple JDBC wrapper. Y'know. For databases.*


Requirements
------------

* Java SE 6
* Scala 2.8.1
* Metrics 2.0.0-BETA12-SNAPSHOT
* Logula 2.1.1
* Tomcat DBCP (not the app server)

How To Use
----------

**First**, specify Jdub as a dependency:

```scala
val codaRepo = "Coda Hale's Repository" at "http://repo.codahale.com/"
val jdub = "com.codahale" %% "jdub" % "0.0.1-SNAPSHOT"
```

(Don't forget to include your JDBC driver!)

**Second**, connect to a database:

```scala
val db = Database.connect("jdbc:postgresql://localhost/wait_what", "myaccount", "mypassword")
```

**Third**, run some queries:

```scala
case class GetUserEmail(userId: Long) extends Query[Option[String]] {
  val sql = trim("""
SELECT email
  FROM users
 WHERE id = ?
""")

  val values = userId :: Nil
  
  def reduce(results: Iterator[IndexedSeq[Cell]]) =
    results.toSeq.headOption.map { _.head.toString }
}

// this'll print the email address for user #4002
println(db(GetUserEmail(4002)))
```

**Fourth**, execute some statements:

```scala
case class UpdateUserEmail(userId: Long, oldEmail: String, newEmail: String) extends Statement {
  val sql = trim("""
UPDATE users
   SET email = ?
 WHERE userId = ? AND email = ?
""")

  val values = userId :: oldEmail :: newEmail :: Nil
}

// execute the statement
db.execute(UpdateUserEmail(4002, "old@example.com", "new@example.com"))

// or return the number of rows updated
db.update(UpdateUserEmail(4002, "old@example.com", "new@example.com"))
```


License
-------

Copyright (c) 2011 Coda Hale

Published under The MIT License, see LICENSE