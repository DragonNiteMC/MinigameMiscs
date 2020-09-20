package com.ericlam.mc.minigamemiscs.api;

import org.bukkit.entity.Player;

public interface TintManager {

    void setTint(Player p, int percentage);

    void fadeTint(Player p, int startpercentage, int timeInSeconds);

    void removeTint(Player p);

    void sendTint(Player p, int percentage);

    boolean isTint(Player p);

}
