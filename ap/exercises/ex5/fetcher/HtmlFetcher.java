package ap.exercises.ex5.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HtmlFetcher {

    public static List<String> fetchHtml(String urlAddress) throws IOException {
        System.out.println("Going to fetch "+urlAddress+" ...");
        HttpURLConnection connection = (HttpURLConnection) new URL(urlAddress).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setInstanceFollowRedirects(true);

        try (InputStream input = connection.getInputStream();
             Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter("\\A");
            String content = scanner.hasNext() ? scanner.next() : "";
            return Arrays.asList(content.split("\\r?\\n"));
        }
    }
}