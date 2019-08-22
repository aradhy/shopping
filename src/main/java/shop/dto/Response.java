package shop.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response<T> {
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

	public static <T> ResponseEntity<Response<T>> OK() {
		return get(null, null, HttpStatus.OK);
	}

	public static <T> ResponseEntity<Response<T>> CREATED() {
		return get(null, null, HttpStatus.CREATED);
	}

	public static <T> ResponseEntity<Response<T>> CREATED(T data) {
		return get(data, null, HttpStatus.CREATED);
	}

	public static <T> ResponseEntity<Response<T>> OK(T data) {
		return get(data, null, HttpStatus.OK);
	}

	private static <T> ResponseEntity<Response<T>> get(T data, String message, HttpStatus status) {
		return new ResponseEntity<>(new Response<T>(data, message, null), status);

	}

}
