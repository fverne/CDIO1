package datatransfer;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 4545864587995944260L;
	private int	userId;
	private String userName;
	private String ini;
	private ArrayList<String> roles;
	private String password;
	private String cpr;
	//TODO Add relevant fields
	
	public UserDTO(int userId, String userName, String ini, ArrayList<String> roles, String password, String cpr) {
		this.userId = userId;
		this.userName = userName;
		this.ini = ini;
		this.roles = roles;
		this.password = password;
		this.cpr = cpr;
	}

	public UserDTO() {
	}


	public String getPassword() {
		return password;
	}

	public String getCpr() {
		return cpr;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCpr(String cpr) {
		this.cpr=cpr;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIni() {
		return ini;
	}
	public void setIni(String ini) {
		this.ini = ini;
	}

	public ArrayList<String> getRoles() {
		return roles;
	}
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	
	public void addRole(String role){
		this.roles.add(role);
	}
	/**
	 * 
	 * @param role
	 * @return true if role existed, false if not
	 */
	public boolean removeRole(String role){
		return this.roles.remove(role);
	}

	@Override
	public String toString() {
		return "[userId=" + userId + ", userName=" + userName + ", ini=" + ini + ", roles=" + roles + ", password=" + password + ", cpr=" + cpr + "]";
	}



}
