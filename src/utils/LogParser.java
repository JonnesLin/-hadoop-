package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.cli.util.ExactComparator;
import org.apache.hadoop.hbase.generated.regionserver.regionserver_jsp;
import org.apache.hadoop.hbase.thrift.TBoundedThreadPoolServer;
import org.jboss.netty.util.internal.ReusableIterator;

import prepare.LogConstants;
/**
****************************
类的功能：解析日志为 IP 时间 访客来源 浏览器类型
输入：单行字符串:String
输出：包含IP 时间 访客来源 浏览器类型的字符串数组:String[]
作者：林锦弘
日期：2020/06/22
****************************
*/
public class LogParser {
	
		// 创建两种日期格式对象
        public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
                LogConstants.DATE_FORMAT_V1, Locale.ENGLISH);
        public static final SimpleDateFormat dateformat1 = new SimpleDateFormat(
                LogConstants.DATE_FORMAT_V2);
        
        /**
         * 功能：解析英文时间字符串
         * 
         * @param string
         * @return
         * @throws ParseException
         */
        @SuppressWarnings("deprecation")
		private Date parseDateFormat(String string) {
            Date parse = null;
            try {
                parse = FORMAT.parse(string);
            } catch (ParseException e) {
            }
            return new Date(parse.getYear(), parse.getMonth(), parse.getDay());
        }

        /**
         * 功能：构造函数
         * 
         * @param line：String
         * @return 数组含有4个元素，分别是 IP 时间 访客来源 浏览器类型
         */
        public String[] parse(String line) {
        		String ip = null;
        		String date = null;
            String source = null;
            String browser = null;
        	try{
        		
            ip= parseIP(line);
            source = parseSource(line);
            browser = parseBrowser(line);
            date = parseTime(line);
        	}catch(Exception e){
        		return null;
        	}
        		String[] newLine = new String[] { ip, date, source, browser };
            return newLine;
            
        }
        
        /**
         * 功能：解析资源信息
         * @param 
         * @return
         */
        private String parseSource(String line) {
            // TODO Auto-generated method stub
            int first = line.lastIndexOf ("http:");
            String source;
            if (first!=-1){
            final int last = line.indexOf("\"", first);
            source = line.substring(first , last);
            }
            else {
                first = line.lastIndexOf ("\"",(line.lastIndexOf("\"")-1));
                final int last = line.indexOf("/", first);
                source = line.substring(first + 1, last);

            }
            return source;
        }
        
		private String parseBrowser(String line) throws Exception {
			// TODO Auto-generated method stub
			line = line.trim();
        		int last = line.lastIndexOf("\"");
            int first = line.lastIndexOf(" ");
            String subString = line.substring(first + 1, last);
            
            if (subString.contains("+") ||subString.contains(";") || subString.contains(":") || subString.contains("http")){
        			throw new Exception();
            }
            if(subString.indexOf("\"") == 0 || subString.indexOf(".") == 0){
            		subString = subString.substring(1);
            }
            if(subString.indexOf(")") == subString.length() - 1){
        			subString = subString.substring(0, subString.indexOf(")"));
            }
            
            String[] browser_and_version = subString.split("/");
            if(browser_and_version.length != 2){
            		throw new Exception();
            }
            
            return browser_and_version[0];
		}

		/**
         * 功能：判断当前数据是否为有效的日志数据
         * @return boolean
         */
        public boolean isValid (String line) {
			Object object = this.parse(line);
			if(object == null)
				return false;
			return true;
		}
        
        /**
         * 功能：解析时间信息
         * @param line
         * @return
         */
        private String parseTime(String line) {
        		line = line.trim();
            final int first = line.indexOf("[");
            final int last = line.indexOf(" +");
            String time = line.substring(first + 1, last).trim();
            Date date = parseDateFormat(time);
            return dateformat1.format(date);
        }

        /**
         * 功能：解析IP信息
         * @param line
         * @return
         */
        private String parseIP(String line) {
        		line = line.trim();
        		final int last = line.indexOf(" - - ");
        		String ip = line.substring(0, last).trim();
            return ip;
        }
    }