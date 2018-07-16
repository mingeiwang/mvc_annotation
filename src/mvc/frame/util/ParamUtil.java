package mvc.frame.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数工具类
 * 
 */
public class ParamUtil {
	/**
	 * 获取参数实体
	 * 
	 * @param methodParams
	 * @param inputParam
	 * @return
	 */
	public static Object[] getParamEntity(Class[] methodParams,
			Object... inputParam) {
		if (methodParams == null || methodParams.length == 0) {
			return null;
		}

		HttpServletRequest request = (HttpServletRequest) findClass(inputParam,
				"javax.servlet.http.HttpServletRequest");
		Object[] objArray = new Object[methodParams.length];
		for (int i = 0; i < objArray.length; i++) {
			Class clazz = methodParams[i];
			Object classObj = findClass(inputParam, clazz.getName());
			if (classObj != null) {
				objArray[i] = classObj;
				continue;
			}

			try {
				Object paramEntity = clazz.newInstance();
				List<Field> fieldList = new ArrayList<Field>();

				do {
					Field[] declaredFields = clazz.getDeclaredFields();
					for (Field field : declaredFields) {
						fieldList.add(field);
					}
					clazz = clazz.getSuperclass();
				} while (clazz != Object.class);

				for (Field field : fieldList) {
					String value = request.getParameter(field.getName());
					if (value != null) {
						field.setAccessible(true);
						field.set(paramEntity, value);
						field.setAccessible(false);
					}
				}
				objArray[i] = paramEntity;
			} catch (Exception e) {

			}

		}
		return objArray;
	}

	/**
	 * classnName获取对象
	 * 
	 * @param inputParam
	 * @param className
	 * @return
	 */
	private static Object findClass(Object[] inputParam, String className) {
		Object object = null;
		for (Object obj : inputParam) {
			if (inputParam.getClass().getName().equals(className)) {
				object = obj;
				break;
			}
			Class[] interfaces = obj.getClass().getInterfaces();
			for (Class itf : interfaces) {
				if (className.equals(itf.getName())) {
					object = obj;
					break;
				}
			}
		}
		return object;
	}
}
