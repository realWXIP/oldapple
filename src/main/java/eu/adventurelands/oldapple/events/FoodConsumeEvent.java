package eu.adventurelands.oldapple.events;

import eu.adventurelands.oldapple.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FoodConsumeEvent implements Listener {
    private final Main plugin;

    public FoodConsumeEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack food = event.getItem();
        String world = player.getWorld().getName();

        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
        if (food.getData().equals(apple.getData())) {
            event.setCancelled(true);

            if (plugin.getConfig().getString("worlds." + world).contains(world)) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);

                for (String effects : plugin.getConfig().getConfigurationSection("worlds." + world + ".effects").getKeys(false)) {
                    int time = plugin.getConfig().getInt("worlds." + world + ".effects." + effects + ".time");
                    int amplifier = plugin.getConfig().getInt("worlds." + world + ".effects." + effects + ".level");

                    if (player.getFoodLevel() < 20.0) {
                        player.setFoodLevel(player.getFoodLevel() + 4);
                    }

                    if (player.getSaturation() < 20.0) {
                        player.setSaturation((float) (player.getSaturation() + 9.60));
                    }

                    player.removePotionEffect(PotionEffectType.getByName(effects));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effects), time*20, amplifier));
                }
            }
        }
    }
}
