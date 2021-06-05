package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import mysql.MysqlDb_Source;
import prepare.GlobalConstants;
import prepare.LogConstants;

/**
************************
类功能：source的reducer
输出：key: date---source
 	 value:出现的次数
作者：林锦弘
时间：2020/6/24
************************
 */
public class SourceReducer extends Reducer<Text,  Text, MysqlDb_Source, NullWritable> {
    protected void reduce(Text k2, java.lang.Iterable<Text> v2s, org.apache.hadoop.mapreduce.Reducer<Text, Text, MysqlDb_Source, NullWritable>.Context context) throws java.io.IOException, InterruptedException {
   	 	//记录一天内的同一source的数量
    		Long totalIPNumber = new Long(0);
        for (Text v2 : v2s) {
        		totalIPNumber += 1;
        }
        MysqlDb_Source outputKey = new MysqlDb_Source();
        outputKey.setDate(k2.toString().split(GlobalConstants.SplitSymbol)[GlobalConstants.SOURCE_DATE]);
        outputKey.setSource(k2.toString().split(GlobalConstants.SplitSymbol)[GlobalConstants.SOURCE_SOURCE]);
        outputKey.setTime(totalIPNumber);
        context.write(outputKey, NullWritable.get());
        
    }
    
}