package dim;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import prepare.GlobalConstants;
import prepare.LogConstants;

/**
 * BrowserDimension类：
 * 				用于存放和使用ip及其浏览器信息
 * 				ip:IP地址  String
 * 				browserName: 浏览器名称  String
 * 				
 * @author jones
 *
 */
public class BrowserDimension extends BaseDimension {
    private String ip;
    private String date;
    private String browserName;
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	

    public BrowserDimension() {
    }
    
    public BrowserDimension(String ip, String date, String browserName) {
    	    this.ip = ip;
    	    this.date = date;
        this.browserName = browserName;
    }
    
    public BrowserDimension(String line) {
        String[] info = line.split(GlobalConstants.SplitSymbol);
        this.browserName = info[LogConstants.BROWSER];
        this.ip = info[LogConstants.IP];
        this.date = info[LogConstants.DATE];
    }
    /**
     *  清空ip和浏览器名称
     */
    public void clean() {
        this.ip = "";
        this.browserName = "";
        this.date = "";
    }
/*
    public static BrowserDimension newInstance(String browserName, String browserVersion) {
        BrowserDimension browserDimension = new BrowserDimension();
        browserDimension.browserName = browserName;
        return browserDimension;
    }
*/


    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowserName() {
        return this.browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }



    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.ip);
        out.writeUTF(this.date);
        out.writeUTF(this.browserName);
    }

    public void readFields(DataInput in) throws IOException {
        this.ip = in.readUTF();
        this.date = in.readUTF();
        this.browserName = in.readUTF();
    }

    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        } else {
            BrowserDimension other = (BrowserDimension)o;
            int tmp = this.ip.compareTo(other.ip);
            if (tmp != 0) {
                return tmp;
            } else {
            		if(this.date.compareTo(other.date) == 0){
                    tmp = this.browserName.compareTo(other.browserName);
                    return tmp; 
            		}else{
            			return this.date.compareTo(other.date);
            		}
            }
        }
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.browserName == null ? 0 : this.browserName.hashCode());
        // 将ip地址中的点(.)去掉，如127.0.0.1 -> 127001
        String numberFormatOfIP = "";
        for (String part: this.ip.split(".")){
        		numberFormatOfIP += part;
        }
        // 将String类型的ip(127001) 转换为整型
        result = 31 * result + Integer.parseInt(numberFormatOfIP);
        return result;
    }

    public boolean equals(Object obj) {
        
            BrowserDimension other = (BrowserDimension)obj;
           
            return this.ip == other.ip;
        
    }
}
