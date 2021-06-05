package chain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.interfaces.ECKey;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringInterner;
import org.apache.hadoop.util.Tool;

import analysis.BrowserMapper;
import analysis.BrowserReducer;
import analysis.IPCombiner;
import analysis.IPMapper;
import analysis.IPReducer;
import analysis.JumpUpRateCombiner;
import analysis.JumpUpRateMapper;
import analysis.JumpUpRateReducer;
import analysis.PVCombiner;
import analysis.PVMapper;
import analysis.PVReducer;
import analysis.SourceMapper;
import analysis.SourceReducer;
import analysis.PVCombiner;
import clean.CleanMapper;
import clean.CleanReducer;
import mysql.MysqlDb_Browser;
import mysql.MysqlDb_IP;
import mysql.MysqlDb_JumpUpRate;
import mysql.MysqlDb_PV;
import mysql.MysqlDb_Source;
import prepare.GlobalConstants;
 
 
public class ChainMapperChainReducer extends Configured{
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		
        //假如是本地测试，需要设置fs.defaultFS
        //conf.set("fs.defaultFS","file:///");
        
		// 清理已存在的输出文件
		FileSystem fs = FileSystem.get(new URI(args[GlobalConstants.DIR_OF_INPUT_DATA]), conf);
		
		Path outPath = new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		outPath = new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_PV);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		outPath = new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_IP);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		outPath = new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_BROWSER);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		outPath = new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_JUMPUPRATE);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		outPath = new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_SOURCE);
		if (fs.exists(outPath)) {
			fs.delete(outPath, true);
		}
		
		
		
		// 设定任务1:清洗数据
		 Job job1 = Job.getInstance(conf, "Clean");
	     job1.setJarByClass(ChainMapperChainReducer.class);
	     job1.setMapperClass(CleanMapper.class);
	     job1.setMapOutputKeyClass(Text.class);
	     job1.setMapOutputValueClass(Text.class);
	     job1.setReducerClass(CleanReducer.class);
	     job1.setOutputKeyClass(Text.class);
	     job1.setOutputValueClass(NullWritable.class);
	     FileInputFormat.addInputPath(job1, new Path(args[GlobalConstants.DIR_OF_INPUT_DATA]));
	     FileOutputFormat.setOutputPath(job1, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.TEMPORARY_DIR_OF_CLEAN_DATA));
	     
	     
	     // 当任务1完成时
	     if (job1.waitForCompletion(true) ) {
	    	 	 // 设定任务2:计算PV值
		     Job job2 = Job.getInstance(conf, "PV");
		     job2.setJarByClass(ChainMapperChainReducer.class);
		     job2.setMapperClass(PVMapper.class);
		     job2.setMapOutputKeyClass(Text.class);
		     job2.setMapOutputValueClass(Text.class);
		     job2.setReducerClass(PVReducer.class);
		     job2.setOutputKeyClass(MysqlDb_PV.class);
		     job2.setOutputValueClass(NullWritable.class);
		     FileInputFormat.addInputPath(job2, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.TEMPORARY_DIR_OF_CLEAN_DATA));
		     FileOutputFormat.setOutputPath(job2, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_PV));
		     
		     // 写入mysql
		     DBConfiguration.configureDB(
		    		 job2.getConfiguration(),GlobalConstants.DRIVECLASS ,
		    		 GlobalConstants.MYSQL_URL ,GlobalConstants.MYSQL_USERNAME,
		    		 GlobalConstants.MYSQL_PASSWORD);

		     DBOutputFormat.setOutput(job2, GlobalConstants.PV_TABLE_NAME,GlobalConstants.PV_TABLE_COLUMNS_NAME);


		     
		     // 设定任务3:计算跳出率
		     Job job3 = Job.getInstance(conf, "JumpUpRate");
		     job3.setJarByClass(ChainMapperChainReducer.class);
		     job3.setMapperClass(JumpUpRateMapper.class);
		     job3.setMapOutputKeyClass(Text.class);
		     job3.setMapOutputValueClass(Text.class);
		     job3.setCombinerClass(JumpUpRateCombiner.class);
		     job3.setReducerClass(JumpUpRateReducer.class);
		     job3.setOutputKeyClass(MysqlDb_JumpUpRate.class);
		     job3.setOutputValueClass(NullWritable.class);
		     FileInputFormat.addInputPath(job3, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.TEMPORARY_DIR_OF_CLEAN_DATA));
		     FileOutputFormat.setOutputPath(job3, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.DIR_OF_JUMPUPRATE));
		     
		     // 写入mysql
		     DBConfiguration.configureDB(job3.getConfiguration(),GlobalConstants.DRIVECLASS ,GlobalConstants.MYSQL_URL ,GlobalConstants.MYSQL_USERNAME, GlobalConstants.MYSQL_PASSWORD);

		     DBOutputFormat.setOutput(job3, GlobalConstants.JUMP_UP_RATE_TABLE_NAME,GlobalConstants.JUMP_UP_RATE_TABLE_COLUMNS_NAME);

		     // 设定任务4:统计浏览器信息
		     Job job4 = Job.getInstance(conf, "Browser");
		     job4.setJarByClass(ChainMapperChainReducer.class);
		     job4.setMapperClass(BrowserMapper.class);
		     job4.setMapOutputKeyClass(Text.class);
		     job4.setMapOutputValueClass(LongWritable.class);
		     job4.setReducerClass(BrowserReducer.class);
		     job4.setOutputKeyClass(MysqlDb_Browser.class);
		     job4.setOutputValueClass(NullWritable.class);
		     FileInputFormat.addInputPath(job4, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.TEMPORARY_DIR_OF_CLEAN_DATA));
		     FileOutputFormat.setOutputPath(job4, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA] + GlobalConstants.DIR_OF_BROWSER));
		     
		     // 写入mysql
		     DBConfiguration.configureDB(job4.getConfiguration(),GlobalConstants.DRIVECLASS ,GlobalConstants.MYSQL_URL ,GlobalConstants.MYSQL_USERNAME, GlobalConstants.MYSQL_PASSWORD);

		     DBOutputFormat.setOutput(job4, GlobalConstants.BROWSER_TABLE_NAME,GlobalConstants.BROWSER_TABLE_COLUMNS_NAME);

		     
		     
		     // 设定任务5:统计IP信息
		     Job job5 = Job.getInstance(conf, "IP");
		     job5.setJarByClass(ChainMapperChainReducer.class);
		     job5.setMapperClass(IPMapper.class);
		     job5.setMapOutputKeyClass(Text.class);
		     job5.setMapOutputValueClass(Text.class);
		     job5.setCombinerClass(IPCombiner.class);
		     job5.setReducerClass(IPReducer.class);
		     job5.setOutputKeyClass(MysqlDb_IP.class);
		     job5.setOutputValueClass(NullWritable.class);
		     FileInputFormat.addInputPath(job5, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA]+GlobalConstants.TEMPORARY_DIR_OF_CLEAN_DATA));
		     FileOutputFormat.setOutputPath(job5, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA] + GlobalConstants.DIR_OF_IP));
		     
		     // 写入mysql
		     DBConfiguration.configureDB(job5.getConfiguration(),GlobalConstants.DRIVECLASS ,GlobalConstants.MYSQL_URL ,GlobalConstants.MYSQL_USERNAME, GlobalConstants.MYSQL_PASSWORD);

		     DBOutputFormat.setOutput(job5, GlobalConstants.IP_TABLE_NAME,GlobalConstants.IP_TABLE_COLUMNS_NAME);
		     
		     // 设定任务6:统计Source信息
		     Job job6 = Job.getInstance(conf, "Source");
		     job6.setJarByClass(ChainMapperChainReducer.class);
		     job6.setMapperClass(SourceMapper.class);
		     job6.setMapOutputKeyClass(Text.class);
		     job6.setMapOutputValueClass(Text.class);
		     job6.setReducerClass(SourceReducer.class);
		     job6.setOutputKeyClass(MysqlDb_Source.class);
		     job6.setOutputValueClass(NullWritable.class);
		     FileInputFormat.addInputPath(job6, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA] + GlobalConstants.TEMPORARY_DIR_OF_CLEAN_DATA));
		     FileOutputFormat.setOutputPath(job6, new Path(args[GlobalConstants.DIR_OF_OUTPUT_DATA] + GlobalConstants.DIR_OF_SOURCE));
		     
		     // 写入mysql
		     DBConfiguration.configureDB(job6.getConfiguration(),GlobalConstants.DRIVECLASS ,GlobalConstants.MYSQL_URL ,GlobalConstants.MYSQL_USERNAME, GlobalConstants.MYSQL_PASSWORD);

		     DBOutputFormat.setOutput(job6, GlobalConstants.SOURCE_TABLE_NAME,GlobalConstants.SOURCE_TABLE_COLUMNS_NAME);
		     
		     // 当所有任务完成后，程序结束
		     if(job2.waitForCompletion(true) && job3.waitForCompletion(true) && job4.waitForCompletion(true) && job5.waitForCompletion(true) && job6.waitForCompletion(true)){
		    	 	System.exit(1);
		    	 	
		     }
	     }
        
	}
 


 
}

