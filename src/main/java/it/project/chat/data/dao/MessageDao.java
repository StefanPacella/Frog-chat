package it.project.chat.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import it.project.chat.data.domainmodel.Message;
import it.project.chat.data.domainmodel.User;
import it.project.chat.data.proxy.MessageProxy;
import it.project.chat.framework.UtilityDateandTime;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DaoAbstract;
import it.project.chat.framework.data.DataException;

public class MessageDao extends DaoAbstract<Message> {

	public static final MessageDao INSTANCE = new MessageDao();
	private MessageQueryDbms messageQueryDbms;
	private UtilityDateandTime utilityDateandTime = new UtilityDateandTime();

	private MessageDao() {
		super("message", new String[] { "dataM", "timeM", "idusersender", "iduserreceiver" });
		// TODO Auto-generated constructor stub
		messageQueryDbms = new MessageQueryDbms(this);
	}

	@Override
	public void initImpl() throws DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyImpl() throws DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Message> querySelectConCondizioneSullaChiave(String[] valore) throws BusinessException {
		// TODO Auto-generated method stub
		return this.getCache().get(x -> {
			try {
				return x.getData().equals(valore[0]) && x.getTime().equals(valore[1])
						&& x.getIdusersender().equals(convertStringToInteger(valore[2]))
						&& x.getIduserreceiver().equals(convertStringToInteger(valore[3]));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		}, querySelectConCondizioneSullaChiaveNelDB(valore));
	}

	private int convertStringToInteger(String id) throws Exception {
		return Integer.parseInt(id);
	}

	@Override
	public Message risultatiSelect(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub
		try (ResultSet rs = preparedStatement.getResultSet()) {
			if (rs.next()) {
				Message r = make(rs);
				return r;
			}
		}
		return null;
	}

	@Override
	public Message make(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		MessageProxy messageProxy = new MessageProxy();
		messageProxy.setData(rs.getString("dataM"));
		messageProxy.setTime(rs.getString("timeM"));
		messageProxy.setHasbeenread(rs.getBoolean("hasbeenread"));
		messageProxy.setId(rs.getInt("id"));
		messageProxy.setIduserreceiver(rs.getInt("iduserreceiver"));
		messageProxy.setIdusersender(rs.getInt("idusersender"));
		messageProxy.setText(rs.getString("text"));
		messageProxy.setVersione(rs.getInt("version"));
		return messageProxy;
	}

	public void addNewMessage(Message m) throws BusinessException {
		try {
			Integer idSender = m.getUserSender().get().getId();
			Integer idReceiver = m.getUserReceiver().get().getId();
			this.checkIfElementIsAlreadyPresentInMemoryAndThenAdd(
					new String[] { m.getData(), m.getTime(), idSender + "", idReceiver + "" }, m);
		} catch (BusinessException | NoSuchElementException e) {
			// TODO: handle exception
			throw new BusinessException();
		}
	}

	@Override
	protected void methodAddInCacheAndDbms(Message o) throws BusinessException {
		// TODO Auto-generated method stub
		getCache().add(x -> {
			try {
				return x.getData().equals(o.getData()) && x.getTime().equals(o.getTime())
						&& x.getIdusersender().equals(o.getIdusersender())
						&& x.getIduserreceiver().equals(o.getIduserreceiver());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		}, o, messageQueryDbms.aggiungiUnNuovoMessaggio(o));
	}

	public List<Message> getTheLatestMessagesFromTimex(String utente, String contactUser, String fromDate,
			String fromTime) throws BusinessException {
		List<Message> l = this.getCache().getList(
				"getTheLatestMessagesFromTimex" + utente + contactUser + fromDate + fromTime,
				x -> predicateGetTheLatestMessagesFromTimeX(x, utente, contactUser, fromDate, fromTime),
				messageQueryDbms.getTheLatestMessagesFromTimex(utente, contactUser, fromDate, fromTime));
		return l.stream().sorted((x, y) -> y.compareTo(x)).limit(10).collect(Collectors.toList());
	}

	private boolean predicateGetTheLatestMessagesFromTimeX(Message m, String utente, String contactUser,
			String fromDate, String fromTime) {

		try {
			return predicategetTheLastTenPosts(m, utente, contactUser) && predicateMessagesFrom(m, fromDate, fromTime);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	private boolean predicateMessagesFrom(Message m, String fromDate, String fromTime) throws ParseException {
		boolean is = utilityDateandTime.xDateTimeDiffyDateTime(fromDate, fromTime, m.getData(), m.getTime()) > 0;
		return is;
	}

	public List<Message> getTheLastTenPosts(String utente, String contactUser) throws BusinessException {
		List<Message> l = this.getCache().getList("getTheLastTenPosts" + utente + contactUser,
				x -> predicategetTheLastTenPosts(x, utente, contactUser),
				messageQueryDbms.getTheLastTenPosts(utente, contactUser));
		return l.stream().sorted((x, y) -> y.compareTo(x)).limit(10).collect(Collectors.toList());
	}

	private boolean predicategetTheLastTenPosts(Message m, String utente, String contactUser) {
		try {
			return (m.getIduserreceiver().equals(Integer.parseInt(utente))
					&& m.getIdusersender().equals(Integer.parseInt(contactUser)))
					|| (m.getIdusersender().equals(Integer.parseInt(utente))
							&& m.getIduserreceiver().equals(Integer.parseInt(contactUser)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public List<Message> getTheNewOnes(String utente, String contactUser) throws BusinessException {
		List<Message> l = this.getCache().getList("getTheNewOnes" + utente + contactUser,
				x -> predicategetTheNewOnes(x, utente, contactUser),
				messageQueryDbms.getTheNewOnes(utente, contactUser));
		return l.stream().filter(x -> x.isHasbeenread() == false).sorted((x, y) -> y.compareTo(x)).collect(Collectors.toList());
	}

	private boolean predicategetTheNewOnes(Message m, String utente, String contactUser) {
		try {
			return (m.getIduserreceiver().equals(Integer.parseInt(utente))
					&& m.getIdusersender().equals(Integer.parseInt(contactUser)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public void getListContattiDellUserId(Integer id) {

	}

	public void updateAllHasbeenreadThatHaveBeenSentByTheOtherUser(Integer myid) throws BusinessException {
		this.getCache().updateAll(x -> x.getIduserreceiver().equals(myid), x -> x.setHasbeenread(true),
				messageQueryDbms.updateAllHasbeenread(myid));
	}

	public void updateHasbeenread(Integer id) throws BusinessException {
		try {
			Optional<Message> m = this.selectConId(id);
			m.get().setHasbeenread(true);
			this.getCache().update(x -> x.getId().equals(id), m.get(), messageQueryDbms.updateHasbeenread(id),
					this.makeQuerySelectWithIdDbms(id));
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
	}

	public List<User> getListUsersWhoWroteToTheUserReceiver(Integer idUser) throws BusinessException {
		return getCache().getList("getListUsersWhoWroteToTheUser" + idUser,
				x -> getListUsersWhoWroteToTheUserPredicate(x, idUser),
				messageQueryDbms.getListUsersWhoWroteToTheUser(idUser)).stream().map(x -> {
					try {
						return x.getUserSender().get();
					} catch (BusinessException e) {
						// TODO Auto-generated catch block
						return null;
					}
				}).distinct().filter(x -> x != null).collect(Collectors.toList());
	}

	private boolean getListUsersWhoWroteToTheUserPredicate(Message m, Integer idUser) {
		return m.getIduserreceiver().equals(idUser) && m.isHasbeenread() == false;
	}

}
