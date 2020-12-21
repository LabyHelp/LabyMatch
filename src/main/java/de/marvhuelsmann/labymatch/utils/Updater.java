package de.marvhuelsmann.labymatch.utils;

import net.labymod.addon.AddonLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Updater {

    public static final URL DOWNLOAD_URL;

    static {
        URL url = null;
        try {
            url = new URL("https://drive.google.com/u/0/uc?id=1p_wvtLs0ikwX_ajplBjoeRj_nMnms27F&export=download");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DOWNLOAD_URL = url;
    }

    public void update() {
        File addonDir = AddonLoader.getAddonsDirectory();
        File addon = new File(addonDir, "LabyMatch.jar");

        try {
            FileUtils.copyURLToFile(DOWNLOAD_URL, addon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
