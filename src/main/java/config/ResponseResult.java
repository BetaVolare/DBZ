package config;

public class ResponseResult {
	private String code;
	private Object data;
	public ResponseResult() {
		super();
	}
	private String message;
	public ResponseResult(String code, Object data, String message) {
		super();
		this.code = code;
		this.data = data;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResponseResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public static ResponseResult success(){
		return new ResponseResult(ResponseCodeCanstants.SUCCESS, "成功");
	}
	public static ResponseResult failed(){
		return new ResponseResult(ResponseCodeCanstants.FAILED, "失败");
	}
}
