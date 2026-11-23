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

    public BasicCkgContainer(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabCkg.CKG_TAB);
    }

    public BasicCkgContainer() {
        this(Material.rock);
        this.setCreativeTab(CreativeTabCkg.CKG_TAB);
    }

    // Strip Minecraft's prefix before adding the mod namespace.
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    // Translation key format: tile.<namespace>:<block_name>.name
    @Override
    public String getUnlocalizedName() {
        return "tile.ckg:" + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.valueOf(getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    @Override
    abstract public TileEntity createNewTileEntity(World world, int meta);

}
