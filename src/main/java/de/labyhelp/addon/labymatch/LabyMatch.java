package de.labyhelp.addon.labymatch;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.labymatch.enums.GenderEnum;
import de.labyhelp.addon.labymatch.enums.IntressEnum;
import de.labyhelp.addon.labymatch.enums.ModuleEnum;
import de.labyhelp.addon.labymatch.listener.ClientJoinListener;
import de.labyhelp.addon.labymatch.listener.ClientQuitListener;
import de.labyhelp.addon.labymatch.listener.ClientTickListener;
import de.labyhelp.addon.labymatch.listener.MessageSendListener;
import de.labyhelp.addon.labymatch.utils.MatchManager;
import de.labyhelp.addon.labymatch.utils.PlayerHandler;
import de.labyhelp.addon.labymatch.utils.SettingsManager;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.main.lang.LanguageManager;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LabyMatch extends LabyModAddon {

    private static LabyMatch instace;

    private final MatchManager matchManager = new MatchManager();
    private final SettingsManager settingsManager = new SettingsManager();
    private final PlayerHandler playerHandler = new PlayerHandler();

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    @Override
    public void onEnable() {
        instace = this;
        try {
            String webVersion = readVersion();

            LabyMatch.getInstace().getSettingsManager().currentVersion = webVersion;
            if (!webVersion.equalsIgnoreCase(LabyMatch.getInstace().getSettingsManager().currentVersion)) {
                LabyMatch.getInstace().getSettingsManager().isNewerVersion = true;
            }
            LabyMatch.getInstace().getSettingsManager().serverResponding = true;
        } catch (Exception ignored) {
            LabyMatch.getInstace().getSettingsManager().serverResponding = false;
        }

        System.out.println("Loading Listeners");
        this.getApi().registerForgeListener(new ClientTickListener());
        this.getApi().getEventManager().registerOnJoin(new ClientJoinListener());
        this.getApi().getEventManager().registerOnQuit(new ClientQuitListener());
        this.getApi().getEventManager().register(new MessageSendListener());


        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LabyMatch.getInstace().getPlayerHandler().quitMatch();
            }
        }));
    }

    @Override
    public void loadConfig() {
        LabyMatch.getInstace().getSettingsManager().isQuering = !this.getConfig().has("query") || this.getConfig().get("query").getAsBoolean();
        LabyMatch.getInstace().getSettingsManager().joinMessage = !this.getConfig().has("join") || this.getConfig().get("join").getAsBoolean();

        LabyMatch.getInstace().getSettingsManager().gender = this.getConfig().has("gender") ? this.getConfig().get("gender").getAsString() : "gender";
        LabyMatch.getInstace().getSettingsManager().intress = this.getConfig().has("intress") ? this.getConfig().get("intress").getAsString() : "intress";
        LabyMatch.getInstace().getSettingsManager().age = this.getConfig().has("age") ? this.getConfig().get("age").getAsString() : "age";
    }

    public static LabyMatch getInstace() {
        return instace;
    }

    public void sendClientMessage(String message) {
        LabyMod.getInstance().displayMessageInChat(LabyMatch.getInstace().getSettingsManager().messagePrefix + message);
    }

    public void sendClientMessageDefault(String message) {
        LabyMod.getInstance().displayMessageInChat(message);
    }

    public ExecutorService getExecutor() {
        return threadPool;
    }

    public MatchManager getMatchManager() {
        return matchManager;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public String readVersion() {
        try {
            final HttpURLConnection con = (HttpURLConnection) new URL("https://marvhuelsmann.de/labymatch.php").openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            con.connect();
            return IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read version!", e);
        }
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

        list.add((SettingsElement) new HeaderElement("§fLabyMatch will not share your personal information."));
        list.add((SettingsElement) new HeaderElement("§fThe data will only be used to find a better match partner"));
        list.add((SettingsElement) new HeaderElement(" "));

        final BooleanElement enabled = new BooleanElement("Searching?", new ControlElement.IconData(Material.LEVER), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyMatch.getInstace().getSettingsManager().isQuering = enable;


                LabyMatch.this.getConfig().addProperty("query", enable);
                LabyMatch.this.saveConfig();
            }
        }, LabyMatch.getInstace().getSettingsManager().getIsQuery());

        final BooleanElement join = new BooleanElement("Join Messages", new ControlElement.IconData(Material.REDSTONE), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enable) {
                LabyMatch.getInstace().getSettingsManager().joinMessage = enable;


                LabyMatch.this.getConfig().addProperty("join", enable);
                LabyMatch.this.saveConfig();
            }
        }, LabyMatch.getInstace().getSettingsManager().getIsQuery());

        final DropDownMenu<ModuleEnum> alignmentDropDownMenu = new DropDownMenu<ModuleEnum>("How old are you?" /* Display name */, 0, 0, 0, 0)
                .fill(ModuleEnum.values());
        DropDownElement<ModuleEnum> alignmentDropDown = new DropDownElement<ModuleEnum>("Your Age:", alignmentDropDownMenu);


        alignmentDropDownMenu.setSelected(ModuleEnum.JOUNG_TO_12);


        alignmentDropDown.setChangeListener(new Consumer<ModuleEnum>() {
            @Override
            public void accept(ModuleEnum alignment) {
                LabyMatch.getInstace().getSettingsManager().age = alignment.name();
                LabyMatch.getInstace().getPlayerHandler().sendAge(LabyMod.getInstance().getPlayerUUID(), alignment.name());

                LabyMatch.this.getConfig().addProperty("age", alignment.name());
                LabyMatch.this.saveConfig();
            }
        });

        alignmentDropDownMenu.setEntryDrawer(new DropDownMenu.DropDownEntryDrawer() {
            @Override
            public void draw(Object object, int x, int y, String trimmedEntry) {
                String entry = object.toString().toLowerCase();
                LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);

            }
        });


        final DropDownMenu<GenderEnum> gamesmenu = new DropDownMenu<GenderEnum>("Whats your Gender?" /* Display name */, 0, 0, 0, 0)
                .fill(GenderEnum.values());
        DropDownElement<GenderEnum> games = new DropDownElement<GenderEnum>("Gender:", gamesmenu);

        gamesmenu.setSelected(GenderEnum.MALE);

        games.setChangeListener(new Consumer<GenderEnum>() {
            @Override
            public void accept(GenderEnum alignment) {
                LabyMatch.getInstace().getSettingsManager().gender = alignment.name();
                LabyMatch.getInstace().getPlayerHandler().sendGender(LabyMod.getInstance().getPlayerUUID(), alignment.name());

                LabyMatch.this.getConfig().addProperty("gender", alignment.name());
                LabyMatch.this.saveConfig();
            }
        });

        gamesmenu.setEntryDrawer(new DropDownMenu.DropDownEntryDrawer() {
            @Override
            public void draw(Object object, int x, int y, String trimmedEntry) {
                // We translate the value and draw it
                String entry = object.toString().toLowerCase();
                LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);
            }
        });


        final DropDownMenu<IntressEnum> intressMenu = new DropDownMenu<IntressEnum>("Where are you Interested in?" /* Display name */, 0, 0, 0, 0)
                .fill(IntressEnum.values());
        DropDownElement<IntressEnum> intress = new DropDownElement<IntressEnum>("Intrested in:", intressMenu);

        intressMenu.setSelected(IntressEnum.COMMUNITY);

        intress.setChangeListener(new Consumer<IntressEnum>() {
            @Override
            public void accept(IntressEnum alignment) {
                LabyMatch.getInstace().getSettingsManager().intress = alignment.name();
                LabyMatch.getInstace().getPlayerHandler().sendIntress(LabyMod.getInstance().getPlayerUUID(), alignment.name());

                LabyMatch.this.getConfig().addProperty("intress", alignment.name());
                LabyMatch.this.saveConfig();

            }
        });

        intressMenu.setEntryDrawer(new DropDownMenu.DropDownEntryDrawer() {
            @Override
            public void draw(Object object, int x, int y, String trimmedEntry) {
                // We translate the value and draw it
                String entry = object.toString().toLowerCase();
                LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);
            }
        });

        list.add(enabled);
        list.add(join);
        list.add(intress);
        list.add(games);
        list.add(alignmentDropDown);
        list.add((SettingsElement) new HeaderElement(" "));
        list.add((SettingsElement) new HeaderElement("§cIf you enter incorrect information, you can be banned."));
        list.add((SettingsElement) new HeaderElement("§fDiscord: §lhttps://labyhelp.de/discord"));
    }
}
