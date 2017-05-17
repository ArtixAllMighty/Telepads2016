package subaraki.telepads.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import subaraki.telepads.block.TelepadBlocks;
import subaraki.telepads.item.TelepadItems;
public class RecipeHandler {

	public RecipeHandler() {
		loadRecipes();
	}

	private static void loadRecipes(){
		loadBlockRecipes();
		loadItemRecipes();
	}

	private static void loadBlockRecipes(){
		GameRegistry.addRecipe(new ItemStack(TelepadBlocks.blockTelepad), new Object[]{
				"GGG","EEE","ICI",
				'G',ConfigurationHandler.instance.pad_recipe_glass,
				'E',ConfigurationHandler.instance.pad_recipe_pearl,
				'I',ConfigurationHandler.instance.pad_recipe_iron,
				'C',ConfigurationHandler.instance.pad_recipe_compass
		});
	}

	private static void loadItemRecipes(){
		GameRegistry.addRecipe(new ItemStack(TelepadItems.toggler),
				"RBR","DDD",
				'R',ConfigurationHandler.instance.toggler_recipe_repeater,
				'B',ConfigurationHandler.instance.toggler_recipe_block,
				'D',ConfigurationHandler.instance.toggler_recipe_dust
				);
		GameRegistry.addRecipe(new ItemStack(TelepadItems.transmitter),
				"III","RDR","III",
				'I',ConfigurationHandler.instance.transmitter_recipe_iron,
				'R',ConfigurationHandler.instance.transmitter_recipe_dust,
				'D',ConfigurationHandler.instance.transmitter_recipe_diamond
				);

		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(TelepadItems.ender_bead_necklace),
				"string", "string", "string",
				TelepadItems.ender_bead,TelepadItems.ender_bead,
				TelepadItems.ender_bead,TelepadItems.ender_bead)
				);

	}	
}
