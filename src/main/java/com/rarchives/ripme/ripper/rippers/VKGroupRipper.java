package com.rarchives.ripme.ripper.rippers;

import com.rarchives.ripme.ripper.AbstractHTMLRipper;
import com.rarchives.ripme.utils.Http;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VKGroupRipper extends AbstractHTMLRipper {

    public VKGroupRipper(URL url) throws IOException {
        super(url);
    }

    @Override
    public String getHost() {
        return "VKGroup";
    }

    @Override
    public String getDomain() {
        return "vk.com";
    }

    @Override
    public boolean canRip(URL url) {
        String u = url.toExternalForm();
        return u.contains("/public");
    }

    @Override
    public String getGID(URL url) throws MalformedURLException {
        //https://vk.com/public69563163 \d+
        //Pattern p = Pattern.compile("\\d+");
        Pattern p = Pattern.compile("^https?://(www\\.)?vk\\.com/(public)-?([0-9_]+).*$");
        Matcher m = p.matcher(url.toExternalForm());
        if (m.matches()) {
            // Return the text contained between () in the regex
            System.out.println(m.group(1));
            return m.group(1);

        }
        throw new MalformedURLException("Expected imgur.com URL format: " +
                "imgur.com/a/albumid - got " + url + " instead");
    }

    @Override
    public Document getFirstPage() throws IOException {
        // "url" is an instance field of the superclass
        return Http.url(url).get();
    }

    @Override
    public List<String> getURLsFromPage(Document doc) {
        List<String> result = new ArrayList<String>();
        for (Element el : doc.select("img")) {
            result.add(el.attr("src"));
        }
        return result;
    }

    @Override
    public void downloadURL(URL url, int index) {
        addURLToDownload(url, getPrefix(index));
    }
}
