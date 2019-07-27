package net.timelegacy.tlbuild.events;

import java.util.UUID;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;

public class IllegalItemListener implements Listener {

  @EventHandler
  public void onInventoryItemClick(InventoryCreativeEvent event) {
    event.setCurrentItem(removeNBT(event.getCurrentItem()));
  }

  private ItemStack removeNBT(ItemStack item) {
    net.minecraft.server.v1_13_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

    NBTTagCompound compound = nmsStack.getTag();
    if (compound == null) {
      compound = new NBTTagCompound();
      nmsStack.setTag(compound);
      compound = nmsStack.getTag();
    }

    NBTTagCompound skullOwner = new NBTTagCompound();
    skullOwner.set("Id", new NBTTagString(UUID.randomUUID().toString()));
    NBTTagCompound properties = new NBTTagCompound();
    NBTTagList textures = new NBTTagList();
    NBTTagCompound value = new NBTTagCompound();
    textures.add(value);
    properties.set("textures", textures);
    skullOwner.set("Properties", properties);

    compound.set("SkullOwner", skullOwner);
    nmsStack.setTag(compound);

    return CraftItemStack.asBukkitCopy(nmsStack);
  }

}
