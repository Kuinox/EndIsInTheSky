package com.kuinox.endisinthesky;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Enabled");
        getServer().getPluginManager().registerEvents(new MyListener(getLogger()), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }
}

class MyListener implements Listener {

    private Logger logger;

    MyListener(Logger logger) {

        this.logger = logger;
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Vector velocity = e.getPlayer().getVelocity().clone();
        float flySpeed = e.getPlayer().getFlySpeed();

        if(e.getTo().getY() < -20 || e.getTo().getY() > 512) {
            boolean teleported = false;
            if(e.getFrom().getWorld().getEnvironment() == World.Environment.THE_END && e.getTo().getY() < -20) {
                World normalWorld = Bukkit.getWorlds().stream().filter(world -> world.getEnvironment() == World.Environment.NORMAL).findFirst().get();
                e.getPlayer().teleport(new Location(normalWorld, e.getTo().getX(), 350, e.getTo().getZ(),  e.getTo().getYaw(), e.getTo().getPitch()));
                teleported = true;
            }
            if(e.getFrom().getWorld().getEnvironment() == World.Environment.NORMAL && e.getTo().getY() > 512) {
                World endWorld = Bukkit.getWorlds().stream().filter(world -> world.getEnvironment() == World.Environment.THE_END).findFirst().get();
                e.getPlayer().teleport(new Location(endWorld, e.getTo().getX(), 0.0, e.getTo().getZ(), e.getTo().getYaw(), e.getTo().getPitch() ));
                teleported = true;
            }
            if(teleported) {
                e.getPlayer().setFlySpeed(flySpeed);
                e.getPlayer().setVelocity(velocity);
            }
        }
    }
}
