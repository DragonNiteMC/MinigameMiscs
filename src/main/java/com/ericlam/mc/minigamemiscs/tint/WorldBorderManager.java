package com.ericlam.mc.minigamemiscs.tint;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.ericlam.mc.minigamemiscs.config.MainConfig;
import com.ericlam.mc.minigamemiscs.packetwrapper.WrapperPlayServerWorldBorder;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public final class WorldBorderManager {

    private final MainConfig.TintSetting tintSetting;

    WorldBorderManager(MainConfig.TintSetting tintSetting) {
        this.tintSetting = tintSetting;
    }

    void sendBorder(Player p, int percentage) {
        percentage = (int) Math.round((double) percentage / (double) tintSetting.intensity_modifier);
        setBorder(p, percentage);
        if (tintSetting.fade_enabled) fadeBorder(p, percentage, tintSetting.fade_time);
    }

    void fadeBorder(Player p, int percentage, long time) {
        int dist = -10000 * percentage + 1300000;
        sendWorldBorderPacket(p, 0, 200000D, dist, (long) 1000 * time + 4000); //Add 4000 to make sure the "security" zone does not count in the fade time
    }

    void removeBorder(Player p) {
        removeWorldBorderPacket(p);
    }

    void setBorder(Player p, int percentage) {
        int dist = -10000 * percentage + 1300000;
        sendWorldBorderPacket(p, dist, 200000D, 200000D, 0);
    }

    private void sendWorldBorderPacket(Player p, int dist, double oldRadius, double newRadius, long delay) {
        var borderPacket = new WrapperPlayServerWorldBorder();
        borderPacket.setAction(EnumWrappers.WorldBorderAction.INITIALIZE);
        borderPacket.setPortalTeleportBoundary(29999984);
        borderPacket.setCenterX(p.getLocation().getX());
        borderPacket.setCenterZ(p.getLocation().getZ());
        borderPacket.setOldRadius(newRadius);
        borderPacket.setRadius(oldRadius);
        borderPacket.setSpeed(delay);
        borderPacket.setWarningTime(15);
        borderPacket.setWarningDistance(dist);
        borderPacket.sendPacket(p);
    }

    private void removeWorldBorderPacket(Player p) {
        var borderPacket = new WrapperPlayServerWorldBorder();
        WorldBorder wb = p.getWorld().getWorldBorder();
        borderPacket.setAction(EnumWrappers.WorldBorderAction.INITIALIZE);
        borderPacket.setPortalTeleportBoundary(29999984);
        borderPacket.setCenterX(p.getLocation().getX());
        borderPacket.setCenterZ(p.getLocation().getZ());
        borderPacket.setRadius(wb.getSize());
        borderPacket.setOldRadius(wb.getSize());
        borderPacket.setWarningDistance(p.getWorld().getWorldBorder().getWarningDistance());
        borderPacket.setWarningTime(0);
        borderPacket.setSpeed(0);
        borderPacket.sendPacket(p);
    }
}
