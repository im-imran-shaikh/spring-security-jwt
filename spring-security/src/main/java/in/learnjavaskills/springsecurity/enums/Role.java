package in.learnjavaskills.springsecurity.enums;

public enum Role 
{
	ROLE_ADMIN("ADMIN"),
	ROLE_USER("USER");
	
	private String role;
	
	Role(String role)
	{
		this.role = role;
	}
	
	public String getRole()
	{
		return this.role;
	}
}
