package in.learnjavaskills.springsecurity.enums;

public enum Secretkeys 
{
	JWT_KEYS("dfjaklfhjallaiepowraoij4kladsfie4lafkldaiowf829ihjelgsfdgsdfgtydfghtrhsdhgsfghrthsdfhs");
	
	private String key;
	
	private Secretkeys(String key)
	{
		this.key = key;
	}
	
	public String getKey()
	{
		return this.key;
	}
}
