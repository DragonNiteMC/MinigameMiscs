package com.ericlam.mc.minigamemiscs.pulse;

import com.ericlam.mc.minigamemiscs.api.PulseManager;
import com.ericlam.mc.minigamemiscs.config.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.List;

public class PulseListener implements Listener {
    private final List<String> disabledWorlds;
    private final MainConfig.PulseSetting pulseSetting;
    private final PulseManager pulseManager;

    public PulseListener(List<String> disabledWorlds, MainConfig.PulseSetting pulseSetting, PulseManager pulseManager) {
        this.disabledWorlds = disabledWorlds;
        this.pulseSetting = pulseSetting;
        this.pulseManager = pulseManager;
    }


    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e){
        if (disabledWorlds.contains(e.getEntity().getWorld().getName())) return;
        if (!(e.getEntity() instanceof Player)) return;
        var player = (Player) e.getEntity();
        if (pulseSetting.mode == MainConfig.MiscMode.HEALTH){
            if (pulseSetting.launch_health > player.getHealth()){
                if (!pulseManager.isPulsing(player)) pulseManager.startPulse(player);
            }
        }else if (pulseSetting.mode == MainConfig.MiscMode.DAMAGE){
            pulseManager.startPulse(player, pulseSetting.damage_duration);
        }
    }
}
