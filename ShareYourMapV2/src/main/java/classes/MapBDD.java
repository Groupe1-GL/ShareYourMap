package classes;
import javax.jdo.annotations.PersistenceCapable;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.CharacterData;
import org.passay.PasswordGenerator;

@PersistenceCapable
public class MapBDD{
	private int id;
	private String name;
	private String creatorName;
	private int uid;
	private boolean access;
	private String sharingID;
	private static int id_map = 1;
	
	public static String generateSharingID() {
	    PasswordGenerator gen = new PasswordGenerator();
	    CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
	    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
	    lowerCaseRule.setNumberOfCharacters(2);
	 
	    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
	    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
	    upperCaseRule.setNumberOfCharacters(2);
	 
	    CharacterData digitChars = EnglishCharacterData.Digit;
	    CharacterRule digitRule = new CharacterRule(digitChars);
	    digitRule.setNumberOfCharacters(2);
	 
	    return gen.generatePassword(6, lowerCaseRule, 
	      upperCaseRule, digitRule);
	}
	
	public MapBDD(int uid, String name, String creator) {
		this.uid = uid;
		this.id = id_map++;
		this.creatorName = creator;
		this.name = name;
		this.access = false;
		this.sharingID = generateSharingID();
	}
	
	public MapBDD(int uid, int id, String name, String creator, boolean b) {
		this.uid = uid;
		this.id = id;
		this.name = name;
		this.creatorName = creator;
		this.access = b;
		this.sharingID = generateSharingID();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public int getUid() {
		return uid;
	}

	public boolean isAccess() {
		return access;
	}
	
	public void setAccess(boolean access) {
		this.access = access;
	}

	public String getSharingID() {
		return sharingID;
	}
	
}
