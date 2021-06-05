package dim;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

import prepare.GlobalConstants;
import prepare.LogConstants;
/**
 * PV信息维度
 * 维度信息：
 * 		ip：用户ip String
 * 		time：用户IP出现次数 long
 * 		date：时间（年/月/日） String
 * @author jones
 *
 */
public class PVDimension extends BaseDimension{

	private String ip;
	private long time;
	private String date;
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	
	
	public PVDimension() {
		// TODO Auto-generated constructor stub
		this.ip = "";
		this.time = 0;
		this.date = null;
	}
	
	
	public PVDimension(String ip, long time, String date){
		this.ip = ip;
		this.time = time;
		this.date = date;
	}
	
	@SuppressWarnings("deprecation")
	public PVDimension(String line){
		String[] info = line.split(GlobalConstants.SplitSymbol);
		this.ip = info[LogConstants.IP];
		this.date = info[LogConstants.DATE];
		this.time = 1;
		
	}
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		this.ip = arg0.readUTF();
		this.date = arg0.readUTF();
		this.time = arg0.readLong();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(this.ip);
		arg0.writeUTF(this.date);
		arg0.writeLong(this.time);
	}

	@Override
	public int compareTo(BaseDimension o) {
		// TODO Auto-generated method stub
		PVDimension pvDimension = (PVDimension)o;
		if(this.ip.compareTo(pvDimension.ip) == 0){
			return this.date.compareTo(pvDimension.date);
		}
		return this.ip.compareTo(pvDimension.ip);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
