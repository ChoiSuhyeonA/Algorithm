import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/* 
 *  프로그램명 : 서브넷 자동 계산기
 *  작성자 : 최수현
 *  작성일자: 2021-04-28
 *  프로그램 동작 방식 : 네트워크 주소와 서브넷 수, 각 서브넷 할당 주소 수를 입력해서 할당 가능한 범위를 구한다.
 *  버전 : 1
 */

public class SubnetCalculation {
	public static String ip; // ipv4 의 주소
	public static int defaultSubnet; // 기본 서브넷
	public static int subnet; // 원하는 서브넷
	public static String[] s = new String[4]; // ip주소를 .마다 부분을 나눔 (총 4개)

	public static int[] assignmentAddress = new int[1000]; // 각 서브넷 할당 주소
	public static int[] assignmentAddress_Range = new int[1000]; // 각 서브넷을 할당하기 위해 필요한 네트워크 범위

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.print("네트워크 주소를 입력하세요:  ");
		ip = scan.next();

		s = ip.split("[.]"); // 네트워크 주소를 구분 ex) 192.168.219.100 -> [192, 168, 219, 100]

		// 클래스 영역 구하기 (비트)
		System.out.println("Host prefix:" + classArea(Integer.parseInt(s[0])));

		System.out.print("기본 서브넷 수를 입력하세요: ");
		defaultSubnet = scan.nextInt();
		System.out.print("원하는 서브넷을 입력하세요:");
		subnet = scan.nextInt();
		// 원하는 서브넷 수만큼 각 서브넷 할당 주소 입력하기
		int i = 1;
		int tmp = subnet;
		while (tmp > 0) {
			System.out.print("각 서브넷 할당 주소를 입력하세요  " + i + "번째: ");
			assignmentAddress[i - 1] = scan.nextInt();
			assignmentAddress_Range[i-1] = subnetSize(assignmentAddress[i - 1]);
			System.out.println("필요한 네트워크 주소의 범위는" + assignmentAddress_Range[i-1]);
			i++;
			tmp--;
		}
		// 결과를 출력해준다. ( Network id , Broadcast id, Range, SubnetMask)
		print();

	}

	// 클래스 영역 구하기 ( A. B. C. D. E)
	public static int classArea(int host) {
		if (host >= 0 && host <= 127) {
			return 8; // A클래스
		} else if (host >= 128 && host <= 191) {
			return 16; // B클래스
		} else if (host >= 192 && host <= 223) {
			return 24; // C클래스
		}
		return 0; // D , E 클래스
	}

	// 서브넷의 크기를 통해서 범위를 구하기
	public static int subnetSize(int subnet) {
		if (subnet >= 128) {
			return 255;
		} else if (subnet >= 64) {
			return 128;
		} else if (subnet >= 32) {
			return 64;
		} else if (subnet >= 16) {
			return 32;
		} else if (subnet >= 8) {
			return 16;
		} else if (subnet >= 4) {
			return 8;
		} else if (subnet >= 2) {
			return 4;
		} else if (subnet >= 1) {
			return 2;
		}
		return 1;
	}

	// 결과를 출력해주는메서드이다.
	public static void print() {
		String networkId[] = new String[subnet];
		String broadcastId[] = new String[subnet];
		String range[] =  new String[subnet];
		String subnetMask[] =  new String[subnet];
		String addr = null;
		//계산을 위한 변수
		String network  = "0";
		String broad ="0";
		int j = 0;
		
		// 호스트 영역이 A일 경우
		if (classArea(Integer.parseInt(s[0])) == 24) {
			addr = s[0] + "." + s[1] + "." + s[2] + ".";
		}
		// 호스트 영역이 B일 경우
		else if (classArea(Integer.parseInt(s[0])) == 16) {
			addr = s[0] + "." + s[1] + ".";
		}
		// 호스트 영역이 C일 경우
		else if (classArea(Integer.parseInt(s[0])) == 8) {
			addr = s[0] + ".";
		}
		
		
		System.out.println("****************결과값********************");

		for (int i = 0; i < subnet; i++) {
			//네트워크 아이디 구하기
			networkId[i] = addr +network;
			//브로드캐스트 아이디 구하기
		
			broadcastId[i] = addr + String.valueOf( Integer.parseInt(network)+assignmentAddress_Range[i]-1); 
			//범위 구하기
			range[i] = addr+String.valueOf(Integer.parseInt(network)+1)+"~"+addr+String.valueOf( Integer.parseInt(network)+assignmentAddress_Range[i]-1-1);
			
			if( Integer.parseInt(network)+assignmentAddress_Range[i]-1<=127 ) {
				j= 1;
			}
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=191) {
				j=2;
			}
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=223) {
				j=3;
			}
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=239) {
				j=4;
			}
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=247) {
				j=5;
			}
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=251) {
				j=6;
			}
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=253) {
				j=7;
			}	
			else if(Integer.parseInt(network)+assignmentAddress_Range[i]-1<=255) {
				j=8;
			}	
			subnetMask[i] = String.valueOf(defaultSubnet+j); 
			
			
			System.out.print("SN" + i + " : ");
			System.out.print("Network_ID: "+ networkId[i]+"\t\t");
			System.out.print("BroadCast_ID: "+  broadcastId[i]+"\t\t");
			System.out.print( "Range: "+ range[i]+"\t");
			System.out.println("/"+ subnetMask[i]);
			
			
		
			network =String.valueOf( Integer.parseInt(network)+assignmentAddress_Range[i]);
		}
		
	}
}
