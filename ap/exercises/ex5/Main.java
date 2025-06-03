package ap.exercises.ex5;

import ap.exercises.html.Conf;
import ap.exercises.html.DomainHtmlScraper;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String domainAddress = ap.exercises.html.Conf.DOMAIN_ADDRESS;
        String savePath = Conf.SAVE_DIRECTORY;

        ap.exercises.html.DomainHtmlScraper domainHtmlScraper = new DomainHtmlScraper(domainAddress, savePath);
        domainHtmlScraper.start();
    }
}

