package config;


/**
 * 返回值code常量
 * 
 * @author rishi
 *
 */
public class ResponseCodeCanstants {
	public static final String SUCCESS = "200"; // 成功
	public static final String FAILED = "500"; // 失败

	public static final String NOAUTH = "402"; // 未登錄

	public static final String CRY_STATUS_C = "1"; // 出院
	public static final String CRY_STATUS_R = "0"; // 住院

	public static final String HIS_INHOSP_MODIFY_NO = "1"; // 没有修改

	public static final String HIS_INHOSP_MODIFY_YES = "2"; // 有修改
	//分页
	public static final String DEFAULT_PAGE_SIZE = "10";
	public static final String DEFAULT_PAGE_NUMBER = "0";
	//邮箱地址
	public static final String Mail_from = "935339656@qq.com";

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
}
