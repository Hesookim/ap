package ap.exercises.ex5;

import ap.exercises.ex5.utils.LinkFileManager;
import ap.exercises.ex5.fetcher.HtmlFetcher;
import ap.exercises.ex5.store.HtmlFileManager;
import ap.exercises.ex5.parser.HtmlParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DomainHtmlScraper {
    private String domainAddress;
    private String domainHost;
    private Queue<String> queue;
    private HtmlFileManager htmlFileManager;
    private Set<String> visitedUrls;
    private Set<String> processedImageUrls;
    private Set<String> processedAudioUrls;

    public DomainHtmlScraper(String domainAddress, String savePath) throws IOException {
        this.domainAddress = domainAddress;
        this.domainHost = getDomainHost(domainAddress);
        this.queue = new LinkedList<>();
        this.htmlFileManager = new HtmlFileManager(savePath + "/html");
        this.visitedUrls = new HashSet<>();
        this.processedImageUrls = new HashSet<>();
        this.processedAudioUrls = new HashSet<>();
        Files.createDirectories(Paths.get(savePath, "html"));
        Files.createDirectories(Paths.get(savePath, "image"));
        Files.createDirectories(Paths.get(savePath, "song"));
    }

    public void start() throws IOException {
        List<String> htmlLines = HtmlFetcher.fetchHtml(domainAddress);
        processPage(htmlLines, domainAddress);

        int counter = 1;
        while (!queue.isEmpty()) {
            String url = queue.remove();
            if (visitedUrls.contains(url) || !isInDomain(url)) continue;

            try {
                Thread.sleep(Conf.DOWNLOAD_DELAY_MS); // Added delay
                htmlLines = HtmlFetcher.fetchHtml(url);
                processPage(htmlLines, url);
            } catch (Exception e) {
                System.out.println("ERROR: " + url + " -> " + e.getMessage());
            }
            System.out.println("[" + counter++ + "] " + url + " processed (queue size:" + queue.size() + ")");
        }
        downloadMediaFiles(processedImageUrls, "images");
        downloadMediaFiles(processedAudioUrls, "audio");

        System.out.println("Operation complete");
    }

    private void processPage(List<String> htmlLines, String currentUrl) throws IOException {
        saveHtmlWithPath(htmlLines, currentUrl);

        List<String> imageUrls = HtmlParser.getAllImageUrlsFromList(htmlLines);
        List<String> audioUrls = HtmlParser.getAllAudioUrlsFromList(htmlLines);

        saveNewImageLinks(imageUrls);
        saveNewAudioLinks(audioUrls);

        List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
        for (String url : urls) {
            if (isInDomain(url) && !visitedUrls.contains(url) && !queue.contains(url)) {
                queue.add(url);
            }
        }
    }

    private boolean isInDomain(String url) {
        try {
            URL netUrl = new URL(url);
            String host = netUrl.getHost();
            return host.equals(domainHost) || host.endsWith("." + domainHost);
        } catch (Exception e) {
            return false;
        }
    }

    private String getDomainHost(String url) {
        try {
            URL netUrl = new URL(url);
            return netUrl.getHost();
        } catch (Exception e) {
            return "";
        }
    }

    private void saveHtmlWithPath(List<String> htmlLines, String url) throws IOException {
        URL netUrl = new URL(url);
        String host = netUrl.getHost();
        String path = netUrl.getPath();
        String domainPart = domainHost;
        String subdomainFolder = getSubdomainFolder(host, domainPart);

        if (path.isEmpty() || path.equals("/")) {
            path = "index.html";
        } else if (path.endsWith("/")) {
            path += "index.html";
        } else if (!path.contains(".")) {
            path += "/index.html";
        }
        if (path.startsWith("/")) path = path.substring(1);

        String savePathStr = htmlFileManager.getBasePath();
        if (!subdomainFolder.isEmpty()) {
            savePathStr += "/" + subdomainFolder;
        }
        savePathStr += "/" + path;

        Path savePath = Paths.get(savePathStr);
        Files.createDirectories(savePath.getParent());
        Files.write(savePath, htmlLines);
    }

    private String getSubdomainFolder(String host, String domainPart) {
        if (host.equals(domainPart)) {
            return "";
        }
        if (host.endsWith(domainPart)) {
            int endIndex = host.length() - domainPart.length();
            String subdomain = host.substring(0, endIndex);
            if (subdomain.endsWith(".")) {
                subdomain = subdomain.substring(0, subdomain.length() - 1);
            }
            return "_" + subdomain;
        }
        return "";
    }

    private void saveNewImageLinks(List<String> imageUrls) throws IOException {
        List<String> newUrls = new ArrayList<>();
        for (String url : imageUrls) {
            if (!processedImageUrls.contains(url)) {
                newUrls.add(url);
            }
        }
        if (!newUrls.isEmpty()) {
            LinkFileManager.saveImageLinks(newUrls);
            processedImageUrls.addAll(newUrls);
        }
    }

    private void saveNewAudioLinks(List<String> audioUrls) throws IOException {
        List<String> newUrls = new ArrayList<>();
        for (String url : audioUrls) {
            if (!processedAudioUrls.contains(url)) {
                newUrls.add(url);
            }
        }
        if (!newUrls.isEmpty()) {
            LinkFileManager.saveAudioLinks(newUrls);
            processedAudioUrls.addAll(newUrls);
        }
    }

    private void downloadMediaFiles(Set<String> mediaUrls, String mediaType) {
        File downloadDir = new File(htmlFileManager.getBasePath(), mediaType);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        for (String mediaUrl : mediaUrls) {
            try {
                URL url = new URL(mediaUrl);
                String fileName = new File(url.getPath()).getName();
                File outputFile = new File(downloadDir, fileName);

                if (outputFile.exists()) {
                    continue;
                }

                InputStream in = url.openStream();
                FileOutputStream out = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                out.close();
                in.close();

                System.out.println("Downloaded: " + fileName);
            } catch (Exception e) {
                System.err.println("Error downloading " + mediaUrl + ": " + e.getMessage());
            }
        }
    }
}