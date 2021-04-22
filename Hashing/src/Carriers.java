
public class Carriers {
 
	int hashValue=0;
	String airlineCode;
	String airlineName;
	
	public Carriers(String airlineCode, String airlineName) {
		this.airlineCode = airlineCode;
		this.airlineName = airlineName;
	}
	public Carriers() {
		
	}

	
	public int getHashValue() {
		return hashValue;
	}


	public void setHashValue(int hashValue) {
		this.hashValue = hashValue;
	}


	public String getAirlineCode() {
		return airlineCode;
	}


	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}


	public String getAirlineName() {
		return airlineName;
	}


	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return hashValue+" - "+airlineCode+" - "+airlineName;
	}
}
