package in.learnjavaskills.springsecurity.enums;

public enum TokenPrefix 
{
	prefix("Bearer ");
	
	private String prefixValue;
	
	private TokenPrefix(String prefix)
	{
		this.prefixValue = prefix;
	}
	
	public String getPrefixValue()
	{
		return this.prefixValue;
	}
	
}
