package pl.piotrkociakx.boxpvpcore.events;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.piotrkociakx.boxpvpcore.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.BlockBreakEvent;
public class inventorydrop implements Listener {
    private boolean inventorydropEnabled;

    public inventorydrop(ConfigManager configManager) {
        inventorydropEnabled = configManager.getBoolean("settings.enabled.inventorydrop.enabled");
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (inventorydropEnabled){
            if (player.hasPermission("boxpvpcore.droptoinventory.enabled")) {
                ItemStack blockItem = new ItemStack(event.getBlock().getType());
                Inventory playerInventory = player.getInventory();
                if (playerInventory.firstEmpty() == -1) {
                    event.setDropItems(false);
                    player.getWorld().dropItemNaturally(event.getBlock().getLocation(), blockItem);
                } else {
                    playerInventory.addItem(blockItem);
                }
            } else {
                return;
            }
        } else {
            return;
        }


    }
}
