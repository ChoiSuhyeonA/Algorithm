import java.util.Comparator;

public class Movie {
	public String name; // ��ȭ����
	public String director; // ��ȭ����
	public int year; // ��ȭ��������
	public int number; // ������

	public Movie(String name, String director, int year, int number) {
		this.name = name;
		this.director = director;
		this.year = year;
		this.number = number;
	}

	// ��ȭ���� �����ټ� �񱳱�
	public static class NameOrder implements Comparator<Movie> {
		public int compare(Movie m1, Movie m2) {
			return m1.name.compareTo(m2.name);
		}
	}
	// ���� �����ټ� �񱳱�
		public static class directorOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				return m1.director.compareTo(m2.director);
			}
		}
	// �����⵵ �������� �񱳱�
	public static class YearOrder implements Comparator<Movie> {
		public int compare(Movie m1, Movie m2) {
			if (m1.year < m2.year)
				return -1;
			if (m1.year > m2.year)
				return 1;
			return 0;
		}
	}
	// �����⵵ �������� �񱳱�
		public static class YearDownOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				if (m1.year < m2.year)
					return 1;
				if (m1.year > m2.year)
					return -1;
				return 0;
			}
		}
		// ������ �������� �񱳱�
		public static class numberOrder implements Comparator<Movie> {
			public int compare(Movie m1, Movie m2) {
				if (m1.number < m2.number)
					return -1;
				if (m1.number > m2.number)
					return 1;
				return 0;
			}
		}
		// ������ �������� �񱳱�
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
