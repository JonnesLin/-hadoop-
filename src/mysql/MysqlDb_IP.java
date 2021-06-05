package mysql;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * key：date --- ip
 * value：不同的ip总数
 * @author jones
 *
 */
public class MysqlDb_IP extends MysqlDb {



	private String date;
	private String ip;
	private long numberOfIp;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getNumberOfIp() {
		return numberOfIp;
	}

	public void setNumberOfIp(long numberOfIp) {
		this.numberOfIp = numberOfIp;
	}
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		this.date = arg0.readUTF();
		this.ip = arg0.readUTF();
		this.numberOfIp = arg0.readLong();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(this.date);
		arg0.writeUTF(this.ip);
		arg0.writeLong(this.numberOfIp);
		
	}

	@Override
	public void readFields(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		this.date = arg0.getString(1);
		this.ip = arg0.getString(2);
		this.numberOfIp = arg0.getLong(3);
	}

	@Override
	public void write(PreparedStatement arg0) throws SQLException {
		// TODO Auto-generated method stub
		arg0.setString(1, this.date);
		arg0.setString(2, this.ip);
		arg0.setLong(3, this.numberOfIp);
	}
}