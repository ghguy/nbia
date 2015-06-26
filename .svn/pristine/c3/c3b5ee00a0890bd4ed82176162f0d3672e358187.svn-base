import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * @author lethai
 *
 */
public class PasswordConversion {
	private static Logger logger = Logger.getLogger(PasswordConversion.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// connect to database
		// read all user info
		
		// reset user password
		// encrypt new reset password
		// update database with new password (encrypted)
		// generate email and send it each user
		
		DatabaseAccess db = new DatabaseAccess();
		db.connect();
		List<User> userList = db.retriveUser();
		List<User> userListWithNewPwd = new ArrayList<User>();
		PasswordConversion pc = new PasswordConversion();
		
		try {
			StringEncrypter se = new StringEncrypter();			
			
			for(int i=0; i<userList.size(); i++) {
				User u = userList.get(i);
				String resetPassword = pc.resetPassword(u);				
				String encryptedPwd = se.encrypt(resetPassword);
				u.setResetPassword(resetPassword);
				u.setResetPasswordEncrypted(encryptedPwd);
				userListWithNewPwd.add(u);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		SendMail smail = new SendMail();
		for(int i=0; i<userListWithNewPwd.size(); i++) {
			int rc = db.updateUser(userListWithNewPwd.get(i).getUserId(), userListWithNewPwd.get(i).getResetPasswordEncrypted());
			if(rc == 1) {
				System.out.println("Updating password successfully for user: " + userListWithNewPwd.get(i).getUserId());
				logger.info("Updating password successfully for user: " + userListWithNewPwd.get(i).getUserId());
//				 send email to each user with their new password				
				smail.sendMail(userListWithNewPwd.get(i).getResetPassword(), userListWithNewPwd.get(i).getLoginEmail(),
						userListWithNewPwd.get(i).getFirstName());
				logger.info("Send email notification successfully for user: " + userListWithNewPwd.get(i).getUserId());
				System.out.println("Send email notification successfully for user: " + userListWithNewPwd.get(i).getUserId());
			}
			/*System.out.println("user id " + userListWithNewPwd.get(i).getUserId() + " " + userListWithNewPwd.get(i).getFirstName() +
					" " + userListWithNewPwd.get(i).getLastName() + " email: " + userListWithNewPwd.get(i).getLoginEmail() +
					" new password: " + userListWithNewPwd.get(i).getResetPassword() + " new encrypted password: " + userListWithNewPwd.get(i).getResetPasswordEncrypted());*/
		}
		
		db.close();
		
	}
	//reset user password to be the first letter of first name and 10 letter of last name, 
	//if length of lastname is less than 10, then use whole last name
	
	public String resetPassword(User u) {
		String resetPassword ="";
		Long userId = u.getUserId();
		String firstName = u.getFirstName();
		String lastName = u.getLastName();
		resetPassword = firstName.substring(0, 1) + lastName;
		
		// make sure it is not more than 10 characters
		if(resetPassword.length() > 10 ) {
			//System.out.println("password is longer than 10");
			resetPassword = resetPassword.substring(0, 10);
		}
		// if it less than 5, add some phone number if not null
		if(resetPassword.length() < 5) {
			//System.out.println("password is less than 5");
			String phone = u.getPhone();
			if (phone != null ) {
				//System.out.println("phone is not empty: " + phone.trim() + " phone length is " + (phone.trim()).length()) ; // ((phone.trim()).length() - 4));
				int len = (phone.trim()).length();
				resetPassword += phone.substring(len - 4, len);
			}
		}
		//System.out.println("Reseting password successfully for user: " + userId + " Name: " + firstName + " " + lastName);
		logger.info("Reseting password successfully for user: " + userId + " Name: " + firstName + " " + lastName);
		return resetPassword;
	}
	
	

}
