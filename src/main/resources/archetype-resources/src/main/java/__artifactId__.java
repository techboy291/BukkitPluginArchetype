package ${package};

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class ${artifactId} extends JavaPlugin {
	private static ${artifactId} inst;
	private CommandsManager<CommandSender> commands;
	private CommandsManagerRegistration commandsRegistration;

	@Override
	public void onEnable()
	{
		this.getServer().getPluginManager()
				.registerEvents(new ${artifactId}Listener(), this);

		this.commands = new BukkitCommandsManager();
		this.commandsRegistration = new CommandsManagerRegistration(
				this, this.commands);

		this.commandsRegistration.register(${artifactId}Commands.class);

		this.getLogger().info(
				"v" + this.getDescription().getVersion()
						+ " is now enabled.");

		inst = this;
	}

	@Override
	public void onDisable()
	{
		this.getLogger().info(
				"v" + this.getDescription().getVersion()
						+ " is now disabled.");

		inst = null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args)
	{
		try {
			this.commands.execute(cmd.getName(), args, sender,
					sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED
					+ "You do not have permission for this command.");
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + "Usage: "
					+ e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED
						+ "Number expected, String (letter/word) received.");
			} else {
				sender.sendMessage(ChatColor.RED
						+ e.getMessage());
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;
	}

	public static ${artifactId} get()
	{
		return inst;
	}
}
