package mysql;

import prepare.GlobalConstants;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class MysqlDb_Browser extends MysqlDb {
 
	private String date;
	private Long time;
    private String browser;
    
    /**
     *数据序列化
     */
    public void write(DataOutput out) throws IOException {
    		out.writeUTF(this.date);
    		out.writeUTF(this.browser);
        out.writeLong(this.time);
        
        
    }
 
    public void readFields(DataInput in) throws IOException {
    		this.date = in.readUTF();
    		this.browser = in.readUTF();
        this.time = in.readLong();
    }
 
    /**
     * 数据库读写
     * 
     */
    public void write(PreparedStatement statement) throws SQLException {
    		statement.setString(1, this.date);
        statement.setString(2,this.browser);
        statement.setLong(3,this.time);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
    		this.date = resultSet.getString(1);
    		this.browser = resultSet.getString(2);
        this.time = resultSet.getLong(3);
    }
 
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long ip) {
        this.time = ip;
    }
    public String getBrowser() {
        return this.browser;
    }
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    @Override
    public String toString() {
        return "date: " + GlobalConstants.SplitSymbol+ this.date + "Browser: "+this.browser + GlobalConstants.SplitSymbol+"time: "+this.time;
    }
}


