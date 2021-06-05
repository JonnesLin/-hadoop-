package dim;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import prepare.GlobalConstants;
import prepare.LogConstants;
/**
 * IPDimension类：
 * 				用于存放和使用ip信息
 * 				ip:IP地址  String
 * 				
 * @author jones
 *
 */
public class IPDimension extends BaseDimension{

	private String ip;
	
	public IPDimension(String line){
		String[] values = line.split(GlobalConstants.SplitSymbol);
		this.ip = values[LogConstants.IP];
		this.date = values[LogConstants.DATE];
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private String date;
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		this.ip = arg0.readUTF();
		this.date = arg0.readUTF();
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(this.ip);
		arg0.writeUTF(this.date);
	}

	@Override
	public int compareTo(BaseDimension o) {
		// TODO Auto-generated method stub
		IPDimension ipDimension = (IPDimension)o;
		if(this.ip.compareTo(ipDimension.ip) == 0){
			return this.date.compareTo(ipDimension.date);
		}
		return this.ip.compareTo(ipDimension.ip);
	}

}
