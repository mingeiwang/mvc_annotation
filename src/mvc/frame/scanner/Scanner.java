package mvc.frame.scanner;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletContext;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapper;
import org.framework.annotation.ResponseBody;

import mvc.frame.bean.ActionBean;

/**
 * 扫描器类
 *
 */
public class Scanner {
	/**
	 * 扫描路径下所有文件
	 * @param path
	 * @param actionBeanMap
	 * @param servletContext 
	 * @return
	 */
  public static Map<String,ActionBean> scannerPackage(String path,Map<String,ActionBean> actionBeanMap, ServletContext servletContext) throws Exception{
	  System.out.println("路径为:"+path);
	  
	  String packagePath = path.replace(".","/");
	  System.out.println(packagePath);
	  URL url = Thread.currentThread().getContextClassLoader().getResource("/");
	  String filePath = url.getFile()+packagePath;
	  System.out.println(filePath);
	  File file = new File(filePath);
	  if(!file.exists()){
		  throw new RuntimeException("路径错误!");
	  }
	  
	  //目录
	  if(file.isDirectory()){
		  File[] files = file.listFiles();
		  for (File fi: files) {
			if(fi.isDirectory()){
				scannerPackage(path + "." + fi.getName(), actionBeanMap,servletContext);
			}
			if (fi.isFile()) {
                scannerClass(path + "." + fi.getName(), actionBeanMap,servletContext);
            }
		}
	  }
	  
	  //文件
	  if(file.isFile()){
		 scannerClass(path+"."+file.getName(), actionBeanMap,servletContext); 
	  }
	  
	  return actionBeanMap;
  }
  
  /**
   * 扫描类中的所有方法
 * @param servletContext 
   */
  public static void scannerClass(String filePath,Map<String, ActionBean> actionBeanMap, ServletContext servletContext) throws Exception{
	  String path = filePath;
	  if(filePath.endsWith(".class")){
		  path = filePath.substring(0,filePath.lastIndexOf(".class"));
	  }
	  
	  Class clazz = Class.forName(path);
	  
	  //非controller注解类跳过
	  if(!clazz.isAnnotationPresent(Controller.class)){
		  return;
	  }
	  
	  Method[] methods = clazz.getDeclaredMethods();
	  for (Method method : methods) {
		  if (method.isAnnotationPresent(RequestMapper.class)) {
              ActionBean ab = scannerMethod(clazz, method,servletContext);
              actionBeanMap.put(ab.getPath(), ab);
          }
	}
	  
  }
  
  /**
   * 得到方法参数及方法返回类型等并封装为ActionBean
 * @param servletContext 
   */
  public static ActionBean scannerMethod(Class cc, Method method, ServletContext servletContext){
	      RequestMapper requestMapper = method.getAnnotation(RequestMapper.class);
	      Controller controller = (Controller)cc.getAnnotation(Controller.class);
	      
	      ActionBean actionBean = new ActionBean();
	      actionBean.setClassPath(cc.getName());
	      actionBean.setMethod(method.getName());
	      actionBean.setTypes(requestMapper.method());
	      actionBean.setPath(servletContext.getContextPath()+controller.value() + requestMapper.value());
	      actionBean.setParams(method.getParameterTypes());
	      actionBean.setResponseBody(method.isAnnotationPresent(ResponseBody.class));
          return actionBean;
  }
}
