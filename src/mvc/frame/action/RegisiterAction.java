package mvc.frame.action;

import static mvc.frame.util.FullBean.syso;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapper;
import org.framework.annotation.RequestMethod;

import mvc.frame.form.Action;
import mvc.frame.form.ActionForm;
import mvc.frame.form.RegisiterForm;
import mvc.frame.util.FullBean;
import mvc.frame.util.Log;

@Controller
public class RegisiterAction {

	private Log log = new Log("INFO", getClass());
	@RequestMapper(value = "/register")
	public String execute(String username ,String password, String password2) {
		// TODO Auto-generated method stub
		log.info("username:{}",username);
		log.info("password:{}",password);
		log.info("password2:{}",password2);
		return "register";
	}
	@RequestMapper(value = "/register1")
	public String register() {
		return "register";
	}

}
