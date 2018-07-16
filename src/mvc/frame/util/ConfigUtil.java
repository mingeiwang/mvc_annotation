package mvc.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigUtil {
	
	private String REDIRECT_PREFIX;
	private String SCANNER_PATH;
	private String PAGE_PREFIX;
	private String PAGE_SUFFIX;
	private String E_CODE;
	private String MVC_CONTENT;
	
	private void init() throws IOException {
		InputStream in = ConfigUtil.class.getResourceAsStream("/config/config.properties");
		 Properties pr = new Properties();	
         pr.load(in);
         REDIRECT_PREFIX  = pr.getProperty("REDIRECT_PREFIX");
         SCANNER_PATH = pr.getProperty("SCANNER_PATH");
         PAGE_PREFIX  = pr.getProperty("PAGE_PREFIX");
         PAGE_SUFFIX = pr.getProperty("PAGE_SUFFIX");
         E_CODE = pr.getProperty("E_CODE");
         MVC_CONTENT = pr.getProperty("MVC_CONTENT");
	}
	
	public ConfigUtil() throws IOException {
		// TODO Auto-generated constructor stub
		init();
	}

	public String getREDIRECT_PREFIX() {
		return REDIRECT_PREFIX;
	}

	public void setREDIRECT_PREFIX(String rEDIRECT_PREFIX) {
		REDIRECT_PREFIX = rEDIRECT_PREFIX;
	}

	public String getSCANNER_PATH() {
		return SCANNER_PATH;
	}

	public void setSCANNER_PATH(String sCANNER_PATH) {
		SCANNER_PATH = sCANNER_PATH;
	}

	public String getPAGE_PREFIX() {
		return PAGE_PREFIX;
	}

	public void setPAGE_PREFIX(String pAGE_PREFIX) {
		PAGE_PREFIX = pAGE_PREFIX;
	}

	public String getPAGE_SUFFIX() {
		return PAGE_SUFFIX;
	}

	public void setPAGE_SUFFIX(String pAGE_SUFFIX) {
		PAGE_SUFFIX = pAGE_SUFFIX;
	}

	public String getE_CODE() {
		return E_CODE;
	}

	public void setE_CODE(String e_CODE) {
		E_CODE = e_CODE;
	}

	public String getMVC_CONTENT() {
		return MVC_CONTENT;
	}

	public void setMVC_CONTENT(String mVC_CONTENT) {
		MVC_CONTENT = mVC_CONTENT;
	}
	
	
	
}



