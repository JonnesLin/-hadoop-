package prepare;


public enum KpiType {
	/*
	 * PV:浏览量
	 */
	
	PV("PV"),
	/*
	 * IP:ip，一个ip代表一个唯一的用户
	 */
	IP("IP"),
	/*
	 * Bounce_Rate: 跳出率
	 */
	Bounce_Rate("Bounce_Rate"),
	/*
	 * TYPE_OF_SOURCE:访客来源
	 */
	TYPE_OF_SOURCE("TYPE_OF_SOURCE"),
	/*
	 * TYPE_OF_BROWSER:浏览器类型
	 */
	TYPE_OF_BROWSER("TYPE_OF_BROWSER");

    public final String name;

    private KpiType(String name) {
        this.name = name;
    }

}
