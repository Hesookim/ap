package ap.exercises.ex5.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LinkFileManager {
    private static final String IMAGE_LINKS_FILE = "image_links.txt";
    private static final String AUDIO_LINKS_FILE = "audio_links.txt";

    public static void saveLinks(List<String> links, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, links, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void saveImageLinks(List<String> links) throws IOException {
        saveLinks(links, IMAGE_LINKS_FILE);
    }

    public static void saveAudioLinks(List<String> links) throws IOException {
        saveLinks(links, AUDIO_LINKS_FILE);
    }
}