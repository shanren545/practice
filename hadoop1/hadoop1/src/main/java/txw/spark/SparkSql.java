/**
 * 
 */
package txw.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.col;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.AnalysisException;

/**
 *
 * @author tanxianwen 2020年4月14日
 */
public class SparkSql {
    public static class Person implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("spark sql eg 1").config("txw", 1).getOrCreate();
        Dataset<Row> ds = spark.read().json("/data/bin/test.json");
        ds.show();
        ds.printSchema();
        ds.select("name", "age").show();
        ds.select(col("name"), col("age").plus(1)).show();
        ds.filter(col("age").gt(32)).show();
        ds.groupBy("age").count().show();

        ds.createOrReplaceTempView("user");
        ds.sqlContext().sql("SELECT * FROM people").show();
        spark.sql("SELECT * FROM people").show();


    }

    private static void runBasicDataFrameExample(SparkSession spark) throws AnalysisException {
        // $example on:create_df$
        Dataset<Row> df = spark.read().json("examples/src/main/resources/people.json");

        // Displays the content of the DataFrame to stdout
        df.show();
        // +----+-------+
        // | age| name|
        // +----+-------+
        // |null|Michael|
        // | 30| Andy|
        // | 19| Justin|
        // +----+-------+
        // $example off:create_df$

        // $example on:untyped_ops$
        // Print the schema in a tree format
        df.printSchema();
        // root
        // |-- age: long (nullable = true)
        // |-- name: string (nullable = true)

        // Select only the "name" column
        df.select("name").show();
        // +-------+
        // | name|
        // +-------+
        // |Michael|
        // | Andy|
        // | Justin|
        // +-------+

        // Select everybody, but increment the age by 1
        df.select(col("name"), col("age").plus(1)).show();
        // +-------+---------+
        // | name|(age + 1)|
        // +-------+---------+
        // |Michael| null|
        // | Andy| 31|
        // | Justin| 20|
        // +-------+---------+

        // Select people older than 21
        df.filter(col("age").gt(21)).show();
        // +---+----+
        // |age|name|
        // +---+----+
        // | 30|Andy|
        // +---+----+

        // Count people by age
        // | name|
        // +-------+
        // |Michael|
        // | Andy|
        // | Justin|
        // +-------+

        // Select everybody, but increment the age by 1
        df.select(col("name"), col("age").plus(1)).show();
        // +-------+---------+
        // | name|(age + 1)|
        // +-------+---------+
        // |Michael| null|
        // | Andy| 31|
        // | Justin| 20|
        // +-------+---------+

        // Select people older than 21
        df.filter(col("age").gt(21)).show();
        // +---+----+
        // |age|name|
        // +---+----+
        // | 30|Andy|
        // +---+----+

        // Count people by age
        df.groupBy("age").count().show();
        // +----+-----+
        // | age|count|
        // +----+-----+
        // | 19| 1|
        // |null| 1|
        // | 30| 1|
        // +----+-----+
        // $example off:untyped_ops$

        // $example on:run_sql$
        // Register the DataFrame as a SQL temporary view
        df.createOrReplaceTempView("people");

        Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
        sqlDF.show();
        // +----+-------+
        // | age| name|
        // +----+-------+
        // |null|Michael|
        // | 30| Andy|
        // | 19| Justin|
        // +----+-------+
        // $example off:run_sql$

        // $example on:global_temp_view$
        // Register the DataFrame as a global temporary view
        df.createGlobalTempView("people");

        // Global temporary view is tied to a system preserved database `global_temp`
        spark.sql("SELECT * FROM global_temp.people").show();
        // +----+-------+
        // | age| name|
        // +----+-------+
        // |null|Michael|
        // | 30| Andy|
        // | 19| Justin|
        // +----+-------+

        // Global temporary view is cross-session
        spark.newSession().sql("SELECT * FROM global_temp.people").show();
        // +----+-------+
        // | age| name|
        // +----+-------+
        // |null|Michael|
        // | 30| Andy|
        // | 19| Justin|
        // +----+-------+
        // $example off:global_temp_view$
    }

    private static void runDatasetCreationExample(SparkSession spark) {
        // $example on:create_ds$
        // Create an instance of a Bean class
        Person person = new Person();
        person.setName("Andy");
        person.setAge(32);


        // Encoders are created for Java beans
        Encoder<Person> personEncoder = Encoders.bean(Person.class);
        Dataset<Person> javaBeanDS = spark.createDataset(Collections.singletonList(person), personEncoder);
        javaBeanDS.show();
        // +---+----+
        // |age|name|
        // +---+----+
        // | 32|Andy|
        // +---+----+

        // Encoders for most common types are provided in class Encoders
        Encoder<Integer> integerEncoder = Encoders.INT();
        Dataset<Integer> primitiveDS = spark.createDataset(Arrays.asList(1, 2, 3), integerEncoder);
        Dataset<Integer> transformedDS = primitiveDS.map((MapFunction<Integer, Integer>) value -> value + 1, integerEncoder);
        transformedDS.collect(); // Returns [2, 3, 4]
        transformedDS.collectAsList();

        // DataFrames can be converted to a Dataset by providing a class. Mapping based on name
        String path = "examples/src/main/resources/people.json";
        Dataset<Person> peopleDS = spark.read().json(path).as(personEncoder);
        peopleDS.show();
        // +----+-------+
        // | age| name|
        // +----+-------+
        // |null|Michael|
        // | 30| Andy|
        // | 19| Justin|
        // +----+-------+
        // $example off:create_ds$
    }

    private static void runInferSchemaExample(SparkSession spark) {
        // $example on:schema_inferring$
        // Create an RDD of Person objects from a text file
        JavaRDD<Person> peopleRDD = spark.read().textFile("examples/src/main/resources/people.txt").javaRDD().map(line -> {
            String[] parts = line.split(",");
            Person person = new Person();
            person.setName(parts[0]);
            person.setAge(Integer.parseInt(parts[1].trim()));
            return person;
        });

        // Apply a schema to an RDD of JavaBeans to get a DataFrame
        Dataset<Row> peopleDF = spark.createDataFrame(peopleRDD, Person.class);
        // Register the DataFrame as a temporary view
        peopleDF.createOrReplaceTempView("people");

        // SQL statements can be run by using the sql methods provided by spark
        Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");

        // The columns of a row in the result can be accessed by field index
        Encoder<String> stringEncoder = Encoders.STRING();
        Dataset<String> teenagerNamesByIndexDF = teenagersDF.map((MapFunction<Row, String>) row -> "Name: " + row.getString(0), stringEncoder);
        teenagerNamesByIndexDF.show();
        // +------------+
        // | value|
        // +------------+
        // |Name: Justin|
        // +------------+

        // or by field name
        Dataset<String> teenagerNamesByFieldDF =
                teenagersDF.map((MapFunction<Row, String>) row -> "Name: " + row.<String>getAs("name"), stringEncoder);
        teenagerNamesByFieldDF.show();
        // +------------+
        // | value|
        // +------------+
        // |Name: Justin|
        // +------------+
        // $example off:schema_inferring$
    }

    private static void runProgrammaticSchemaExample(SparkSession spark) {
        // $example on:programmatic_schema$
        // Create an RDD
        JavaRDD<String> peopleRDD = spark.sparkContext().textFile("examples/src/main/resources/people.txt", 1).toJavaRDD();

        // The schema is encoded in a string
        String schemaString = "name age";

        // Generate the schema based on the string of schema
        List<StructField> fields = new ArrayList<>();
        for (String fieldName : schemaString.split(" ")) {
            StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
            fields.add(field);
        }
        StructType schema = DataTypes.createStructType(fields);

        // Convert records of the RDD (people) to Rows
        JavaRDD<Row> rowRDD = peopleRDD.map((Function<String, Row>) record -> {
            String[] attributes = record.split(",");
            return RowFactory.create(attributes[0], attributes[1].trim());
        });

        // Apply the schema to the RDD
        Dataset<Row> peopleDataFrame = spark.createDataFrame(rowRDD, schema);

        // Creates a temporary view using the DataFrame
        peopleDataFrame.createOrReplaceTempView("people");

        // SQL can be run over a temporary view created using DataFrames
        Dataset<Row> results = spark.sql("SELECT name FROM people");
        // The results of SQL queries are DataFrames and support all the normal RDD operations
        // The columns of a row in the result can be accessed by field index or by field name
        Dataset<String> namesDS = results.map((MapFunction<Row, String>) row -> "Name: " + row.getString(0), Encoders.STRING());
        namesDS.show();
        // +-------------+
        // | value|
        // +-------------+
        // |Name: Michael|
        // | Name: Andy|
        // | Name: Justin|
        // +-------------+
        // $example off:programmatic_schema$
    }
    // $example on:create_ds$


    public static class MyAverage extends UserDefinedAggregateFunction {

        private StructType inputSchema;
        private StructType bufferSchema;

        public MyAverage() {
            List<StructField> inputFields = new ArrayList<>();
            inputFields.add(DataTypes.createStructField("inputColumn", DataTypes.LongType, true));
            inputSchema = DataTypes.createStructType(inputFields);

            List<StructField> bufferFields = new ArrayList<>();
            bufferFields.add(DataTypes.createStructField("sum", DataTypes.LongType, true));
            bufferFields.add(DataTypes.createStructField("count", DataTypes.LongType, true));
            bufferSchema = DataTypes.createStructType(bufferFields);
        }

        // Data types of input arguments of this aggregate function
        public StructType inputSchema() {
            return inputSchema;
        }

        // Data types of values in the aggregation buffer
        public StructType bufferSchema() {
            return bufferSchema;
        }

        // The data type of the returned value
        public DataType dataType() {
            return DataTypes.DoubleType;
        }

        // Whether this function always returns the same output on the identical input
        public boolean deterministic() {
            return true;
        }

        // Initializes the given aggregation buffer. The buffer itself is a `Row` that in addition
        // to
        // standard methods like retrieving a value at an index (e.g., get(), getBoolean()),
        // provides
        // the opportunity to update its values. Note that arrays and maps inside the buffer are
        // still
        // immutable.
        public void initialize(MutableAggregationBuffer buffer) {
            buffer.update(0, 0L);
            buffer.update(1, 0L);
        }

        // Updates the given aggregation buffer `buffer` with new input data from `input`
        public void update(MutableAggregationBuffer buffer, Row input) {
            if (!input.isNullAt(0)) {
                long updatedSum = buffer.getLong(0) + input.getLong(0);
                long updatedCount = buffer.getLong(1) + 1;
                buffer.update(0, updatedSum);
                buffer.update(1, updatedCount);
            }
        }

        // Merges two aggregation buffers and stores the updated buffer values back to `buffer1`
        public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
            long mergedSum = buffer1.getLong(0) + buffer2.getLong(0);
            long mergedCount = buffer1.getLong(1) + buffer2.getLong(1);
            buffer1.update(0, mergedSum);
            buffer1.update(1, mergedCount);
        }

        // Calculates the final result
        public Double evaluate(Row buffer) {
            return ((double) buffer.getLong(0)) / buffer.getLong(1);
        }
    }

    public void udf(SparkSession spark) {
        spark.udf().register("avg", new MyAverage());
        Dataset<Row> df = spark.read().json("examples/src/main/resources/employees.json");
        df.createOrReplaceTempView("employees");
        df.show();
        // +-------+------+
        // | name|salary|
        // +-------+------+
        // |Michael| 3000|
        // | Andy| 4500|
        // | Justin| 3500|
        // | Berta| 4000|
        // +-------+------+

        Dataset<Row> result = spark.sql("SELECT avg(salary) as average_salary FROM employees");
        result.show();
        // +--------------+
        // |average_salary|
        // +--------------+
        // | 3750.0|
        // +--------------+
        
        
        Dataset<Row> usersDF = spark.read().load("examples/src/main/resources/users.parquet");
        usersDF.select("name", "favorite_color").write().save("namesAndFavColors.parquet");
    }


}


