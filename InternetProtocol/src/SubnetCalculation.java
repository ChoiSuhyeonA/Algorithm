import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/* 
 *  ���α׷��� : ����� �ڵ� ����
 *  �ۼ��� : �ּ���
 *  �ۼ�����: 2021-04-28
 *  ���α׷� ���� ��� : ��Ʈ��ũ �ּҿ� ����� ��, �� ����� �Ҵ� �ּ� ���� �Է��ؼ� �Ҵ� ������ ������ ���Ѵ�.
 *  ���� : 1
 */

public class SubnetCalculation {
	public static String ip; // ipv4 �� �ּ�
	public static int defaultSubnet; // �⺻ �����
	public static int subnet; // ���ϴ� �����
	public static String[] s = new String[4]; // ip�ּҸ� .���� �κ��� ���� (�� 4��)

	public static int[] assignmentAddress = new int[1000]; // �� ����� �Ҵ� �ּ�
	public static int[] assignmentAddress_Range = new int[1000]; // �� ������� �Ҵ��ϱ� ���� �ʿ��� ��Ʈ��ũ ����

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.print("��Ʈ��ũ �ּҸ� �Է��ϼ���:  ");
		ip = scan.next();

		s = ip.split("[.]"); // ��Ʈ��ũ �ּҸ� ���� ex) 192.168.219.100 -> [192, 168, 219, 100]

		// Ŭ���� ���� ���ϱ� (��Ʈ)
		System.out.println("Host prefix:" + classArea(Integer.parseInt(s[0])));

		System.out.print("�⺻ ����� ���� �Է��ϼ���: ");
		defaultSubnet = scan.nextInt();
		System.out.print("���ϴ� ������� �Է��ϼ���:");
		subnet = scan.nextInt();
		// ���ϴ� ����� ����ŭ �� ����� �Ҵ� �ּ� �Է��ϱ�
		int i = 1;
		int tmp = subnet;
		while (tmp > 0) {
			System.out.print("�� ����� �Ҵ� �ּҸ� �Է��ϼ���  " + i + "��°: ");
			assignmentAddress[i - 1] = scan.nextInt();
			assignmentAddress_Range[i-1] = subnetSize(assignmentAddress[i - 1]);
			System.out.println("�ʿ��� ��Ʈ��ũ �ּ��� ������" + assignmentAddress_Range[i-1]);
			i++;
			tmp--;
		}
		// ����� ������ش�. ( Network id , Broadcast id, Range, SubnetMask)
		print();

	}

	// Ŭ���� ���� ���ϱ� ( A. B. C. D. E)
	public static int classArea(int host) {
		if (host >= 0 && host <= 127) {
			return 8; // AŬ����
		} else if (host >= 128 && host <= 191) {
			return 16; // BŬ����
		} else if (host >= 192 && host <= 223) {
			return 24; // CŬ����
		}
		return 0; // D , E Ŭ����
	}

	// ������� ũ�⸦ ���ؼ� ������ ���ϱ�
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

	// ����� ������ִ¸޼����̴�.
	public static void print() {
		String networkId[] = new String[subnet];
		String broadcastId[] = new String[subnet];
		String range[] =  new String[subnet];
		String subnetMask[] =  new String[subnet];
		String addr = null;
		//����� ���� ����
		String network  = "0";
		String broad ="0";
		int j = 0;
		
		// ȣ��Ʈ ������ A�� ���
		if (classArea(Integer.parseInt(s[0])) == 24) {
			addr = s[0] + "." + s[1] + "." + s[2] + ".";
		}
		// ȣ��Ʈ ������ B�� ���
		else if (classArea(Integer.parseInt(s[0])) == 16) {
			addr = s[0] + "." + s[1] + ".";
		}
		// ȣ��Ʈ ������ C�� ���
		else if (classArea(Integer.parseInt(s[0])) == 8) {
			addr = s[0] + ".";
		}
		
		
		System.out.println("****************�����********************");

		for (int i = 0; i < subnet; i++) {
			//��Ʈ��ũ ���̵� ���ϱ�
			networkId[i] = addr +network;
			//��ε�ĳ��Ʈ ���̵� ���ϱ�
		
			broadcastId[i] = addr + String.valueOf( Integer.parseInt(network)+assignmentAddress_Range[i]-1); 
			//���� ���ϱ�
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
