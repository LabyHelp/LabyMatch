package de.labyhelp.addon.labymatch.utils;

import de.labyhelp.addon.labymatch.LabyMatch;
import net.labymod.main.LabyMod;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MatchManager {

    private final HashMap<String, String> match = new HashMap<String, String>();

    public String sendMatch() {
        try {
                final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/sendMatch.php?name=" + LabyMod.getInstance().getPlayerName() + "&uuid=" + LabyMod.getInstance().getPlayerUUID()).openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                con.setConnectTimeout(3000);
                con.setReadTimeout(3000);
                con.connect();
                return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch match request", e);
        }
    }

    public String sendQuery() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/query.php?name=" + LabyMod.getInstance().getPlayerName() + "&uuid=" + LabyMod.getInstance().getPlayerUUID()).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not fetch query request", e);
        }
    }

    public void readMatch(Boolean isMessage) {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/match.php?uuid=" + LabyMod.getInstance().getPlayerUUID() + "&name=" + LabyMod.getInstance().getPlayerName()).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            final String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
            final String[] entries;
            final String[] array;
            final String[] split = array = (entries = result.split(","));
            for (final String entry : array) {
                final String[] data = entry.split(":");
                if (data[0] != null ) {
                    if (data.length == 2) {
                        LabyMatch.getInstace().getMatchManager().getMatch().clear();
                        LabyMatch.getInstace().getMatchManager().getMatch().put(data[0], data[1]);
                        if (isMessage) {
                            sendMatchResponse();
                        }
                    }
                } else {
                    LabyMatch.getInstace().sendClientMessage(EnumChatFormatting.RED + "Error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read match informations!", e);
        }
    }

    public HashMap<String, String> getMatch() {
        return match;
    }

    public void sendMatchResponse() {
        if (LabyMatch.getInstace().getSettingsManager().getIsQuery()) {
            if (LabyMatch.getInstace().getSettingsManager().onServer) {
                for (Map.Entry<String, String> list : match.entrySet()) {
                    if (!list.getValue().startsWith("!")) {

                        if (!LabyMatch.getInstace().getSettingsManager().isAlready) {
                            LabyMatch.getInstace().getSettingsManager().isAlready = true;

                            StringSelection stringSelection = new StringSelection(list.getValue());
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(stringSelection, null);

                            LabyMatch.getInstace().sendClientMessage(EnumChatFormatting.WHITE + "You have an TeamPartner " + EnumChatFormatting.BOLD + list.getValue() + " (save in Clipboard)");
                            LabyMatch.getInstace().sendClientMessage(EnumChatFormatting.WHITE + "You can add the player as a LabyMod friend");
                            LabyMatch.getInstace().sendClientMessage(EnumChatFormatting.BOLD + "Or his Profile Page for Contact:" + EnumChatFormatting.GRAY + " https://labyhelp.de/profile?uuid=" + list.getKey() + "&name=" + list.getValue());
                        }
                    } else {
                        LabyMatch.getInstace().sendClientMessage(EnumChatFormatting.RED + "Error:" + list.getValue());
                    }
                }
            }
        }
    }
}



