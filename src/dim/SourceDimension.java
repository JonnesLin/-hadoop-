package dim;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import prepare.GlobalConstants;
import prepare.LogConstants;
/**
 * SourceDimension类：
 * 				用于存放和使用ip, date, source信息
 * 				ip:IP地址  String
 * 				date:日期  String
 * 				source: 访客来源  String
 * 				
 * @author jones
 *
 */
public class SourceDimension extends BaseDimension{

	private String ip;
	private String source;
	private String date;
	
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public  SourceDimension() {
		// TODO Auto-generated constructor stub
		this.date  = "";
		this.ip = "";
		this.source = "";
	}
	
	public SourceDimension(String line){
		String[] values = line.split(GlobalConstants.SplitSymbol);
		this.date = values[LogConstants.DATE];
		this.ip = values[LogConstants.IP];
		this.source = values[LogConstants.SOURCE];
	}
	
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		this.date = arg0.readUTF();
		this.ip = arg0.readUTF();
		this.source = arg0.readUTF();
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeUTF(this.date);
		arg0.writeUTF(this.ip);
		arg0.writeUTF(this.source);
		
	}

	@Override
	public int compareTo(BaseDimension o) {
		// TODO Auto-generated method stub
		SourceDimension sourceDimension = (SourceDimension)o;
		if(this.ip.compareTo(sourceDimension.ip) == 0){
			if(this.date.compareTo(sourceDimension.date) == 0){
				return this.source.compareTo(sourceDimension.source);
			}
			return this.date.compareTo(sourceDimension.date);
		}
		return this.ip.compareTo(sourceDimension.ip);
	}

}
