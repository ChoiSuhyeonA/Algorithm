import java.util.Comparator;

public class Insertion {
	public static void sort(Movie[] hits, Comparator comparator) {
		int n = hits.length;
		for (int i = 1; i < n; i++) {
			Object x = hits[i];
			int j = i - 1;
			while (j >= 0 && comparator.compare(hits[j], x)>0) {
				hits[j + 1] = hits[j];
				j--;
			}
			hits[j + 1] = (Movie) x;
		}
	}
}