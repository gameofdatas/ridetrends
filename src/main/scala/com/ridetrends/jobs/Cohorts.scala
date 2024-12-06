package com.ridetrends.jobs

import com.ridetrends.configurations.Config
import com.ridetrends.reading.SourceReader
import com.ridetrends.utils.Session
import com.ridetrends.writing.DestinationWriter
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Cohorts extends App {
  val config = Config()
  implicit val spark: SparkSession = Session.createSparkSession(config.Master, config.AppName)
  val reader = SourceReader(config)
  val writer = DestinationWriter(config)

  val inputDF = reader.readFromCSVToDF()

  val enrichedDF = inputDF.withColumn("week",
    weekofyear(col("ts")))

  val customersByWeek = enrichedDF.groupBy("week").
    agg(collect_set(col("number")).as("unique_customer"),
    min(to_date(col("ts"))).as("min_date"))



  val cohortCounts = customersByWeek.alias("week1")
    .join(customersByWeek.alias("week2"), col("week1.min_date") >= col("week2.min_date"))
    .select(col("week1.min_date").as("start_week"), col("week2.min_date").as("cohort_week"),
      size(array_intersect(col("week1.unique_customer"), col("week2.unique_customer"))).as("active_users"))
    .groupBy("cohort_week")
    .pivot("start_week")
    .sum("active_users")
    .orderBy("cohort_week")

  cohortCounts.show()

}
