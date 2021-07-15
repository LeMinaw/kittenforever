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
    Plugin plugin;

    public AnimalListener(Plugin plugin) {
        this.plugin = plugin;
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

        if (!entity.isAdult()) {
            if (
                isAllowed(player, "kittenforever.growth.stop")
                && inventory.getItemInMainHand().getType().equals(Material.MILK_BUCKET)
                && !((Breedable) entity).getAgeLock()
            ) {
                location.getWorld().spawnParticle(Particle.HEART, location, 20);
                ((Breedable) entity).setAgeLock(true);
                inventory.setItemInMainHand(new ItemStack(Material.BUCKET));
                event.setCancelled(true);
            }

            else if (
                isAllowed(player, "kittenforever.growth.resume")
                && inventory.getItemInMainHand().getType().equals(Material.MUSHROOM_STEW)
                && ((Breedable) entity).getAgeLock()
            ) {
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 20);
                ((Breedable) entity).setAgeLock(false);
                inventory.setItemInMainHand(new ItemStack(Material.BOWL));
                event.setCancelled(true);
            }
        }
    }

    private boolean isAllowed(Player player, String node) {
        return (
            (plugin.getConfig().getBoolean("allowOPs") && player.isOp())
            || player.hasPermission(node)
        );
    }
}
