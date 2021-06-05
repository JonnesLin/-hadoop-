package analysis;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import prepare.GlobalConstants;
import prepare.LogConstants;


public class IPCombiner extends Reducer<Text, Text, Text, Text> {
    protected void reduce(Text k2, java.lang.Iterable<Text> v2s, org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text>.Context context) throws java.io.IOException, InterruptedException {
   	 
    		//当存在某天的某个ip时，只写入文件一次，也就是当天一个ip只记录一次
        for (Text v2 : v2s) {
        		String date = v2.toString().split(GlobalConstants.SplitSymbol)[LogConstants.DATE];
        		context.write(new Text(date), new Text("1"));
       	 	break;
        }
        
        
    }
    
}

