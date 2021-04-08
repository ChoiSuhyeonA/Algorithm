
public class mcss_bfs {
	public static void main(String[] args) {
		int[ ] a = {-7, 4, -3, 6, 3, -8, 3, 4};
		System.out.print(mcss_bfs(a));
	}
	public static int mcss_bfs(int a[]) {
		int n = a.length;
		int max= 0;
		int sum= 0;
		for(int i =0; i<n; i++) {
			for(int j=i; j<n; j++) {
				sum += a[j];
				if(sum>max) {
					max =sum;
					sum=0;
				}	
			}
		}
		return max;
	}
}
