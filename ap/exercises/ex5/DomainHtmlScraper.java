package ap.exercises.ex5;

import ap.exercises.html.HtmlFetcher;
import ap.exercises.html.HtmlFileManager;
import ap.exercises.html.HtmlParser;

import java.io.IOException;
import java.util.*;

public class DomainHtmlScraper {
    private String domainAddress;
    private Queue<String> queue;
    private HtmlFileManager htmlFileManager;
    private Set<String> visitedUrls;
    private Set<String> processedMediaUrls;

    public DomainHtmlScraper(String domainAddress, String savePath) {
        this.domainAddress = domainAddress;
        this.queue = new LinkedList<>();
        this.htmlFileManager=new HtmlFileManager(savePath);
        this.visitedUrls = new HashSet<>();
        this.processedMediaUrls = new HashSet<>();
    }

    public void start() throws IOException {
        List<String> htmlLines = HtmlFetcher.fetchHtml(domainAddress);
        processPage(htmlLines, domainAddress);

        int counter=1;
        while (!queue.isEmpty()){
            String url = queue.remove();

            if (visitedUrls.contains(url)) {
                continue;
            }
            visitedUrls.add(url);

            try {
                htmlLines = HtmlFetcher.fetchHtml(url);
                processPage(htmlLines, url);
            }
            catch (Exception e){
                System.out.println("ERROR: "+url+"\t -> "+e.getMessage());
            }
            System.out.println("[" + counter++ + "] " + url + " processed (queue size:" + queue.size() + ")");
        }
        System.out.println("Operation complete");
    }

    private void processPage(List<String> htmlLines, String currentUrl) throws IOException {
        this.htmlFileManager.save(htmlLines);

        List<String> imageUrls = HtmlParser.getAllImageUrlsFromList(htmlLines);
        List<String> audioUrls = HtmlParser.getAllAudioUrlsFromList(htmlLines);

        saveMediaLinks(imageUrls, "image_links.txt");
        saveMediaLinks(audioUrls, "audio_links.txt");

        List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
        for (String url : urls) {
            if (!visitedUrls.contains(url) && !queue.contains(url)) {
                queue.add(url);
            }
        }
    }

    private void saveMediaLinks(List<String> mediaUrls, String filename) throws IOException {
        try (java.io.FileWriter writer = new java.io.FileWriter(filename, true)) {
            for (String url : mediaUrls) {
                if (!processedMediaUrls.contains(url)) {
                    writer.write(url + System.lineSeparator());
                    processedMediaUrls.add(url);
                }
            }
        }
    }
}