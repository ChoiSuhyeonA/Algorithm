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
		// Carriers Ŭ���� 1673�� ����
		Carriers[] carriers = new Carriers[1673];
		int i = 0;
		// ����ó������ ����ϴ� ����
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
			//�ؽ��ڵ带 �ؽð����� �����Ѵ�.
			hashValue = compressionFunction(hashList.get(i1));
			for(int i2=0; i2<i1; i2++) {
				//�ؽø���Ʈ�� �ߺ����� ���� ������ ������, �ؽð��� 1ĭ�� �ڷ� �̷��.
				while(hashValueList.contains(hashValue)) {
					hashValue = hashValue + 1;
				}
			}
			hashValueList.add(hashValue);
		}
       //�ؽð��� Carriers�� ����
		for(int i3=0; i3<carriers.length; i3++) {
			carriers[i3].setHashValue(hashValueList.get(i3));
		}
		
		//�װ��縦 �˻�
		java.util.Scanner scan = new java.util.Scanner(System.in);
		for(int k=0; k<5; k++) {
			System.out.print("�ؽð��� �Է��ϼ���:");
		    int location =scan.nextInt();	    
		    
			for(int i4=0; i4<carriers.length; i4++) {
			 
			    if(carriers[i4].getHashValue()== location) {
			    	System.out.println(carriers[i4].toString());
			    }
			}
		}
	
	    
		//�ؽ��ڵ� ���̺��� ������ ������
//		for(Carriers c: carriers)
//			System.out.println(c);

	}

	// �ؽ��ڵ带 ������ִ� �޼���
	public static int hashCodes(String string, int a) {
		// hash�� �ؽ��ڵ��̴�.
		int hash = 0;

		byte[] byte_str = new byte[string.length()];
		for (int i = 0; i < string.length(); i++) {
			byte_str[i] = (byte) string.charAt(i);

			hash = byte_str[i] + a * hash;
		}

		return Math.abs(hash);
	}
	
	//���̺��� ũ�⸦ ���ϴ� �޼���
	public static int  calculateTableSize() {
		int tableSize= 4;
	
		while(tableSize < hashList.size()*2) {
			tableSize = tableSize * 2;
		}
	
		return tableSize;
	}

	// �ؽ��ڵ� -> �����Լ�
	public static int compressionFunction(int hash) {
		// hashValues�� �ؽ��ڵ带 ������ �Լ��̴�.
		int hashValues = 0;
		// m�� ���̺� ũ��
		int m  = calculateTableSize();
		
		// �ؽð�= �ؽ��ڵ� % ���̺�ũ��
		hashValues = hash % m;
		

		return hashValues;
	}

}
