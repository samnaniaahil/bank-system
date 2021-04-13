
public class Customer {
	private String firstName;
	private String lastName;
	private String sin;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	
	public Customer(String firstName, String lastName, String sin, int birthYear, int birthMonth, int birthDay) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sin = sin;
		this.birthYear = birthYear;
		this.birthMonth = birthMonth;
		this.birthDay = birthDay;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getSin() {
		return sin;
	}
	
	public int getBirthYear() {
		return birthYear;
	}
	
	public int getBirthMonth() {
		return birthMonth;
	}
	
	public int getBirthDay() {
		return birthDay;
	}
		
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setSin(String sin) {
		this.sin = sin;
	}
	
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}
	
	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}
	
	public String toString() {
		return lastName + ", " + firstName + " " + sin;
	}
}
