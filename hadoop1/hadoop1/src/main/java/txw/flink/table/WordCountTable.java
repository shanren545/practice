/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package txw.flink.table;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * Simple example for demonstrating the use of the Table API for a Word Count in Java.
 *
 * <p>This example shows how to:
 *  - Convert DataSets to Tables
 *  - Apply group, aggregate, select, and filter operations
 */
public class WordCountTable {

	// *************************************************************************
	//     PROGRAM
	// *************************************************************************

	public static void main(String[] args) throws Exception {
//	    EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
//	    StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//	    StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
//	    ExecutionEnvironment fbEnv = ExecutionEnvironment.getExecutionEnvironment();
//	    BatchTableEnvironment fbTableEnv = BatchTableEnvironment.create(fbEnv);
//	    
//	    StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//	    EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
//	    StreamTableEnvironment bsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);
//	    
//	    EnvironmentSettings bbSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inBatchMode().build();
//	    TableEnvironment bbTableEnv = TableEnvironment.create(bbSettings);
	    

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);

		DataSet<WC> input = env.fromElements(
				new WC("Hello", 1),
				new WC("Ciao", 1),
				new WC("Hello", 1));

		Table table = tEnv.fromDataSet(input);

		Table filtered = table
				.groupBy("word")
				.select("word, frequency.sum as frequency")
				.filter("frequency = 2");

		DataSet<WC> result = tEnv.toDataSet(filtered, WC.class);

		result.print();
	}

	// *************************************************************************
	//     USER DATA TYPES
	// *************************************************************************

	/**
	 * Simple POJO containing a word and its respective count.
	 */
	public static class WC {
		public String word;
		public long frequency;

		// public constructor to make it a Flink POJO
		public WC() {}

		public WC(String word, long frequency) {
			this.word = word;
			this.frequency = frequency;
		}

		@Override
		public String toString() {
			return "WC " + word + " " + frequency;
		}
	}
}
