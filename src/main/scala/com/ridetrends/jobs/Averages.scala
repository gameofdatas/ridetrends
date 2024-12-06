package com.ridetrends.jobs

import com.ridetrends.configurations.Config
import com.ridetrends.utils.Session
import com.ridetrends.{reading, writing}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object Averages extends App {
  val config = Config()
  implicit val spark: SparkSession = Session.createSparkSession(config.Master, config.AppName)
  val reader = reading.SourceReader(config)
  val writer = writing.DestinationWriter(config)

  val inputDF = reader.readFromCSVToDF()

  val enrichedDF = inputDF.withColumn("hours", hour(col("ts")))
    .withColumn("days", to_date(col("ts")))
    .withColumn("weeks", weekofyear(col("ts")))

  val hourWindow = Window.partitionBy("number", "days", "hours")
  val dailyWindow = Window.partitionBy("number", "days")
  val weeklyWindow = Window.partitionBy("number", "weeks")

  val resultDF = enrichedDF
    .withColumn("hourly_count", count("*").over(hourWindow))
    .withColumn("daily_count", count("*").over(dailyWindow))
    .withColumn("weekly_count", count("*").over(weeklyWindow))

  val finalDF = resultDF.groupBy("number")
    .agg(
      avg("hourly_count").as("average_hourly_count"),
      avg("daily_count").as("average_daily_count"),
      avg("weekly_count").as("average_weekly_count")
    )

  finalDF.show()
}
