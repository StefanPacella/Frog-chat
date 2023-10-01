package it.project.chat.framework.template;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import freemarker.core.HTMLOutputFormat;
import freemarker.core.JSONOutputFormat;
import freemarker.core.JavaScriptOutputFormat;
import freemarker.core.PlainTextOutputFormat;
import freemarker.core.XMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class TemplateResult {

	private Map<Object, Object> data = new HashMap<Object, Object>();
	private Configuration cfg;
	private String dir;
	private ServletContext servletContext;

	public TemplateResult(String dir, ServletContext servletContext) {
		// TODO Auto-generated constructor stub
		this.dir = dir;
		this.servletContext = servletContext;
	}

	public void init() throws IOException {
		cfg = new Configuration(Configuration.VERSION_2_3_32);
		cfg.setServletContextForTemplateLoading(servletContext, dir);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setFallbackOnNullLoopVariable(false);
	}

	public void aggiungiDato(Object etichetta, Object dato) {
		data.put(etichetta, dato);
	}

	public void make(Writer out, String template) throws TemplateException, IOException {
		Template temp = null;
		try {
			temp = cfg.getTemplate(template);
			temp.process(data, out);
		} catch (TemplateException | IOException ex) {
			Logger.getLogger(TemplateResult.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void setupServletResponseTextHtml() {
		cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
	}

	public void setupServletResponseXML() {
		cfg.setOutputFormat(XMLOutputFormat.INSTANCE);
	}

	public void setupServletResponseJson() {
		cfg.setOutputFormat(JSONOutputFormat.INSTANCE);
	}
	
	public void setupServletResponsePlainText() {
		cfg.setOutputFormat(PlainTextOutputFormat.INSTANCE);
	}
	
	public void setupServletResponseJavaScript() {
		cfg.setOutputFormat(JavaScriptOutputFormat.INSTANCE);
	} 

}