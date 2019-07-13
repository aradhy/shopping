package shop.dto;



public class Response<T>
	{
		T obj;
		String message;
		Integer code;
		public Response(T obj, String message, Integer code) {
			super();
			this.obj = obj;
			this.message = message;
			this.code = code;
		}
		
		public Response(String message, Integer code) {
			super();
			this.message = message;
			this.code = code;
		}
		public T getObj() {
			return obj;
		}
		public void setObj(T obj) {
			this.obj = obj;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
	}
	

