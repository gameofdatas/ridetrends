package com.ridetrends.reading

import com.ridetrends.configurations.Config
import com.ridetrends.models.Customers
import org.apache.spark.sql.functions.{col, to_timestamp}
import org.apache.spark.sql.{Dataset, SparkSession}


class SourceReader(config: Config)(implicit spark: SparkSession) extends ReaderService[Customers] {
  override def readFromCSVToDF(): Dataset[Customers] = {
    import spark.implicits._
    spark
      .read
      .options(Map("inferSchema" -> "true", "delimiter" -> ",", "header" -> "true"))
      .csv(config.InputPath)
      .withColumn("ts", to_timestamp(col("ts"), "yyyy-MM-dd HH:mm:ss"))
      .as[Customers]
  }
}

object SourceReader {
  def apply(config: Config)(implicit spark: SparkSession) = new SourceReader(config)
}
