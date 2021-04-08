import java.util.Comparator;

public class Movie {
	public String name; // 영화제목
	public String director; // 영화감독
	public int year; // 영화개봉연도
	public int number; // 관객수

	public Movie(String name, String director, int year, int number) {
		this.name = name;
		this.director = director;
		this.year = year;
		this.number = number;
	}

	// 영화제목 가나다순 비교기
	public static class NameOrder implements Comparator<Movie> {
		public int compare(Movie m1, Movie m2) {
			return m1.name.compareTo(m2.name);
		}
	}
	// 감독 가나다순 비교기
		public static class directorOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				return m1.director.compareTo(m2.director);
			}
		}
	// 개봉년도 오름차순 비교기
	public static class YearOrder implements Comparator<Movie> {
		public int compare(Movie m1, Movie m2) {
			if (m1.year < m2.year)
				return -1;
			if (m1.year > m2.year)
				return 1;
			return 0;
		}
	}
	// 개봉년도 내림차순 비교기
		public static class YearDownOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				if (m1.year < m2.year)
					return 1;
				if (m1.year > m2.year)
					return -1;
				return 0;
			}
		}
		// 관객수 오름차순 비교기
		public static class numberOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				if (m1.number < m2.number)
					return -1;
				if (m1.number > m2.number)
					return 1;
				return 0;
			}
		}
		// 관객수 내림차순 비교기
		public static class numberDownOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				if (m1.number < m2.number)
					return 1;
				if (m1.number > m2.number)
					return -1;
				return 0;
			}
		}

	public String toString() {
		return name + " - " + director + " - " +   year + " - " +  number ;
	}

}
