import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * @author lethai
 *
 */
public class DatabaseAccess {
	private static Logger logger = Logger.getLogger(DatabaseAccess.class);
	public static String DB_PROPERTIES = "database.properties";
	
	private String driver;
	private String url;
	private String username;
	private String password;
	private String userIdList;
	private Connection conn;
	
	public DatabaseAccess() {
		
		//get system properties
		Properties prop = new Properties();
        prop = System.getProperties();

        InputStream in = Thread.currentThread().getContextClassLoader()
                               .getResourceAsStream(DB_PROPERTIES);

        try {
            prop.load(in);
        } catch (IOException ioe) {
            logger.error("Unable to load NCIA properties", ioe);
        }
		//Properties prop = new Properties();
		//prop = PropertyLoader.loadProperties( "database.properties");
		
		driver = prop.getProperty("driver");
		url = prop.getProperty("url");
		username = prop.getProperty("username");
		password = prop.getProperty("password");
		userIdList = prop.getProperty("userid");
		logger.info("driver: " + driver + "\turl: " + url + "\tusername: " + username + "\tpassword: " + password);
		logger.info("userid: " +userIdList);
	}
	
	public Connection connect() {
		try {
			
			Class.forName(driver);
			
		}catch(ClassNotFoundException e) {
			System.err.println("Driver could not be loaded " + e.getMessage());
		}
		
		try {
			
			conn = DriverManager.getConnection(url, username, password);
			
			System.out.println("got connection: " + conn);
		}catch(SQLException e) {
			//System.err.println("SQLException: " + e.getMessage());
			logger.error("SQL Exception: " + e);
		}
		return conn;
	}
	
	public List<User> retriveUser(){
		List<User> userList = new ArrayList<User>();
		Statement stmt = null;
        ResultSet rs = null;
        String sql="";
        // read in user list from properties file, if not empty, use it. If empty, then select all user
        if(userIdList.length() > 0) {
        	sql= "select * from csm_user where user_id in (" + userIdList + ")";
        }else {
        	sql = "select * from csm_user";
        }
        logger.info("sql " + sql);
        /*Development DB 
        Jeff Bollers id 3124
        David Bauer 2644
        Qinyan Pan 1556
        carl blake 2652
        Yiming Hu 3094
        David Palmer 2922*/
        //String sql= "select * from csm_user where user_id =1893";
        //String sql= "select * from csm_user where user_id in (3124, 2644, 1556, 2652)";
        try {
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	
        	 while(rs.next()){
                 User aUser = new User();
                 aUser.setUserId(rs.getLong("user_id"));                 
                 aUser.setLastName(rs.getString("last_name"));
                 aUser.setLoginEmail(rs.getString("login_name"));
                 aUser.setPassword(rs.getString("password"));
                 aUser.setTitle(rs.getString("title"));
                 aUser.setPhone(rs.getString("phone_number"));
                 aUser.setFirstName(rs.getString("first_name"));
                 userList.add(aUser);
                 
             }
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	try {
        		stmt.close();
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
		logger.info("List of user: " + userList.size());
		return userList;
	}
	
	public int updateUser(Long user_id, String password) {
		
		String sql = "update csm_user set password='" + password + "' where user_id=" + user_id ;
		//System.out.println(sql);
		Statement stmt = null;
		int rc=-1;
		try {				
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			/*for(int i=0; i<userList.size(); i++) {
				user_id = userList.get(i).getUserId();
				password = userList.get(i).getResetPasswordEncrypted();*/
				rc = stmt.executeUpdate(sql);
				if(rc==1) {
					logger.info("Successfully update password for user id " + user_id);
				}else {
					logger.info("UnSuccessfully update password for user id " + user_id);
				}					
			//}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return rc;
		  
	}
	public int close() {
		try {
			if(conn != null) {
				conn.close();
				return 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

}
