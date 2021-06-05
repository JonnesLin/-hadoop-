package clean;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
****************************
类的功能：数据清理Reducer, 根据ip值和时间进行聚集，也就是将相同ip及相同时间的value写入
输入：
	key:ip --- date :Text
	value:ip --- date --- source --- browser :Text
输出：
	key:ip --- date --- source --- browser :Text
	value:NullWritable
作者：林锦弘
日期：2020/06/22
****************************
*/
public class CleanReducer extends Reducer<Text, Text, Text, NullWritable> {
        protected void reduce(Text k2, java.lang.Iterable<Text> v2s, org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, NullWritable>.Context context) throws java.io.IOException, InterruptedException {
        		
        		// 根据ip值和时间进行聚集，也就是将相同ip及相同时间的value写入
            for (Text v2 : v2s) {
            	    context.write(v2, NullWritable.get());
            }
            
        }
    }