package mvc.frame.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import mvc.frame.form.ActionForm;

public class FullBean {

	public FullBean() {
		// TODO Auto-generated constructor stub
	}
	
	public static ActionForm full(HttpServletRequest request){
		return full(request.getParameter("sign"), request);
	}
	
	public static ActionForm full(String className, HttpServletRequest request){
		ActionForm o = null;
		try{
			//根据类名获取对象
			Class clazz = Class.forName(className);
			o = (ActionForm) clazz.newInstance();
			//给属性赋值
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				field.set(o, request.getParameter(field.getName()));
				field.setAccessible(false);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return o;
	}
	
	public static void syso(Object o){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		System.out.println(sdf.format(new Date())+":"+o);
	}
}
