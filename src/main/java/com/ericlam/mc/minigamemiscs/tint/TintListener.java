package com.ericlam.mc.minigamemiscs.tint;

import com.ericlam.mc.minigamemiscs.api.TintManager;
import com.ericlam.mc.minigamemiscs.config.MainConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.List;
import java.util.Optional;

public class TintListener implements Listener {

    private final MainConfig.TintSetting tintSetting;
    private final List<String> disabledWorlds;
    private final TintManager tintManager;

    public TintListener(MainConfig.TintSetting tintSetting, TintManager tintManager, List<String> disabledWorlds) {
        this.tintSetting = tintSetting;
        this.tintManager = tintManager;
        this.disabledWorlds = disabledWorlds;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if (disabledWorlds.contains(e.getEntity().getWorld().getName())) return;
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        var health = player.getHealth();
        double maxHealth = Optional.ofNullable(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).map(AttributeInstance::getBaseValue).orElse(20.0);
        if (tintSetting.mode == MainConfig.MiscMode.HEALTH) {
            if (tintSetting.launch_health > health) {
                tintManager.setTint(player, 100 - (int) ((health / maxHealth) * 100));
            }
        } else if (tintSetting.mode == MainConfig.MiscMode.DAMAGE) {
            health = e.getDamage();
            tintManager.sendTint(player, (int) (health / maxHealth));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onHeal(EntityRegainHealthEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        double heal = e.getAmount();
        int health = (int) (p.getHealth() + heal);
        if (health > tintSetting.launch_health){
            if (tintManager.isTint(p)){
                tintManager.removeTint(p);
                tintManager.sendTint(p, health);
            }

        }
    }
}
