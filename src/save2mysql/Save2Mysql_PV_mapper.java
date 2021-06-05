package save2mysql;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import mysql.MysqlDb;

public class Save2Mysql_PV_mapper extends MysqlMapper{

	@Override
	public void map(Text key, LongWritable value, Mapper<Text, LongWritable, IntWritable, MysqlDb>.Context context) {
		// TODO Auto-generated method stub
		
	}

}
