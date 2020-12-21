package de.marvhuelsmann.labymatch.utils;

import net.minecraft.util.EnumChatFormatting;

public class SettingsManager {

    public final String messagePrefix = EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.LIGHT_PURPLE + "LabyMatch" + EnumChatFormatting.DARK_GRAY + "] " + EnumChatFormatting.GRAY;
    public boolean serverResponding = false;
    public Boolean joinMessage = false;

    public boolean onServer = false;

    public boolean isServerResponding() {
        return serverResponding;
    }

    public String currentVersion = "1.0";
    public Boolean isNewerVersion = false;
    public boolean isNewerVersion() {
        return isNewerVersion;
    }


    /* playerInformations */

    public String gender;
    public String intress;
    public String age;

    public String getGender() {
        return gender;
    }

    public String getIntress() {
        return intress;
    }

    public String getAge() {
        return age;
    }

    public boolean isQuering = false;
    public boolean getIsQuery() {
        return isQuering;
    }




}
