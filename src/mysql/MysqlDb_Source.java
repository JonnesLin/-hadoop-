package mysql;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDb_Source extends MysqlDb {

	
	private String date;
	private String source;
	private long time;
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		this.date = arg0.readUTF();
		this.source = arg0.readUTF();
		this.time = arg0.readLong();
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(this.date);
		arg0.writeUTF(this.source);
		arg0.writeLong(this.time);
	}

	@Override
	public void readFields(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		this.date = arg0.getString(1);
		this.source = arg0.getString(2);
		this.time = arg0.getLong(3);
	}
	
	@Override
	public void write(PreparedStatement arg0) throws SQLException {
		// TODO Auto-generated method stub
		arg0.setString(1, this.date);
		arg0.setString(2, this.source);
		arg0.setLong(3, this.time);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
	
}