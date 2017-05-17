package subaraki.telepads.mod;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import subaraki.telepads.block.TelepadBlocks;
import subaraki.telepads.capability.TelePadDataCapability;
import subaraki.telepads.gui.GuiHandler;
import subaraki.telepads.handler.ConfigurationHandler;
import subaraki.telepads.handler.RecipeHandler;
import subaraki.telepads.handler.WorldDataHandler;
import subaraki.telepads.handler.proxy.ServerProxy;
import subaraki.telepads.hooks.AttachCapability;
import subaraki.telepads.hooks.PlayerTracker;
import subaraki.telepads.item.TelepadItems;
import subaraki.telepads.network.NetworkHandler;

@Mod(modid = Telepads.MODID, name = Telepads.NAME, version = Telepads.VERSION,  dependencies = Telepads.DEPENDENCY)
public class Telepads {

	public static final String MODID = "telepads";
	public static final String NAME = "Telepads";
	public static final String VERSION = "1.11.2-1.0.2.0";
	public static final String DEPENDENCY = "required-after:subcommonlib";

	@SidedProxy(clientSide = "subaraki.telepads.handler.proxy.ClientProxy", serverSide = "subaraki.telepads.handler.proxy.ServerProxy")
	public static ServerProxy proxy;

	@Instance(MODID)
	public static Telepads instance;

	public static Logger log = LogManager.getLogger(MODID);

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event){

		new ConfigurationHandler(event.getSuggestedConfigurationFile());

		ModMetadata modMeta = event.getModMetadata();
		modMeta.authorList = Arrays.asList(new String[] { "Subaraki" });
		modMeta.autogenerated = false;
		modMeta.credits = "";
		modMeta.description = "Telepads : blocks to teleport you to and fro, fro an to.";
		modMeta.url = "https://github.com/ArtixAllMighty/Telepads2016/wiki";

		instance = this;

		new TelePadDataCapability().register();
		new AttachCapability();

		TelepadBlocks.loadBlocks(); // blocks before items.
		TelepadItems.loadItems(); //items need to register itemBlocks

		new RecipeHandler();

		proxy.registerRenders();
		proxy.registerTileEntityAndRender();

		new PlayerTracker();

		new NetworkHandler();
		new GuiHandler();

		new WorldDataHandler.WorldDataHandlerSaveEvent();
		
		new subaraki.telepads.handler.EventHandler();
	}
}
