package de.marvhuelsmann.labymatch.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PlayerHandler {

    public String sendAge(final UUID uuid, String age) {
        try {
            if (uuid != null) {

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendAge.php?name=" + age + "&uuid=" + uuid.toString()).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch age", e);
        }
    }

    public String sendGender(final UUID uuid, String gender) {
        try {
            if (uuid != null) {

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendGender.php?name=" + gender + "&uuid=" + uuid.toString()).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch gender", e);
        }
    }

    public String sendIntress(final UUID uuid, String intress) {
        try {
            if (uuid != null) {

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendIntress.php?name=" + intress + "&uuid=" + uuid.toString()).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch intress", e);
        }
    }


    public String sendMatchOffline(final UUID uuid) {
        try {
            if (uuid != null) {

                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendMatchOffline.php?&uuid=" + uuid.toString()).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch intress", e);
        }
    }


}
