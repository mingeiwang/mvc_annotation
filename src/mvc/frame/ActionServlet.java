package mvc.frame;

import static mvc.frame.util.FullBean.full;
import static mvc.frame.util.FullBean.syso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.frame.action.LoginAction;
import mvc.frame.action.RegisiterAction;
import mvc.frame.bean.ActionBean;
import mvc.frame.form.Action;
import mvc.frame.form.ActionForm;
import mvc.frame.form.LoginForm;
import mvc.frame.scanner.Scanner;
import mvc.frame.util.ConfigUtil;
import mvc.frame.util.FullBean;
import mvc.frame.util.Log;
import mvc.frame.util.ParamUtil;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ConfigUtil config;
	private Log log = new Log("INFO", ActionServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String sign = request.getParameter("sign");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		ActionForm actionForm = full(sign, request);
		Action action = null;
		//action = method1(sign, action);
		Map<String, String> map = ActionMapping.getMap();
		try {
			String className = map.get(sign);
			Class clazz = Class.forName(className);
			action = (Action) clazz.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String message = action.execute(actionForm);
		PrintWriter pw = response.getWriter();
		pw.println(message);
		pw.close();*/
		deal(request, response);
	}

	private Action method1(String sign, Action action) {
		/*if("mvc.frame.form.LoginForm".equals(sign)){
			action = new LoginAction();
		} else if("mvc.frame.form.RegisiterForm".equals(sign)){
			action = new RegisiterAction();
		}*/
		return action;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	public void deal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map<String, ActionBean> beanMap = (Map<String, ActionBean>)getServletContext().getAttribute(config.getMVC_CONTENT());
		Set<String> sets = beanMap.keySet();
		for (String string : sets) {
			log.info("key:{},value:{}",string,beanMap.get(string));
		}
		String path = request.getRequestURI();
		if(path.endsWith(".html")){
			path = path.replace(getServletContext().getContextPath(), "");
			sendHtml(path, response, request);
			return;
		}
		log.info("path:{}",path);
		ActionBean actionBean = beanMap.get(path);
		log.info("actionBean:{}",actionBean);
		if(actionBean == null){
			PrintWriter out = response.getWriter();
			out.print("error");
			return;
		}
		 
		 Object[] params = ParamUtil.getParamEntity(actionBean.getParams(),request,response,request.getSession());
		 Object result = "";
		 try {
			Class clazz = Class.forName(actionBean.getClassPath());
			Method method = clazz.getMethod(actionBean.getMethod(), actionBean.getParams());//得到哪个方法，参数是
			result = method.invoke(clazz.newInstance());//执行哪个对象的的方法,参数是什么
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		if(actionBean.isResponseBody()){
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		}else if(result instanceof String){
			String url = (String) result;if (url.startsWith(config.getREDIRECT_PREFIX())) {
                response.sendRedirect(url.substring(config.getREDIRECT_PREFIX().length()));
            } else {
                request.getRequestDispatcher(config.getPAGE_PREFIX() + url + config.getPAGE_SUFFIX()).forward(request, response);
            }
        } else {
        	log.error("-----> 返回类型错误");

        }
	}
	
	private void sendHtml(String path,HttpServletResponse response,HttpServletRequest request) throws IOException{
		String rootPath =  Thread.currentThread().getContextClassLoader().getResource("/").getFile();
		rootPath = rootPath.split("WEB-INF")[0];
		path = rootPath + path;
		log.info("path:{}",path);
		FileReader reader = new FileReader(new File(path));
		BufferedReader br = new BufferedReader(reader);
		PrintWriter out = response.getWriter();
		String line = br.readLine();
		while(line != null){
			out.append(line);
			line = br.readLine();
		}
		out.flush();
		out.close();
	}

	@Override
	public void init() throws ServletException {
		try {
			config = new ConfigUtil();
			getServletContext().setAttribute(config.getMVC_CONTENT(), Scanner.scannerPackage(config.getSCANNER_PATH(), new HashMap<>(),getServletContext()));
			log.info("--mvc初始化成功!--");
		} catch (Exception e) {
			log.info("--mvc初始化失败--");
			log.error("初始化错误", e);
		}
	}
 
	
	
	
	
}
