package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import dim.BrowserDimension;
import dim.IPDimension;
import prepare.GlobalConstants;

/**
**********************
类功能：实现IP信息的map
输出: 
	key: date---ip
	value: 1
输出：
	key：date --- ip
	value：清洗后的数据
作者：林锦弘
时间：2020/6/24
**********************
 */
public class IPMapper extends Mapper<LongWritable, Text, Text, Text> {
	// 获得PV对象
    IPDimension ipDimension = null;
    // 定义map的输出key和value
    Text outKey = new Text();
    Text outputValue = new Text();
    
    protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
    		// 构造PV对象
    		ipDimension = new IPDimension(value.toString());
    		outKey.set(ipDimension.getDate() + GlobalConstants.SplitSymbol+ipDimension.getIp());
    		outputValue.set(value);
        	context.write(outKey, outputValue);
    }
}