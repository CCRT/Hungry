package us.icebrg.hungry.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.icebrg.hungry.Hungry;
import us.icebrg.hungry.HungryConfiguration;
import us.icebrg.hungry.HungryMessages;

public class HungryFoodCommand implements CommandExecutor {

	protected Hungry plugin;

	public HungryFoodCommand(Hungry plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String cmdLabel, String[] args) {
		// Requires one argument: /food <id>
		if (args.length != 1) {
			return false;
		}

		if (sender instanceof Player
				&& !Hungry.permissions.hasGuard((Player) sender,
						"hungry.player.food")) {
			return true;
		}

		Material foodMat = Material.getMaterial(args[0]);

		if (foodMat == null) {
			sender.sendMessage(this.plugin.getConfig().getMessage(HungryMessages.ERR_NO_SUCH_FOOD));
			return true;
		}

		HungryConfiguration config = this.plugin.getConfig();
		String foodName = foodMat.name();

		if (config.foods.containsKey(foodName)) {
			sender.sendMessage(
					this.plugin.getConfig().getMessage(
							HungryMessages.VAR_FOOD_INFO,
							foodName, config.foods.get(foodName).toString()));

			return true;
		}

		if (config.foodBlocks.containsKey(foodName)) {
			sender.sendMessage(
					this.plugin.getConfig().getMessage(
							HungryMessages.VAR_FOOD_INFO,
							foodName, config.foodBlocks.get(foodName).toString()));

			return true;
		}

		// if nothing has been returned so far, this food item was not in either
		// food or foodBlocks -
		// inform the user
		sender.sendMessage(this.plugin.getConfig().getMessage(HungryMessages.ERR_NO_SUCH_FOOD));

		return true;
	}
}
