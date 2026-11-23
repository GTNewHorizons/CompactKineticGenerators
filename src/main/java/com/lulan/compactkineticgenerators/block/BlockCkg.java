package com.lulan.compactkineticgenerators.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.lulan.compactkineticgenerators.CompactKineticGenerators;
import com.lulan.compactkineticgenerators.reference.Reference;
import com.lulan.compactkineticgenerators.tileentity.ExtremeKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.ExtremeWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.ExtremeWindKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.HighKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.HighWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.HighWindKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.KineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.LowKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.LowWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.LowWindKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.MediumKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.MediumWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.MediumWindKineticGeneratorTileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.IHasGui;

/**
 * meta:
 * 0:Wind L, 1:Wind M, 2:Wind H, 3:Wind E
 * 4:Water L, 5:Water M, 6:Water H, 7:Water E
 * 8:KineticGenerator L, 9:KineticGenerator M, 10:KineticGenerator H, 11:KineticGenerator E
 */
public class BlockCkg extends BasicCkgContainer {

    // [face][side], iconID: 6:head, 7:tail, 8:side, 9:top, 10:btm (0~5 save for vanilla icon method)
    public static final int[][] face2iconID = { { 6, 7, 8, 8, 8, 8 }, // face btm(0)
        { 7, 6, 8, 8, 8, 8 }, // face top(1)
        { 10, 9, 6, 7, 8, 8 }, // face N(2)
        { 10, 9, 7, 6, 8, 8 }, // face S(3)
        { 10, 9, 8, 8, 6, 7 }, // face W(4)
        { 10, 9, 8, 8, 7, 6 } };// face E(5)
    IIcon[] icons = new IIcon[22];

    public BlockCkg() {
        super(Material.iron);
        this.setBlockName("BlockCkg");
        this.setStepSound(soundTypeMetal);
        this.setHardness(3F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        icons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmLH");
        icons[1] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmLT");
        icons[2] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmLS");
        icons[3] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmMH");
        icons[4] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmMT");
        icons[5] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmMS");
        icons[6] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmHH");
        icons[7] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmHT");
        icons[8] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmHS");
        icons[9] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmEH");
        icons[10] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmET");
        icons[11] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmES");
        icons[12] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmT");
        icons[13] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwmB");
        icons[14] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwaL");
        icons[15] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwaM");
        icons[16] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwaH");
        icons[17] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkwaE");
        icons[18] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkgL");
        icons[19] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkgM");
        icons[20] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkgH");
        icons[21] = iconRegister.registerIcon(Reference.MOD_ID + ":BlockCkgE");
    }

    // side: 0:bottom, 1:top, 2:N, 3:S, 4:W, 5:E
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        try {
            boolean waterMill = meta >= 4 && meta <= 7;
            boolean generator = meta >= 8;
            int tier = meta - (generator ? 8 : waterMill ? 4 : 0);
            int windIcon = tier * 3;

            int icon = switch (side) {
                case 0, 9 -> 12;
                case 2 -> windIcon + 1;
                case 3 -> windIcon;
                case 4, 5, 8 -> windIcon + 2;
                case 6 -> waterMill ? tier + 14 : windIcon;
                case 7 -> generator ? tier + 18 : windIcon + 1;
                default -> 13;
            };
            return icons[icon];
        } catch (Exception e) {
            CompactKineticGenerators.LOGGER.error("Generator icon lookup failed: side " + side + ", meta " + meta, e);
            return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        TileEntity tile = block.getTileEntity(x, y, z);

        if (tile instanceof IWrenchable wrenchable) {
            return this.getIcon(face2iconID[wrenchable.getFacing()][side], block.getBlockMetadata(x, y, z));
        }

        return this.getIcon(face2iconID[2][side], 0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return switch (meta) {
            // wind mills
            case 1 -> new MediumWindKineticGeneratorTileEntity();
            case 2 -> new HighWindKineticGeneratorTileEntity();
            case 3 -> new ExtremeWindKineticGeneratorTileEntity();
            // water mills
            case 4 -> new LowWaterKineticGeneratorTileEntity();
            case 5 -> new MediumWaterKineticGeneratorTileEntity();
            case 6 -> new HighWaterKineticGeneratorTileEntity();
            case 7 -> new ExtremeWaterKineticGeneratorTileEntity();
            // kinetic generators
            case 8 -> new LowKineticGeneratorTileEntity();
            case 9 -> new MediumKineticGeneratorTileEntity();
            case 10 -> new HighKineticGeneratorTileEntity();
            case 11 -> new ExtremeKineticGeneratorTileEntity();
            default -> new LowWindKineticGeneratorTileEntity();
        };
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        if (player.isSneaking()) {
            return false;
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        return tile instanceof IHasGui gui && IC2.platform.launchGui(player, gui);
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof ISidedInventory inventory) {
            for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
                ItemStack stack = inventory.getStackInSlot(slot);
                if (stack == null) {
                    continue;
                }

                float offsetX = world.rand.nextFloat() * 0.8F + 0.1F;
                float offsetY = world.rand.nextFloat() * 0.8F + 0.1F;
                float offsetZ = world.rand.nextFloat() * 0.8F + 0.1F;
                while (stack.stackSize > 0) {
                    int size = Math.min(world.rand.nextInt(32) + 32, stack.stackSize);
                    stack.stackSize -= size;
                    EntityItem drop = new EntityItem(
                        world,
                        x + offsetX,
                        y + offsetY,
                        z + offsetZ,
                        new ItemStack(stack.getItem(), size, stack.getItemDamage()));
                    if (stack.hasTagCompound()) {
                        drop.getEntityItem()
                            .setTagCompound(
                                (NBTTagCompound) stack.getTagCompound()
                                    .copy());
                    }
                    world.spawnEntityInWorld(drop);
                }
            }
            world.func_147453_f(x, y, z, block); // Notify neighbors of the inventory change.
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs ctab, List<ItemStack> list) {
        for (int meta = 0; meta < 12; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof IWrenchable wrenchable) || itemStack == null) {
            return;
        }

        int meta = itemStack.getItemDamage();
        world.setBlockMetadataWithNotify(x, y, z, meta > 11 ? 0 : meta, 2);

        int face = entity == null ? 0 : MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
        short facing = switch (face) {
            case 1 -> 5; // E
            case 2 -> 3; // S
            case 3 -> 4; // W
            default -> 2; // N
        };
        wrenchable.setFacing(facing);
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);

        // unload from e-net
        if (tile instanceof KineticGeneratorTileEntity ckg) {
            ckg.onBlockBreak(this, meta);
            ckg.onUnloaded();
        }
    }

}
