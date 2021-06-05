package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import dim.PVDimension;
import prepare.GlobalConstants;
import prepare.LogConstants;
import utils.LogParser;
/**
 *  程序逻辑：从清洗完的数据中获得ip地址，统计每个ip地址出现的次数
 *  key: ip ---- Date
 * @author jones
 *
 */
public class PVMapper extends Mapper<LongWritable, Text, Text, Text> {
		// 获得PV对象
        PVDimension pv = null;
        // 定义map的输出key和value
        Text outKey = new Text();
        Text outputValue = new Text();
        
        protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
        		// 构造PV对象
        		pv = new PVDimension(value.toString());
        		outKey.set(pv.getDate());
        		outputValue.set(value.toString());
	        	context.write(outKey, outputValue);
        }
    }
	

