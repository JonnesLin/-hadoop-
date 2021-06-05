package mysql;

import prepare.GlobalConstants;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class MysqlDb_PV extends MysqlDb {
 
    private String date;
    private long time;
 
    /**
     *数据序列化
     */
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.date);
        out.writeLong(this.time);
    }
 
    public void readFields(DataInput in) throws IOException {
        this.date = in.readUTF();
        this.time = in.readLong();
    }
 
    /**
     * 数据库读写
     * 
     */
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.date);
        statement.setLong(2,this.time);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
        this.date = resultSet.getString(1);
        this.time = resultSet.getLong(2);
    }
 
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    
    @Override
    public String toString() {
        return "Date: "+this.date + GlobalConstants.SplitSymbol+"Time: "+this.time;
    }
}


