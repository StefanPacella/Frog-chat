package it.project.chat.data.domainmodel;

import java.text.ParseException;
import java.util.Optional;

import it.project.chat.framework.UtilityDateandTime;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DataItem;

public abstract class Message implements DataItem, Comparable<Message> {

	private Integer id;

	private String text;

	private String data;

	private String time;

	private Integer idusersender;

	private Integer iduserreceiver;

	private boolean hasbeenread;

	private UtilityDateandTime utilityDateandTime = new UtilityDateandTime();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getIdusersender() {
		return idusersender;
	}

	public void setIdusersender(Integer idusersender) {
		this.idusersender = idusersender;
	}

	public Integer getIduserreceiver() {
		return iduserreceiver;
	}

	public void setIduserreceiver(Integer iduserreceiver) {
		this.iduserreceiver = iduserreceiver;
	}

	public boolean isHasbeenread() {
		return hasbeenread;
	}

	public void setHasbeenread(boolean hasbeenread) {
		this.hasbeenread = hasbeenread;
	}

	public abstract Optional<User> getUserSender() throws BusinessException;

	public abstract Optional<User> getUserReceiver() throws BusinessException;

	@Override
	public int compareTo(Message o) {
		// TODO Auto-generated method stub
		try {
			long l1 = utilityDateandTime.convertDateToDate(this.getData()).getTime()
					+ utilityDateandTime.convertStringToTime(this.getTime()).getTime();
			long l2 = utilityDateandTime.convertDateToDate(o.getData()).getTime()
					+ utilityDateandTime.convertStringToTime(o.getTime()).getTime();

			if(l1 > l2)
				return 1;
			if(l1 < l2)
				return -1;
			else
				return 0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

}
