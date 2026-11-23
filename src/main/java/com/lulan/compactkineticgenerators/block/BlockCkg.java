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
import com.lulan.compactkineticgenerators.tileentity.BasicTileCkg;
import com.lulan.compactkineticgenerators.tileentity.TileCkgE;
import com.lulan.compactkineticgenerators.tileentity.TileCkgH;
import com.lulan.compactkineticgenerators.tileentity.TileCkgL;
import com.lulan.compactkineticgenerators.tileentity.TileCkgM;
import com.lulan.compactkineticgenerators.tileentity.TileCkwaE;
import com.lulan.compactkineticgenerators.tileentity.TileCkwaH;
import com.lulan.compactkineticgenerators.tileentity.TileCkwaL;
import com.lulan.compactkineticgenerators.tileentity.TileCkwaM;
import com.lulan.compactkineticgenerators.tileentity.TileCkwmE;
import com.lulan.compactkineticgenerators.tileentity.TileCkwmH;
import com.lulan.compactkineticgenerators.tileentity.TileCkwmL;
import com.lulan.compactkineticgenerators.tileentity.TileCkwmM;

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
            CompactKineticGenerators.LOGGER.error(e);
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
            case 1 -> new TileCkwmM();
            case 2 -> new TileCkwmH();
            case 3 -> new TileCkwmE();
            // water mills
            case 4 -> new TileCkwaL();
            case 5 -> new TileCkwaM();
            case 6 -> new TileCkwaH();
            case 7 -> new TileCkwaE();
            // kinetic generators
            case 8 -> new TileCkgL();
            case 9 -> new TileCkgM();
            case 10 -> new TileCkgH();
            case 11 -> new TileCkgE();
            default -> new TileCkwmL();
        };
    }

    /**
     * Called when a player right-clicks the block.
     * Arguments: world, block coordinates, player, clicked face, and hit position within the block.
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        // client side
        if (world.isRemote) {
            return true;
        }
        // server side
        else if (!player.isSneaking()) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile instanceof IHasGui) {
                if (!world.isRemote) {
                    return IC2.platform.launchGui(player, (IHasGui) tile);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    // Drop the block's inventory when it breaks.
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);

        // Drop the contents of each inventory slot as item entities.
        if (tile instanceof ISidedInventory tile2) {
            // for all slots
            for (int i = 0; i < tile2.getSizeInventory(); i++) {
                ItemStack itemstack = tile2.getStackInSlot(i);

                if (itemstack != null) {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F; // Random drop offset within the block.
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        // Split large stacks into randomly sized drops.
                        int j = world.rand.nextInt(32) + 32;
                        if (j > itemstack.stackSize) {
                            j = itemstack.stackSize;
                        }
                        itemstack.stackSize -= j;

                        // Create an item entity for this drop.
                        EntityItem item = new EntityItem(
                            world,
                            x + f,
                            y + f1,
                            z + f2,
                            new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                        // Copy any NBT data to the dropped stack.
                        if (itemstack.hasTagCompound()) {
                            item.getEntityItem()
                                .setTagCompound(
                                    (NBTTagCompound) itemstack.getTagCompound()
                                        .copy());
                        }

                        world.spawnEntityInWorld(item); // Spawn the item entity.
                    }
                }
            }

            world.func_147453_f(x, y, z, block); // alert block changed
        }

        // Let the superclass remove the tile entity.
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs ctab, List<ItemStack> list) {
        // wind mills
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 3));
        // wateer mills
        list.add(new ItemStack(item, 1, 4));
        list.add(new ItemStack(item, 1, 5));
        list.add(new ItemStack(item, 1, 6));
        list.add(new ItemStack(item, 1, 7));
        // kinetic generators
        list.add(new ItemStack(item, 1, 8));
        list.add(new ItemStack(item, 1, 9));
        list.add(new ItemStack(item, 1, 10));
        list.add(new ItemStack(item, 1, 11));
    }

    // set facing when placed
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if ((tile instanceof IWrenchable tile2) && itemStack != null) {

            // set meta value
            int meta = itemStack.getItemDamage();
            if (meta > 11) meta = 0;

            world.setBlockMetadataWithNotify(x, y, z, meta, 2);

            // set facing
            if (entity != null) {
                int face = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

                switch (face) {
                    case 0:
                        tile2.setFacing((short) 2); // N
                        break;
                    case 1:
                        tile2.setFacing((short) 5); // E
                        break;
                    case 2:
                        tile2.setFacing((short) 3); // S
                        break;
                    case 3:
                        tile2.setFacing((short) 4); // W
                        break;
                }
            } else {
                // Default to north if no entity placed the block.
                tile2.setFacing((short) 2);
            }
        }
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);

        // unload from e-net
        if (tile instanceof BasicTileCkg ckg) {
            ckg.onBlockBreak(this, meta);
            ckg.onUnloaded();
        }
    }

}
