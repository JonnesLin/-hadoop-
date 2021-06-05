package analysis;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TestOldCombinerGrouping.Combiner;
import org.apache.hadoop.mapreduce.Reducer;

import mysql.MysqlDb_PV;
import prepare.GlobalConstants;
import prepare.LogConstants;

/**
**********************
类功能：计算PV值
输入：IP  该IP的访问次数
输出：当日网站的总访问次数
作者：林锦弘
日期：2020/6/24
**********************
 */
public class PVReducer extends Reducer<Text, Text, MysqlDb_PV, NullWritable> {
    protected void reduce(Text k2, java.lang.Iterable<Text> v2, org.apache.hadoop.mapreduce.Reducer<Text, Text, MysqlDb_PV, NullWritable>.Context context) throws java.io.IOException, InterruptedException {
    		// 遍历所有IP访问的次数
    		long totalTime = 0;
    		String date = null;
        for (Text singleDateAndIp : v2) {
        		totalTime += 1;
        		date = singleDateAndIp.toString().split(GlobalConstants.SplitSymbol)[LogConstants.DATE];
        }
        MysqlDb_PV outputKey = new MysqlDb_PV();
        outputKey.setDate(date);
        outputKey.setTime(totalTime);
        context.write(outputKey, NullWritable.get());
        
    }
}