package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import dim.PVDimension;
import prepare.GlobalConstants;

/**
类的功能：统计当日访问次数为1的ip，除以所有ip数，得到跳出率
输入：	
输出：
 	key: ip --- date：Text
	value:value：Text
作者：林锦弘
日期：2020/06/22
 *
 */
public class JumpUpRateMapper extends Mapper<LongWritable, Text, Text, Text> {
	// 获得PV对象
    PVDimension pv = null;
    // 定义map的输出key和value
    Text outKey = new Text();
    
    protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
    		// 构造PV对象
    		pv = new PVDimension(value.toString());
    		outKey.set(pv.getIp() + GlobalConstants.SplitSymbol+pv.getDate());
        	context.write(outKey, value);
    }
}