
public class mcss_rec {
    static	int[ ] a = {-7, 4, -3, 6, 3, -8, 3, 4};
    
	public static void main(String[] args) {
		int low =0;
		int high = a.length-1;
		System.out.print( mcss_rec(low, high) );
	}
	
	public static int mcss_rec(int low, int high) {
		if(low == high) return a[low];
		int mid=(low+high)/2;
		
		int  left_sum = mcss_rec(low, mid);
		int right_sum = mcss_rec(mid+1, high);
		
		int lsum , rsum = 0, sum =0;
		
		 lsum = sum = a[mid];	
		for(int i=mid-1; i>=low; i-- ) {
			sum +=  a[i];
			if(sum>lsum) lsum = sum;
		}
		
		for(int i=mid+1; i<=high; i++ ) {
			sum+= a[i];
			if(sum>rsum) rsum = sum;			
		}

		int two_sided_sum = lsum + rsum;
		
		return max(left_sum, right_sum, two_sided_sum);
	}

	public static int max(int a, int b, int c) {
		int max=0;
		if(a>b) {
			if(a>c)
			max=a;
		   else
			max= c;
		}  
		else if(b> c) max= b;
		else max = c;
		
		return max;
	}

}
