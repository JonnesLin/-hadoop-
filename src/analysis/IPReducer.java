package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import mysql.MysqlDb_IP;
import prepare.GlobalConstants;
import prepare.LogConstants;
/**
**********************
类功能：实现IP信息的reducer
输出: 
	key: date---ip
	value: 清洗后的数据
输出：
	key：date --- ip
	value：不同的ip总数
作者：林锦弘
时间：2020/6/24
**********************
 */
public class IPReducer extends Reducer<Text, Text, MysqlDb_IP, NullWritable> {
    protected void reduce(Text k2, java.lang.Iterable<Text> v2s, org.apache.hadoop.mapreduce.Reducer<Text, Text, MysqlDb_IP, NullWritable>.Context context) throws java.io.IOException, InterruptedException {
   	 	//记录一天的不同ip数
    		Long totalIPNumber = new Long(0);
        for (Text v2 : v2s) {
        		totalIPNumber += 1;
        		
        }
        MysqlDb_IP outputKey = new MysqlDb_IP();
        outputKey.setDate(k2.toString().split(GlobalConstants.SplitSymbol)[GlobalConstants.IP_DATE]);
        outputKey.setIp(k2.toString().split(GlobalConstants.SplitSymbol)[GlobalConstants.IP_IP]);
        outputKey.setNumberOfIp(totalIPNumber);
        context.write(outputKey, NullWritable.get());
        
    }
    
}