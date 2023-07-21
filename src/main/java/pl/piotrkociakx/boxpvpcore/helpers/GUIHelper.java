package pl.piotrkociakx.boxpvpcore.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.piotrkociakx.boxpvpcore.helpers.ChatHelper.colored;

public class GUIHelper {

    public static ItemStack createGUIItem(Material material, String name, List<String> lore, Map<Enchantment, Integer> enchantments) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(colored(name));
        meta.setLore(colored(lore));
        item.setItemMeta(meta);

        if (enchantments != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
            }
        }

        return item;
    }

    public static Inventory createGUI(String title, int size) {
        return Bukkit.createInventory(new GUIHolder(), size, colored(title));
    }

    public static void setGUIItem(Inventory inventory, int slot, ItemStack item, GUIAction leftClickAction, GUIAction rightClickAction) {
        inventory.setItem(slot, item);

        GUIHolder holder = (GUIHolder) inventory.getHolder();
        holder.setLeftClickAction(slot, leftClickAction);
        holder.setRightClickAction(slot, rightClickAction);
    }

    public static void fillEmptySlots(Inventory inventory, ItemStack item, boolean locked) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack current = inventory.getItem(i);
            if (current == null || current.getType() == Material.AIR) {
                inventory.setItem(i, item);
                if (locked) {
                    inventory.getItem(i).setAmount(0);
                }
            }
        }
    }

    public interface GUIAction {
        void execute(InventoryClickEvent event);
    }

    public static class GUIHolder implements InventoryHolder, Listener {

        private final Map<Integer, GUIAction> leftClickActions = new HashMap<>();
        private final Map<Integer, GUIAction> rightClickActions = new HashMap<>();

        @Override
        public Inventory getInventory() {
            return null;
        }

        public void setLeftClickAction(int slot, GUIAction action) {
            leftClickActions.put(slot, action);
        }

        public void setRightClickAction(int slot, GUIAction action) {
            rightClickActions.put(slot, action);
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            if (event.getInventory().getHolder() instanceof GUIHolder) {
                event.setCancelled(true);

                int slot = event.getRawSlot();
                if (slot >= 0 && slot < event.getInventory().getSize()) {
                    if (event.isLeftClick()) {
                        GUIAction leftClickAction = leftClickActions.get(slot);
                        if (leftClickAction != null) {
                            leftClickAction.execute(event);
                        }
                    } else if (event.isRightClick()) {
                        GUIAction rightClickAction = rightClickActions.get(slot);
                        if (rightClickAction != null) {
                            rightClickAction.execute(event);
                        }
                    }
                }
            }
        }
    }
}
