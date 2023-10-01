package it.project.chat.framework.data;

public interface DataItem {

	public Integer getId();
	
	public void setId(Integer id);

	public Integer getVersione();
	
	public void setVersione(Integer id);

	public void destroy();
	
}
