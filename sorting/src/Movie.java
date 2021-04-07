import java.util.Comparator;

public class Movie {
	private String name;
	private int year;
	
	public Movie(String name, int year) {
		this.name = name;
		this.year = year;
	}
	//개봉년도 오름차순 비교기 
	public static class YearOrder implements Comparator<Movie>{
		public int compare(Movie m1, Movie m2) {
			if(m1.year < m2.year) return -1;
			if(m1.year > m2.year) return 1;
			return 0;
		}
	}

	//영화제목 가나다순 비교기 
	public static class NameOrder implements Comparator<Movie>{
		public int compare(Movie m1, Movie m2) {
			return m1.name.compareTo(m2.name); 
			//c언어는 문자열비교 = strcmp(문자열1, 문자열2)
			//java는 문자열1.compareTo(문자열2) -> 자바는 객체지향이기 때문에 
		}
	}
	public String toString() {
		return name + "(" + year + ")";
	}
}
