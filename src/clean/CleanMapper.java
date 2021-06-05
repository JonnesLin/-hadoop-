package clean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import prepare.GlobalConstants;
import prepare.LogConstants;
import utils.LogParser;
/**
****************************
类的功能：数据清理Mapper
输入：单行原始数据字符串:String
输出：
	key:ip --- date :Text
	value:ip --- date --- source --- browser :Text
作者：林锦弘
日期：2020/06/22
****************************
*/
public class CleanMapper extends Mapper<LongWritable, Text, Text, Text> {
		// 获得日志转换对象
        LogParser logParser = new LogParser();
        // 定义map的输出key和value
        Text outputValue = new Text();
        Text outKey = new Text();
        
        protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
        		// 当日志有效即不含有缺失项时 写入reducer
	        	if(logParser.isValid(value.toString())){
	        		// 获得该条数据的目标数据
	            	final String[] parsed = logParser.parse(value.toString());
	            	// 设置value 和 key
	            outputValue.set(
	            		parsed[LogConstants.IP] 
	            		+ GlobalConstants.SplitSymbol 
	            		+ parsed[LogConstants.DATE]
	            		+ GlobalConstants.SplitSymbol
	            		+ parsed[LogConstants.SOURCE]
	            		+ GlobalConstants.SplitSymbol
	            		+ parsed[LogConstants.BROWSER]);
	            outKey.set(parsed[LogConstants.IP] + GlobalConstants.SplitSymbol + parsed[LogConstants.DATE]);
	            context.write(outKey, outputValue);
	            }
        }
    }
	
