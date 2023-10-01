package it.project.chat.framework.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import freemarker.template.TemplateException;

public abstract class OutputStreamCatch extends Writer {

	private TemplateResult templateResult;
	private String path = "";
	private char[] catturaOutputStream = new char[0];

	public OutputStreamCatch(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
	}

	public void init(String template, ServletContext servletContext) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		templateResult = new TemplateResult(path, servletContext);
		templateResult.init();
		templateResult.setupServletResponseTextHtml();
		templateResult.make(this, template);
		setOutput(new String(catturaOutputStream));
	}

	public void init(String template, ServletContext servletContext, Map<Object, Object> dati)
			throws TemplateException, IOException {
		// TODO Auto-generated method stub

		templateResult = new TemplateResult(path, servletContext);
		templateResult.init();
		templateResult.setupServletResponseTextHtml();

		for (Map.Entry<Object, Object> entry : dati.entrySet()) {
			templateResult.aggiungiDato(entry.getKey(), entry.getValue());
		}

		templateResult.make(this, template);
		setOutput(new String(catturaOutputStream));
	}

	public void initJS(String template, ServletContext servletContext, Map<String, String> dati)
			throws TemplateException, IOException {
		// TODO Auto-generated method stub

		templateResult = new TemplateResult(path, servletContext);
		templateResult.init();
		templateResult.setupServletResponseJavaScript();

		for (Map.Entry<String, String> entry : dati.entrySet()) {
			templateResult.aggiungiDato(entry.getKey(), entry.getValue());
		}

		templateResult.make(this, template);
		setOutput(new String(catturaOutputStream));
	}

	@Override
	public void write(String p) throws IOException {
		char[] a = new char[p.length()];
		for (int i = 0; i < p.length(); i++) {
			a[i] = p.charAt(i);
		}
		catturaOutputStream = copiaICaratteri(catturaOutputStream, a);
	}

	@Override
	public void write(String p, int off, int len) throws IOException {
		char[] a = new char[p.length()];
		for (int i = 0; i < p.length(); i++) {
			a[i] = p.charAt(i);
		}
		catturaOutputStream = copiaICaratteri(catturaOutputStream, a);
	}

	@Override
	public void write(char cbuf[]) {
		catturaOutputStream = copiaICaratteri(catturaOutputStream, cbuf);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		catturaOutputStream = copiaICaratteri(catturaOutputStream, cbuf);
	}

	private char[] copiaICaratteri(char[] a, char[] b) {
		synchronized (this) {
			char[] r = new char[a.length + b.length];
			System.arraycopy(a, 0, r, 0, a.length);
			System.arraycopy(b, 0, r, a.length, r.length - a.length);
			return r;
		}
	}

	public abstract void setOutput(String catturaOutputStream);

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
