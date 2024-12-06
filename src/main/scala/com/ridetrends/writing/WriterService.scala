package com.ridetrends.writing

import org.apache.spark.sql.DataFrame

trait WriterService {
  def writeDFToCSV(outputDF: DataFrame): Unit
  def printDFConsole(outputDF: DataFrame): Unit
}
