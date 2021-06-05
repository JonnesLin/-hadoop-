package analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import prepare.GlobalConstants;
import prepare.LogConstants;
/**
****************************
类的功能：计算跳出率,combiner
输入：
输出：
 	key: 日期：Text
 	value: 当日访问的次数：Text
作者：林锦弘
日期：2020/06/22
 */

public class JumpUpRateCombiner extends Reducer<Text, Text, Text, Text> {
    protected void reduce(Text k2, java.lang.Iterable<Text> v2s, org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
   	 	// 记录总的访问次数
   	 	int totalIP = 0;
   	 	String date = "";
    	// 遍历所有ip的访问次数
        for (Text v2 : v2s) {
        		totalIP++;
        		date = v2.toString().split(GlobalConstants.SplitSymbol)[LogConstants.DATE];
        }
        
        if(totalIP == 1){
    			context.write(new Text(date), new Text("1"));
        }
        else{
        		context.write(new Text(date), new Text("2"));
        }
    }
    
}