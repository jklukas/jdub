package com.simple.jdub

import java.sql.ResultSet

trait RawQuery[A] extends SqlBase {
  private[jdub] val timer = metrics.timer("query")

  def sql: String

  def values: Seq[Any]

  def handle(results: ResultSet): A

  def apply(db: Database): A = db(this)
}
