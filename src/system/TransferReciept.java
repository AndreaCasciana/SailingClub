package system;

import java.sql.ResultSet;
import java.sql.SQLException;

import utility.ConnectDB;

/**
 * 
 * TransferReciept class
 *
 */
public class TransferReciept extends ConnectDB{
	private int id;
	private String IBAN;
	private String bank;
	private String accountName;
	
	/**
	 * constructor
	 */
	public TransferReciept() {super();}
	/**
	 * constructor
	 * @param id
	 * @param iBAN
	 * @param bank
	 * @param accountName
	 */
	public TransferReciept(int id, String iBAN, String bank, String accountName) {
		super();
		this.id = id;
		IBAN = iBAN;
		this.bank = bank;
		this.accountName = accountName;
	}
	/**
	 * constructor
	 * @param iBAN
	 * @param bank
	 * @param accountName
	 */
	public TransferReciept(String iBAN, String bank, String accountName) {
		super();
		IBAN = iBAN;
		this.bank = bank;
		this.accountName = accountName;
	}
	
	/**
	 * @return id of Transfer
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets id of Transfer
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return IBAN of Transfer
	 */
	public String getIBAN() {
		return IBAN;
	}

	/**
	 * sets IBAN of Transfer
	 * @param IBAN
	 */
	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}

	/**
	 * @return bank of Transfer
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * sets bank of Transfer
	 * @param bank
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return account name of Transfer
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * sets account name of Transfer
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	/**
	 * add transfer receipt to database
	 * @param transferReciept
	 * @return true if successful
	 */
	public int addTransfer(TransferReciept transferReciept) {
		String query = "INSERT INTO transfer_reciept_details (IBAN, bank, accountName) "
				+ "VALUES ('"+transferReciept.getIBAN()+"','"+transferReciept.getBank()+"','"+transferReciept.getAccountName()+"')";
		if (executeUpdate(query)) {
			query = "SELECT LAST_INSERT_ID()";
			ResultSet result = executeQuery(query);
			if (result != null)
				try {
					return result.getInt("LAST_INSERT_ID()");
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return 0;
	}

	/**
	 * get transfer receipt from database
	 * @param idTransfer
	 * @return true if successful
	 */
	public TransferReciept getTransfer(int idTransfer) {
		String query = "SELECT * FROM transfer_reciept_details  WHERE id = " + idTransfer;
		ResultSet result = executeQuery(query);
		if (result != null)
			try {
				do {
					return new TransferReciept(result.getInt("id"),result.getString("IBAN"),result.getString("bank"), result.getString("accountName"));
				}while(result.next());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
}
