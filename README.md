# RideShare Customer Analysis

This project analyzes ride-sharing customer data to calculate various metrics for **customer engagement and retention**.
The project has two primary objectives:

1. **Calculate hourly, daily, weekly, and monthly averages** of customer ride counts.
2. **Compute week-on-week cohorts** for analyzing customer retention across weeks.

## Dataset

The dataset can be accessed here: [Dataset Link](src/main/resources/ct_rr.csv)

The data contains information on customer rides, including timestamps and customer identifiers.

## Objectives

### 1. Aggregated Averages Calculation

The first part of the project calculates **hourly, daily, weekly, and monthly averages** of ride counts for each
customer. These averages are stored and made accessible for querying purposes. This allows for easy access to ride
patterns and engagement metrics on different time scales.

#### Expected Output

- **Hourly Average**: Average number of rides per hour for each customer.
- **Daily Average**: Average number of rides per day for each customer.
- **Weekly Average**: Average number of rides per week for each customer.
- **Monthly Average**: Average number of rides per month for each customer.

### 2. Week-on-Week Cohort Analysis

The second part of the project computes **week-on-week cohorts**. This analysis helps in understanding customer
retention, showing how many customers from each starting week remain active in the following weeks.

#### Input

- **Starting Week**: The initial week for cohort analysis.
- **Number of Weeks**: The number of consecutive weeks to consider in the cohort analysis.

#### Sample Output

For an input starting with the week of `1/12/2019` and covering `3 weeks`:

```
           1/12/2019   8/12/2019   16/12/2019
1/12/2019     2         2            2
8/12/2019     null      3            3
16/12/2019    null      null         4
```

#### Explanation

- **2 customers** were active in the week of **1st December**. Of these:
- **3 new customers** were active in the week of **8th December**. Of these:
    - **2** returned from the week of **1st December**.
- **4 customers** were active in the week of **16th December**. Of these:
    - **2** returned from the week of **1st December**.
    - **3** returned from the week of **8th December**.

## Implementation

### Technology Stack

- **Apache Spark**: Used for large-scale data processing, supporting both Python and Scala for flexible development.
- **Scala/Python**: Core languages for the Spark programs to calculate averages and cohort analysis.

### Approach

1. **Data Preprocessing**:
    - Extract and label each timestamp with relevant time segments (hour, day, week, month) for aggregation.

2. **Calculating Averages**:
    - For each customer, compute the hourly, daily, weekly, and monthly ride averages using Spark SQL or DataFrame
      operations.

3. **Cohort Analysis**:
    - Group customers by each week and calculate week-on-week retention.
    - Use window functions or joins to determine the number of customers returning in subsequent weeks.
    - Present the results in a cohort matrix format.

