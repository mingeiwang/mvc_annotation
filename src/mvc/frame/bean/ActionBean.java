package mvc.frame.bean;

import java.util.Arrays;

import org.framework.annotation.RequestMethod;

/**
 * Action对应的bean
 */
public class ActionBean {
	private String path;// 请求路径
	private RequestMethod[] types;// 请求类型(post、get)
	private Class[] params;// 方法参数集合
	private String classPath;// 处理类路径
	private String method;// 请求方法名称
	private boolean isResponseBody;// 是否将内容作为响应正文输出

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public RequestMethod[] getTypes() {
		return types;
	}

	public void setTypes(RequestMethod[] types) {
		this.types = types;
	}

	public Class[] getParams() {
		return params;
	}

	public void setParams(Class[] params) {
		this.params = params;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public boolean isResponseBody() {
		return isResponseBody;
	}

	public void setResponseBody(boolean isResponseBody) {
		this.isResponseBody = isResponseBody;
	}

	@Override
	public String toString() {
		return "ActionBean [path=" + path + ", types=" + Arrays.toString(types)
				+ ", params=" + Arrays.toString(params) + ", classPath="
				+ classPath + ", method=" + method + ", isResponseBody="
				+ isResponseBody + "]";
	}

}
