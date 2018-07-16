package mvc.frame.action;

import static mvc.frame.util.FullBean.syso;

import org.framework.annotation.Controller;
import org.framework.annotation.RequestMapper;

import mvc.frame.form.Action;
import mvc.frame.form.ActionForm;
import mvc.frame.form.LoginForm;
import mvc.frame.util.FullBean;
import mvc.frame.util.Log;

@Controller
public class LoginAction{

	private Log log = new Log("INFO", getClass());
	@RequestMapper(value = "/login")
	public String execute(String name,String password) {
		/*LoginForm loginForm = (LoginForm) form;
		syso(loginForm);
		return loginForm.toString();*/
		log.info("name:{}",name);
		log.info("password:{}",password);
		return "login";
	}
	
	@RequestMapper(value = "/login1")
	public String login() {
		return "login";
	}

}
