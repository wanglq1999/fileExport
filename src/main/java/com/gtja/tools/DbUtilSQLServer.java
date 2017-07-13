package com.gtja.tools;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * SQLServer���ݿ⹤�ߣ��������־�����
 * 
 * @author Bob
 *
 */
public class DbUtilSQLServer {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private static String DBClassName = null;
	private static String DBUrl = null;
	private static String DBName = null;
	private static String DBUser = null;
	private static String DBPassword = null;

	private static DbUtilSQLServer instance = null;

	private static Logger logger = Logger.getLogger(DbUtilSQLServer.class);

	/**
	 * ˽�еĹ���
	 */
	private DbUtilSQLServer() {
		loadConfig();
	}

	/**
	 * ��ȡʵ�����̰߳�ȫ
	 * 
	 * @return
	 */
	public static synchronized DbUtilSQLServer getInstance() {
		if (instance == null) {
			instance = new DbUtilSQLServer();
		}
		return instance;
	}

	/**
	 * �������ļ������������ݿ���Ϣ
	 */
	private void loadConfig() {
		try {
			DBClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			DBUrl = ConstantsTools.CONFIGER.getHostFrom();
			DBName = ConstantsTools.CONFIGER.getNameFrom();
			DBUser = ConstantsTools.CONFIGER.getUserFrom();
			DBPassword = ConstantsTools.CONFIGER.getPasswordFrom();

			Class.forName(DBClassName);
		} catch (Exception e) {

			logger.error(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���ӣ��̰߳�ȫ
	 * 
	 * @return
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {
		String user = "";
		String password = "";
		try {
//			DESPlus des = new DESPlus();
//			user = des.decrypt(DBUser);
//			password = des.decrypt(DBPassword);
		} catch (Exception e) {
			logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.decode") + e.toString());
			e.printStackTrace();
		}
		// ��DB���ñ��ʱ���»�ȡ
		if (!ConstantsTools.CONFIGER.getHostFrom().equals(DBUrl)
				|| !ConstantsTools.CONFIGER.getNameFrom().equals(DBName)
				|| !ConstantsTools.CONFIGER.getUserFrom().equals(DBUser)
				|| !ConstantsTools.CONFIGER.getPasswordFrom().equals(DBPassword)) {
			loadConfig();
			// "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
			connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, user,
					password);
			// �������ύ��ʽ��Ϊ�ֹ��ύ
			connection.setAutoCommit(false);
		}

		// ��connectionʧЧʱ���»�ȡ
		if (connection == null || connection.isValid(10) == false) {

			// "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
			connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, user,
					password);
			// �������ύ��ʽ��Ϊ�ֹ��ύ
			connection.setAutoCommit(false);
		}

		if (connection == null) {
			logger.error("Can not load SQL Server jdbc and get connection.");
		}
		return connection;
	}

	/**
	 * �������ӣ��̰߳�ȫ �����������ļ���ȡ
	 * 
	 * @return
	 * @throws SQLException
	 */
	public synchronized Connection testConnection() throws SQLException {
		loadConfig();
		// "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
		String user = "";
		String password = "";
		try {
//			DESPlus des = new DESPlus();
//			user = des.decrypt(DBUser);
//			password = des.decrypt(DBPassword);
		} catch (Exception e) {
			logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.decode") + e.toString());
			e.printStackTrace();
		}
		connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, user,
				password);
		// �������ύ��ʽ��Ϊ�ֹ��ύ
		connection.setAutoCommit(false);

		if (connection == null) {
			logger.error("Can not load SQL Server jdbc and get connection.");
		}
		return connection;
	}

	/**
	 * �������ӣ��̰߳�ȫ ��������δ���
	 * 
	 * @return
	 * @throws SQLException
	 */
	public synchronized Connection testConnection(String DBUrl, String DBName, String DBUser, String DBPassword)
			throws SQLException {
		loadConfig();
		// "jdbc:sqlserver://20.1.1.194:1433;DatabaseName=AIS20151221115438;"
		connection = DriverManager.getConnection("jdbc:sqlserver://" + DBUrl + ";DatabaseName=" + DBName, DBUser,
				DBPassword);
		// �������ύ��ʽ��Ϊ�ֹ��ύ
		connection.setAutoCommit(false);

		if (connection == null) {
			logger.error("Can not load SQL Server jdbc and get connection.");
		}
		return connection;
	}

	/**
	 * ��ȡ���ݿ�������˽�У��̰߳�ȫ
	 * 
	 * @throws SQLException
	 */
	private synchronized void getStatement() throws SQLException {
		getConnection();
		// ����statementʧЧʱ�����´���
		if (statement == null || statement.isClosed() == true) {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}
	}

	/**
	 * �رգ�����������������ӣ����̰߳�ȫ
	 * 
	 * @throws SQLException
	 */
	public synchronized void close() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}
		if (statement != null) {
			statement.close();
			statement = null;
		}
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	/**
	 * ִ�в�ѯ���̰߳�ȫ
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public synchronized ResultSet executeQuery(String sql) throws SQLException {
		getStatement();
		if (resultSet != null && resultSet.isClosed() == false) {
			resultSet.close();
		}
		resultSet = null;
		resultSet = statement.executeQuery(sql);
		return resultSet;
	}

	/**
	 * ִ�и��£��̰߳�ȫ
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public synchronized int executeUpdate(String sql) throws SQLException {
		int result = 0;
		getStatement();
		result = statement.executeUpdate(sql);
		return result;
	}

	/**
	 * ִ�����⣬�̰߳�ȫ
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public synchronized boolean execute(String sql) throws SQLException {
		boolean result;
		getStatement();
		result = statement.execute(sql);
		return result;
	}

}
