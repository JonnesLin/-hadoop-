package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import dim.IPDimension;
import dim.SourceDimension;
import prepare.GlobalConstants;

/**
************************
类功能：source的map
输出：
 	key:date---source
 	value: 对应的整行的清洗后的数据
作者：林锦弘
时间：2020/6/24
************************
 */
public class SourceMapper extends Mapper<LongWritable, Text, Text, Text> {
	// 获得PV对象
	SourceDimension sourceDimension = null;
    // 定义map的输出key和value
    Text outKey = new Text();
    Text outputValue = new Text();
    
    protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
    		// 构造PV对象
    		sourceDimension = new SourceDimension(value.toString());
    		outKey.set(sourceDimension.getDate() + GlobalConstants.SplitSymbol+sourceDimension.getSource());
    		outputValue.set(value);
        	context.write(outKey, outputValue);
    }
}