package ap.exercises.ex5.parser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlParser {

    public static String getFirstUrl(String htmlLine) {
        String url = null;
        int startIndex = htmlLine.indexOf("href=\"");
        if (startIndex >= 0) {
            try {
                int hrefLength = "href=\"".length();
                int endIndex = htmlLine.indexOf("\"", startIndex + hrefLength);
                url = htmlLine.substring(startIndex + hrefLength, endIndex);
            } catch (Exception e) {
                return null;
            }
        }
        return url;
    }

    private static List<String> getAllUrlsFromHtmlLinesStream(Stream<String> htmlLinesStream) throws IOException {
        List<String> urls = htmlLinesStream
                .map(line -> getFirstUrl(line))
                .filter(s -> s != null)
                .collect(Collectors.toList());
        return urls;
    }

    public static List<String> getAllUrlsFromList(List<String> htmlLines, String baseUrl) throws IOException {
        return htmlLines.stream()
                .map(line -> normalizeUrl(baseUrl, getFirstUrl(line)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<String> getAllUrlsFromList(List<String> htmlLines) throws IOException {
        return getAllUrlsFromHtmlLinesStream(htmlLines.stream());
    }

    private static String extractUrl(String htmlLine, String tag, String attr) {
        String url = null;
        String pattern = "<" + tag + "[^>]*" + attr + "=\"([^\"]+)\"";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(htmlLine);
        if (m.find()) {
            url = m.group(1);
        }
        return url;
    }

    public static String getFirstImageUrl(String htmlLine) {
        String url = extractUrl(htmlLine, "img", "src");
        if (url == null) {
            url = extractUrl(htmlLine, "img", "data-src");
        }
        return url;
    }

    public static String getFirstAudioUrl(String htmlLine) {
        String url = extractUrl(htmlLine, "audio", "src");
        if (url == null) {
            url = extractUrl(htmlLine, "source", "src");
        }
        return url;
    }

    public static List<String> getAllImageUrlsFromList(List<String> htmlLines) {
        return htmlLines.stream()
                .map(HtmlParser::getFirstImageUrl)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public static List<String> getAllImageUrlsFromFile(String filePath) throws IOException {
        List<String> htmlLines = Files.readAllLines(Path.of(filePath));
        return getAllImageUrlsFromList(htmlLines);
    }


    public static List<String> getAllAudioUrlsFromList(List<String> htmlLines) {
        return htmlLines.stream()
                .map(HtmlParser::getFirstAudioUrl)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public static List<String> getAllAudioUrlsFromFile(String filePath) throws IOException {
        List<String> htmlLines = Files.readAllLines(Path.of(filePath));
        return getAllAudioUrlsFromList(htmlLines);
    }

    public static String normalizeUrl(String baseUrl, String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        try {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return url;
            }
            return new URL(new URL(baseUrl), url).toString();
        } catch (Exception e) {
            return null;
        }
    }
}