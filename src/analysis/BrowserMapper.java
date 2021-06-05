package analysis;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import dim.BrowserDimension;
import dim.PVDimension;
import prepare.GlobalConstants;
import prepare.LogConstants;
import utils.LogParser;
/**
********************************
程序逻辑：从清洗完的数据中获得浏览器信息地址，统计每种类型的地址出现的次数
输出：
	key: date --- browserName
	value: 1
作者：林锦弘
时间：2020/6/24
********************************
 */
public class BrowserMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
		// 获得PV对象
        BrowserDimension browser = null;
        // 定义map的输出key和value
        Text outKey = new Text();
        LongWritable outputValue = new LongWritable();
        
        protected void map(LongWritable key, Text value, org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, LongWritable>.Context context) throws java.io.IOException, InterruptedException {
        		// 构造PV对象
        		browser = new BrowserDimension(value.toString());
        		outKey.set(browser.getDate() + GlobalConstants.SplitSymbol+browser.getBrowserName());
        		outputValue.set(1);
	        	context.write(outKey, outputValue);
        }
    }
	
