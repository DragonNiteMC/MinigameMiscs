package com.ericlam.mc.minigamemiscs.tint;

import com.ericlam.mc.minigamemiscs.api.TintManager;
import com.ericlam.mc.minigamemiscs.config.MainConfig;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public final class TintManagerImpl implements TintManager {

    private final WorldBorderManager worldBorderManager;
    private final Set<Player> tiningForever = new HashSet<>();

    public TintManagerImpl(MainConfig.TintSetting tintSetting) {
        this.worldBorderManager = new WorldBorderManager(tintSetting);
    }

    @Override
    public void setTint(Player p, int percentage) {
        worldBorderManager.setBorder(p, 100 - percentage);
        this.tiningForever.add(p.getPlayer());
    }

    @Override
    public void fadeTint(Player p, int startpercentage, int timeInSeconds) {
        worldBorderManager.fadeBorder(p, 100 - startpercentage, timeInSeconds);
    }

    @Override
    public void removeTint(Player p) {
        if (!this.isTint(p)) return;
        worldBorderManager.removeBorder(p);
        this.tiningForever.remove(p.getPlayer());
    }

    @Override
    public void sendTint(Player p, int percentage) {
        worldBorderManager.sendBorder(p, 100 - percentage);
    }

    @Override
    public boolean isTint(Player p) {
        return tiningForever.contains(p);
    }


}
