package com.lulan.compactkineticgenerators.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.compactkineticgenerators.creativetab.CreativeTabCkg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

abstract public class BasicCkgContainer extends BlockContainer {

    // Create a block with the specified material.
    public BasicCkgContainer(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabCkg.CKG_TAB); // Add to the creative tab.
    }

    // Default to rock if no material is specified.
    public BasicCkgContainer() {
        this(Material.rock);
        this.setCreativeTab(CreativeTabCkg.CKG_TAB); // Add to the creative tab.
    }

    // Strip Minecraft's prefix before adding the mod namespace.
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    // Add the mod namespace for use in language files.
    // Translation key format: tile.<namespace>:<block_name>.name
    @Override
    public String getUnlocalizedName() {
        return "tile.ckg:" + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    // Block icon registration.
    // Pass the block name without the tile. prefix to the icon registry.
    // Icons are only registered on the client.
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.valueOf(getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    @Override
    abstract public TileEntity createNewTileEntity(World world, int meta);

}
