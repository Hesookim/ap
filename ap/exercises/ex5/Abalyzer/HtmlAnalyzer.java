package ap.exercises.ex5.Abalyzer;

import ap.exercises.ex5.Conf;
import ap.exercises.ex5.parser.HtmlParser;
import ap.exercises.ex5.utils.DirectoryTools;
import ap.exercises.ex5.utils.FileTools;
import ap.exercises.ex5.utils.ObjectCounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class HtmlAnalyzer {

    private static List<String> fileList = DirectoryTools.getFilesAbsolutePathInDirectory(Conf.SAVE_DIRECTORY);

    public static List<String> getAllUrls() {
        return fileList.stream()
                .map(FileTools::getTextFileLines)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(HtmlParser::getFirstUrl)
                .filter(Objects::nonNull)
                .filter(s -> s.length() > 1)
                .collect(Collectors.toList());
    }

    public static List<String> getTopUrls(int k) {
        Map<String, Long> urlCount = getAllUrls().stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        return urlCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static void printTopCountUrls(int k) {
        ObjectCounter<String> urlCounter = new ObjectCounter<>();
        getAllUrls().forEach(urlCounter::add);
        for (Map.Entry<String, Integer> urlCountEntry : urlCounter.getTop(k)) {
            System.out.println(urlCountEntry.getKey() + " -> " + urlCountEntry.getValue());
        }
    }

    public static void extractAndSaveMediaLinks() {
        Set<String> imageLinks = new HashSet<>();
        Set<String> audioLinks = new HashSet<>();

        for (String filePath : fileList) {
            List<String> lines = FileTools.getTextFileLines(filePath);
            if (lines != null) {
                imageLinks.addAll(HtmlParser.getAllImageUrlsFromList(lines));
                audioLinks.addAll(HtmlParser.getAllAudioUrlsFromList(lines));
            }
        }

        try {
            Path basePath = Paths.get(Conf.SAVE_DIRECTORY);
            Files.write(basePath.resolve("image_links.txt"), imageLinks);
            Files.write(basePath.resolve("audio_links.txt"), audioLinks);
            System.out.println("Media links saved in " + Conf.SAVE_DIRECTORY);
        } catch (IOException e) {
            System.err.println("Error writing media link files: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Top 10 URLs:");
        printTopCountUrls(10);

        System.out.println("____________________");
        getTopUrls(10).forEach(System.out::println);

        System.out.println("Extracting image/audio links...");
        extractAndSaveMediaLinks();
    }
}
