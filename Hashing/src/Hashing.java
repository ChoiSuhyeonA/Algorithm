import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class Hashing {
	static int hash = 0;
	static int hashValue = 0;
	 static ArrayList<Integer> hashList = new ArrayList<Integer>();
	 static ArrayList<Integer> hashValueList = new ArrayList<Integer>();

	public static void main(String[] args) {
		// Carriers 클래스 1673개 생성
		Carriers[] carriers = new Carriers[1673];
		int i = 0;
		// 파일처리에서 사용하는 변수
		String line;
		BufferedReader reader = null;

		int a = 31;

		try {
			reader = new BufferedReader(new FileReader("CARRIERS.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((line = reader.readLine()) != null) {
				carriers[i] = new Carriers();
				carriers[i].setAirlineCode(line.split("\t")[0]);
				carriers[i].setAirlineName(line.split("\t")[1]);
				hash = hashCodes(line.split("\t")[0], a);
				hashList.add(hash);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i1=0; i1<hashList.size(); i1++) {
			//해시코드를 해시값으로 변경한다.
			hashValue = compressionFunction(hashList.get(i1));
			for(int i2=0; i2<i1; i2++) {
				//해시리스트에 중복으로 값을 가지고 있으면, 해시값을 1칸식 뒤로 미룬다.
				while(hashValueList.contains(hashValue)) {
					hashValue = hashValue + 1;
				}
			}
			hashValueList.add(hashValue);
		}
       //해시값을 Carriers의 저장
		for(int i3=0; i3<carriers.length; i3++) {
			carriers[i3].setHashValue(hashValueList.get(i3));
		}
		
		//항공사를 검색
		java.util.Scanner scan = new java.util.Scanner(System.in);
		for(int k=0; k<5; k++) {
			System.out.print("해시값을 입력하세요:");
		    int location =scan.nextInt();	    
		    
			for(int i4=0; i4<carriers.length; i4++) {
			 
			    if(carriers[i4].getHashValue()== location) {
			    	System.out.println(carriers[i4].toString());
			    }
			}
		}
	
	    
		//해시코드 테이블의 정보를 보여줌
//		for(Carriers c: carriers)
//			System.out.println(c);

	}

	// 해시코드를 만들어주는 메서드
	public static int hashCodes(String string, int a) {
		// hash는 해시코드이다.
		int hash = 0;

		byte[] byte_str = new byte[string.length()];
		for (int i = 0; i < string.length(); i++) {
			byte_str[i] = (byte) string.charAt(i);

			hash = byte_str[i] + a * hash;
		}

		return Math.abs(hash);
	}
	
	//테이블의 크기를 구하는 메서드
	public static int  calculateTableSize() {
		int tableSize= 4;
	
		while(tableSize < hashList.size()*2) {
			tableSize = tableSize * 2;
		}
	
		return tableSize;
	}

	// 해시코드 -> 압축함수
	public static int compressionFunction(int hash) {
		// hashValues는 해시코드를 압축한 함수이다.
		int hashValues = 0;
		// m은 테이블 크기
		int m  = calculateTableSize();
		
		// 해시값= 해시코드 % 테이블크기
		hashValues = hash % m;
		

		return hashValues;
	}

}
