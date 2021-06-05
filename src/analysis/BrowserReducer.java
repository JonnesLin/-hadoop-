package analysis;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import mysql.MysqlDb_Browser;
import prepare.GlobalConstants;
/**
********************************
程序逻辑：从清洗完的数据中获得浏览器信息地址，统计每种类型的地址出现的次数
输出：
	key: date --- browserName
	value: 浏览器出现的次数
作者：林锦弘
时间：2020/6/24
********************************
 */
public class BrowserReducer extends Reducer<Text, LongWritable, MysqlDb_Browser, NullWritable> {
        protected void reduce(Text k2, java.lang.Iterable<LongWritable> v2, org.apache.hadoop.mapreduce.Reducer<Text, LongWritable, MysqlDb_Browser, NullWritable>.Context context) throws java.io.IOException, InterruptedException {
        		// 记录某种浏览器出现的次数
        		long totalTime = 0;
            for (LongWritable time : v2) {
            		totalTime += time.get();
            }
            MysqlDb_Browser outputKey = new MysqlDb_Browser();
            outputKey.setDate(k2.toString().split(GlobalConstants.SplitSymbol)[GlobalConstants.BROWSER_DATE]);
            outputKey.setBrowser(k2.toString().split(GlobalConstants.SplitSymbol)[GlobalConstants.BROWSER_NAME]);
            outputKey.setTime(totalTime);
            context.write(outputKey, NullWritable.get());
            
        }
    }



