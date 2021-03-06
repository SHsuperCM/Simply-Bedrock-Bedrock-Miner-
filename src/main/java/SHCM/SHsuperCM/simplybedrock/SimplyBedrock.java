package SHCM.SHsuperCM.simplybedrock;

import SHCM.SHsuperCM.simplybedrock.blocks.bedrock_miner.TEBlockMiner;
import SHCM.SHsuperCM.simplybedrock.items.ModItems;
import SHCM.SHsuperCM.simplybedrock.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mod(modid = SimplyBedrock.MODID, acceptedMinecraftVersions = "[1.12]")
public class SimplyBedrock {
    public static final String MODID = "simplybedrock";

    @Mod.Instance
    public static SimplyBedrock instance;

    @SidedProxy(clientSide = "SHCM.SHsuperCM.simplybedrock.proxy.ClientProxy", serverSide = "SHCM.SHsuperCM.simplybedrock.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        syncConfig(new ConfigChangedEvent(MODID, null, false, false));
    }

    @SubscribeEvent
    public void syncConfig(ConfigChangedEvent event) {
        if(!event.getModID().equals(MODID)) return;

        ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
        try {
            proxy.setModelPickaxe(JsonToNBT.getTagFromJson(Config.block_model_pickaxe));
        } catch (NBTException e) {
            proxy.setModelPickaxe(ItemStack.EMPTY.serializeNBT());
            e.printStackTrace();
        }

        TEBlockMiner.setBedrockBlocks(Arrays.stream(Config.bedrock_blocks).map(Block::getBlockFromName).collect(Collectors.toList()));

        Map<Block, ItemStack> drops = new HashMap<>();
        for (String drop : Config.block_drops) {
            String[] blockDrop = drop.split(">");
            ItemStack stack;
            try {
                stack = new ItemStack(JsonToNBT.getTagFromJson(blockDrop[1]));
            } catch (NBTException e) {
                stack = ItemStack.EMPTY;
            }
            drops.put(Block.getBlockFromName(blockDrop[0]), stack);
        }
        TEBlockMiner.setDrops(drops);
    }
    @net.minecraftforge.common.config.Config(modid = MODID)
    public static class Config {

        @net.minecraftforge.common.config.Config.Name("Bedrock Hitpoints")
        @net.minecraftforge.common.config.Config.Comment({"Determines how many hits bedrock requires by a Bedrock Miner to be broken.","Controls the both the time and fuel it takes in ticks(20th of a second)"})
        public static int bedrock_hitpoints = 6000;

        @net.minecraftforge.common.config.Config.Name("Orb of Infinity")
        @net.minecraftforge.common.config.Config.Comment({"Should the Orb of Infinity anvil recipe be added"})
        public static boolean orb_of_infinity = true;

        @net.minecraftforge.common.config.Config.Name("Orb of Infinity XP cost")
        @net.minecraftforge.common.config.Config.Comment({"How many levels should applying an Orb of Infinity on a tool cost"})
        public static int orb_of_infinity_xp_cost = 100;

        @net.minecraftforge.common.config.Config.Name("Bedrock blocks")
        @net.minecraftforge.common.config.Config.Comment({"A list of blocks that the miner considers as bedrock.", "The format is \"modid:blockid\"."})
        public static String[] bedrock_blocks = new String[] {"minecraft:bedrock"};

        @net.minecraftforge.common.config.Config.Name("Block Drops")
        @net.minecraftforge.common.config.Config.Comment({"A list of blocks and itemstacks that say what drops when a block is mined,", "The format is:", "modid:blockid>{item_nbt}"})
        public static String[] block_drops = new String[]{"minecraft:bedrock>{id:\"simplybedrock:bedrock_dust\",Count:1b}"};

        @net.minecraftforge.common.config.Config.Name("Block model pickaxe")
        @net.minecraftforge.common.config.Config.Comment("NBT formatted itemstack that'll be rendered mining the bedrock")
        public static String block_model_pickaxe = new ItemStack(Items.DIAMOND_PICKAXE).serializeNBT().toString();
    }
}
