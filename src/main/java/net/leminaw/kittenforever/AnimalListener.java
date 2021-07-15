package net.leminaw.kittenforever;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public class AnimalListener implements Listener
{
    private Plugin plugin;

    private Material stopGrowthItem;
    private Material stopGrowthItemAfter;
    private Material resumeGrowthItem;
    private Material resumeGrowthItemAfter;

    public AnimalListener(Plugin plugin)
    {
        this.plugin = plugin;

        stopGrowthItem = getMaterialFromConfig("items.stopGrowth.before");
        stopGrowthItemAfter = getMaterialFromConfig("items.stopGrowth.after");
        resumeGrowthItem = getMaterialFromConfig("items.resumeGrowth.before");
        resumeGrowthItemAfter = getMaterialFromConfig("items.resumeGrowth.after");
    }

    private Material getMaterialFromConfig(String node)
    {
        return Material.matchMaterial(plugin.config.getString(node));
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event)
    {
        final Entity clicked = event.getRightClicked();
        if (!(clicked instanceof Ageable)) return;
        
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final Ageable entity = (Ageable) clicked;
        final Location location = entity.getLocation();

        if (!entity.isAdult())
        {
            if (
                player.hasPermission("kittenforever.growth.stop")
                && inventory.getItemInMainHand().getType().equals(stopGrowthItem)
                && !((Breedable) entity).getAgeLock()
            ) {
                location.getWorld().spawnParticle(Particle.HEART, location, 20);
                ((Breedable) entity).setAgeLock(true);
                inventory.setItemInMainHand(new ItemStack(stopGrowthItemAfter));
                event.setCancelled(true);
            }

            else if (
                player.hasPermission("kittenforever.growth.resume")
                && inventory.getItemInMainHand().getType().equals(resumeGrowthItem)
                && ((Breedable) entity).getAgeLock()
            ) {
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 20);
                ((Breedable) entity).setAgeLock(false);
                inventory.setItemInMainHand(new ItemStack(resumeGrowthItemAfter));
                event.setCancelled(true);
            }
        }
    }
}
