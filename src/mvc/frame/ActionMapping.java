package mvc.frame;

import java.util.HashMap;
import java.util.Map;

public class ActionMapping {

	
	public ActionMapping() {
		// TODO Auto-generated constructor stub
	}
	
	public static Map<String, String> getMap(){
		Map<String, String> map = new HashMap<>();
		map.put("mvc.frame.form.LoginForm", "mvc.frame.action.LoginAction");
		map.put("mvc.frame.form.RegisiterForm", "mvc.frame.action.RegisiterAction");
		return map;
	}
}
