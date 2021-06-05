package analysis;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import mysql.MysqlDb_JumpUpRate;

/**
****************************
类的功能：根据清洗完的数据分析跳出率
逻辑：
  	根据单个IP的访问次数，判断该IP是否为偶然进入该网站
  	若访问次数为1，则为偶然进入该网站，跳出数加1，否则非跳出数加1
输入：IP 该IP的访问次数
输出：该网页当天的跳出率
	key：date：String
	value：跳出率：DoubleWritable
作者：林锦弘
日期：2020/06/22
****************************
 */


 public class JumpUpRateReducer extends Reducer<Text, Text, MysqlDb_JumpUpRate, NullWritable> {
     protected void reduce(Text k2, java.lang.Iterable<Text> v2s, org.apache.hadoop.mapreduce.Reducer<Text, Text, MysqlDb_JumpUpRate, NullWritable>.Context context) throws java.io.IOException, InterruptedException {
    	 	// 记录访问一次的IP数
    	 	int numOfOnce = 0;
    	 	// 记录总的访问次数
    	 	int totalIP = 0;
     	// 遍历所有ip的访问次数
         for (Text v2 : v2s) {
        	 	// 判断该IP是否只访问了一次
        	 	
        	 	if(v2.equals(new Text("1"))){
        	 		numOfOnce++;
        	 	}
        	 	totalIP ++;
         }
         //计算跳出率
         double JumpUpRate = (1.0 * numOfOnce)/totalIP;
         MysqlDb_JumpUpRate outputKey = new MysqlDb_JumpUpRate();
         outputKey.setDate(k2.toString());
         outputKey.setJumpOutRate(JumpUpRate);
         context.write(outputKey, NullWritable.get());
     }
     
 }
 
 
     