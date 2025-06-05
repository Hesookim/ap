package ap.exercises.ex5;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String domainAddress = ap.exercises.ex5.Conf.DOMAIN_ADDRESS;
        String savePath = Conf.SAVE_DIRECTORY;

        ap.exercises.ex5.DomainHtmlScraper domainHtmlScraper = new DomainHtmlScraper(domainAddress, savePath);
        domainHtmlScraper.start();

        System.out.println("Extracting image/audio links...");
        ap.exercises.ex5.Abalyzer.HtmlAnalyzer.extractAndSaveMediaLinks();
    }
}