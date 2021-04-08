import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class QuickTest {
	public static void main(String[] args) {
		
		int n = 26;
		int i = 0;
		Movie[] hitMovies = new Movie[n];
		BufferedReader inputStream = null;
		String line;	
		
		try {
			try {
				inputStream = new BufferedReader(new FileReader("movie.txt"));
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				while((line = inputStream.readLine()) != null) {
					hitMovies[i] = new Movie(line.split("\t")[0], line.split("\t")[1],  new Integer(line.split("\t")[2]), new Integer(line.split("\t")[3]) );
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		//Quick.sort(hitMovies, new Movie.NameOrder());
		//Quick.sort(hitMovies, new Movie.directorOrder());
		//Quick.sort(hitMovies, new Movie.YearOrder());
		//Quick.sort(hitMovies, new Movie.YearDownOrder());
		
		//Quick.sort(hitMovies, new Movie.numberOrder());
		Quick.sort(hitMovies, new Movie.numberDownOrder());
		
		for(Movie m: hitMovies)
			System.out.println(m);
		
	}

}
