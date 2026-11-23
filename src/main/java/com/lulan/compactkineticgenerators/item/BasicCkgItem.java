package com.lulan.compactkineticgenerators.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.lulan.compactkineticgenerators.creativetab.CreativeTabCkg;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicCkgItem extends Item {

    public BasicCkgItem() {
        super();
        this.setCreativeTab(CreativeTabCkg.CKG_TAB);
    }

    // Strip Minecraft's prefix before adding the mod namespace.
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    // Add the mod namespace for use in language files.
    // Translation key format: item.<namespace>:<item_name>.name
    @Override
    public String getUnlocalizedName() {
        return "item." + Reference.MOD_ID + ":" + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    // ItemStack overload of getUnlocalizedName().
    // Positive metadata is appended to the item name in the translation key.
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        String name = "item." + Reference.MOD_ID + ":" + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
        return meta > 0 ? name + meta : name;
    }

    // Item icon registration.
    // Pass the item name without the item. prefix to the icon registry.
    // Icons are only registered on the client.
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String name = this.getUnlocalizedName();
        itemIcon = iconRegister.registerIcon(name.substring(name.indexOf(".") + 1));
    }

}
