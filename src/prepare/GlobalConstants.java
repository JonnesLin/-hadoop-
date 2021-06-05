package prepare;

public class GlobalConstants {
	
	public static final int DIR_OF_INPUT_DATA = 0;
	public static final int DIR_OF_OUTPUT_DATA = 1;
	public static final String DRIVECLASS="com.mysql.jdbc.Driver";
	public static final String MYSQL_URL="jdbc:mysql://192.168.0.8:3306/hadoop";
	public static final String MYSQL_USERNAME="root";
	public static final String MYSQL_PASSWORD="123456";
    public static final String RUNNING_DATE_PARAMES = "RUNNING_DATE";
    public static final String DEFAULT_VALUE = "unknown";
    public static final String VALUE_OF_ALL = "all";
    public static final String OUTPUT_COLLECTOR_KEY_PREFIX = "collector_";
    public static final String WAREHOUSE_OF_REPORT = "report";
    public static final String JDBC_BATCH_NUMBER = "mysql.batch.number";
    public static final String DEFAULT_JDBC_BATCH_NUMBER = "500";
    public static final String JDBC_DRIVER = "mysql.%s.driver";
    public static final String JDBC_URL = "mysql.%s.url";
    public static final String JDBC_USERNAME = "mysql.%s.username";
    public static final String JDBC_PASSWORD = "mysql.%s.password";
    public static final String SplitSymbol = "---";
    public static final String UnitedKey = "";
    public static final String DB_OF_PV_DATE = "Date";
    public static final String DB_OF_PV_VALUE = "PV_value";
    public static final String DIR_OF_IP = "/IP";
    public static final String DIR_OF_PV = "/PV";
    public static final String DIR_OF_SOURCE = "/SOURCE";
    public static final String DIR_OF_JUMPUPRATE = "/JUMPUPRATE";
    public static final String DIR_OF_BROWSER= "/BROWSER";
    public static final String TEMPORARY_DIR_OF_CLEAN_DATA= "/CLEAN_DATA";
    public static final int BROWSER_DATE= 0;
    public static final int BROWSER_NAME= 1;
    public static final int IP_DATE= 0;
    public static final int IP_IP= 1;
    public static final int SOURCE_DATE= 0;
    public static final int SOURCE_SOURCE= 1;
    public static final String SOURCE_TABLE_NAME ="SOURCE";
    public static final String[] SOURCE_TABLE_COLUMNS_NAME ={"DATE", "SOURCE","TIME"};
    public static final String IP_TABLE_NAME ="IP";
    public static final String[] IP_TABLE_COLUMNS_NAME ={"DATE", "IP","TIME"};
    public static final String BROWSER_TABLE_NAME ="BROWSER";
    public static final String[] BROWSER_TABLE_COLUMNS_NAME ={"DATE", "BROWSER_NAME","TIME"};
    public static final String JUMP_UP_RATE_TABLE_NAME ="JUMP_UP_RATE";
    public static final String[] JUMP_UP_RATE_TABLE_COLUMNS_NAME ={"DATE", "JUMP_UP_RATE"};
    public static final String PV_TABLE_NAME ="PV";
    public static final String[] PV_TABLE_COLUMNS_NAME ={"DATE", "PV"};
    
    public GlobalConstants() {
    }
}
