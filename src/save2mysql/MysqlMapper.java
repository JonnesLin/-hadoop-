package save2mysql;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import mysql.MysqlDb;

public abstract class MysqlMapper extends Mapper<Text, LongWritable, IntWritable ,MysqlDb> {
	public abstract void map(Text key, LongWritable value, Context context);

}
