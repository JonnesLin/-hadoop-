package analysis;

import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TestOldCombinerGrouping.Combiner;
import org.apache.hadoop.mapreduce.Reducer;

import prepare.GlobalConstants;
import prepare.LogConstants;

/**
**************************
类功能：统计相同IP一天内访问的次数
输入：
	key：ip-date ：Text
	value：清洗后的数据：ip-date-source-browser ：Text
输出：
	key：date ：Text
	value：1 ：LongWritable
作者：林锦弘
时间：2020/6/24
**************************
 */
public class PVCombiner extends Reducer<Text, Text, Text, LongWritable> {
    protected void reduce(Text k2, java.lang.Iterable<Text> v2, org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, LongWritable>.Context context) throws java.io.IOException, InterruptedException {
    		// 记录同一ip访问的次数
    		String[] value = null;
    		String date = null;
        for (Text singleValue : v2) {
        		value = singleValue.toString().split(GlobalConstants.SplitSymbol);
        		date = value[LogConstants.DATE];
        		context.write(new Text(date), new LongWritable(1));
        }
        
        
    }
}