package mvc.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private String level;
	
	private String classInfo;
	private Class clazz;
	
	
	
	public Log(String level,Class clazz) {
		super();
		this.level = level.toUpperCase();
		this.clazz = clazz;
		Package package1 = clazz.getPackage();
		classInfo = clazz.getName();
	}


	public boolean debug(){
		return "DEBUG".equals(level);
	}
	
	
	public boolean info(){
		return "INFO".equals(level) || debug();
	}
	
	public void info(String message,Object...objects){
		syso(message, objects);
	}
	
	public void debug(String message,Object...objects){
		if(debug()){
			syso(message, objects);
		}
	}
	
	private String format(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}


	private void syso(String message, Object... objects) {
		String info = format();
		for (Object object : objects) {
			String string = object == null ? "null" : object.toString();
			message = message.replace("{}", string);
		}
		System.out.println(info + " ["+ level + "]"+ " " + classInfo + " " + message);
	}
	
	public void error(String message,Object...e){
		syso(message, e);
	}
	
	public void error(String message,Exception e){
		syso(message, e.getMessage());
		e.printStackTrace(System.out);
	}
	
	public static void main(String[] args) {
		Log log = new Log("debug", Process.class);
		log.debug("消息：{}，名字：{}", "123456","1234567");
	}
}
