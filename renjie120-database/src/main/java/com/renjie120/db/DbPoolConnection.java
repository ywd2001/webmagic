package com.renjie120.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

/**
 * 一个简单的druid数据连接池.
 * @author Administrator
 *
 */
public class DbPoolConnection {
	private static DbPoolConnection databasePool = null;
	private static DruidDataSource dds = null;
	static {
		Properties properties = loadPropertyFile("db_server.properties");
		try {
			dds = (DruidDataSource) DruidDataSourceFactory
					.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DbPoolConnection() {
	}

	public static synchronized DbPoolConnection getInstance() {
		if (null == databasePool) {
			databasePool = new DbPoolConnection();
		}
		return databasePool;
	}

	public DruidPooledConnection getConnection() throws SQLException {
		return dds.getConnection();
	}

	public static Properties loadPropertyFile(String fullFile) {
		String webRootPath = null;
		if (null == fullFile || fullFile.equals(""))
			throw new IllegalArgumentException(
					"Properties file path can not be null : " + fullFile);
//		webRootPath = DbPoolConnection.class.getClassLoader().getResource("")
//				.getPath();
//		System.out.println(new File(webRootPath).getAbsolutePath());
//		webRootPath = new File(webRootPath).getParent();
		InputStream inputStream = null;
		Properties p = null;
		try {
			inputStream = new FileInputStream(new File("d:"
					+ File.separator + fullFile));
			p = new Properties();
			p.load(inputStream);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found: "
					+ fullFile);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Properties file can not be loading: " + fullFile);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

}