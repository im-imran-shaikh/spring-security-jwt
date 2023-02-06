package in.learnjavaskills.springsecurity.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.learnjavaskills.springsecurity.enums.Role;

@Entity
@Table(name = "userdetails")
public class UserdetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userid;
	private String useremail;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private boolean active;
	private boolean passwordexpired;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	  
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isPasswordexpired() {
		return passwordexpired;
	}
	public void setPasswordexpired(boolean passwordexpired) {
		this.passwordexpired = passwordexpired;
	}
	@Override
	public String toString() {
		return "UserdetailsEntity [userid=" + userid + ", useremail=" + useremail + ", password=" + password + ", role="
				+ role + ", active=" + active + ", passwordexpired=" + passwordexpired + "]";
	}
	
}
