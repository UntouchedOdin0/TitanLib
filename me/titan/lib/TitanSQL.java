package me.titan.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class TitanSQL {

	private final String line;
	private final String user;
	private final String password;

	@Getter
	@Setter
	boolean showErrors = true;

	@Getter
	private Connection connection;

	public TitanSQL(String host, int port, String database, String user, String password) {
		this("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
	}

	public TitanSQL(String line, String user, String password) {
		this.line = line;
		this.user = user;
		this.password = password;

		connect();
	}

	private void connect() {
		try {
			connection = DriverManager.getConnection(line, user, password);
		} catch (final SQLException e) {
			if (showErrors) {
				System.out.println("Failed to connect to MySQL database!");

				e.printStackTrace();
			}
		}
	}

	public final void close() {
		try {
			if (connection != null)
				connection.close();

		} catch (final SQLException e) {
			if (showErrors) {

				System.out.println("Failed to close MySQL connection!");

				e.printStackTrace();
			}
		}
	}

	public final void update(String qry) {
		openIfClosed();

		try {
			final Statement st = connection.createStatement();

			st.executeUpdate(qry);
			st.close();

		} catch (final SQLException e) {
			if (showErrors) {

				System.out.println("Failed to update MySQL with query: " + qry);

				e.printStackTrace();
			}
		}
	}

	public final WrappedResultSet query(String qry) {
		openIfClosed();

		ResultSet rs = null;

		try {
			final Statement st = connection.createStatement();

			rs = st.executeQuery(qry);

		} catch (final SQLException e) {
			if (showErrors) {

				System.out.println("Failed to query MySQL with: " + qry);

				e.printStackTrace();
			}
		}

		return rs == null ? null : new WrappedResultSet(rs);
	}

	private final void openIfClosed() {
		try {
			if (connection == null || connection.isClosed() || !connection.isValid(0))
				connect();

		} catch (final SQLException ex) {
			if (showErrors) {

				System.out.println("Failed to reopen MySQL connection!");

				ex.printStackTrace();
			}
		}
	}

	@RequiredArgsConstructor
	public static class WrappedResultSet {

		@Getter
		private final ResultSet resultSet;

		public final boolean next() throws SQLException {
			return resultSet.next();
		}

		public final boolean hasColumn(String name) throws SQLException {
			final ResultSetMetaData meta = resultSet.getMetaData();

			for (int i = 1; i <= meta.getColumnCount(); i++)
				if (name.equals(meta.getColumnName(i)))
					return true;

			return false;
		}
	}
}
