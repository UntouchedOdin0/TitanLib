package me.titan.lib.CommandsLib;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;
import me.titan.lib.Chat;
import me.titan.lib.Common;
import me.titan.lib.TitanLib;
import me.titan.lib.enums.CommandAccess;
import me.titan.lib.enums.LogType;

/**
 * 
 * A Class that represent a Command,
 * each ParentCommand can have multiple ChildCommand's
 * <br>
 * <br>
 * <b>You may only register this Command on your onEnable using this:</b>
 * <br> 
 * 
 * <code> Common.registerCommand(Command);</code>
 * <br>
 * <br>
 * <b>You May set the childs of a ParentCommand using:</b>
 * <br>
 * <code>setChilds(Child1,Child2,..);</code>
 * 
 * @author TitanDev / JustAgamer
 *
 */
@Getter
public abstract class ParentCommand extends Command {

	boolean returned;

	String name;

	@Getter
	@Setter
	CommandAccess flag;

	ConsoleCommandSender console;
	Player p;

	@Getter
	@Setter
	boolean adminCommand;

	String childsString;

	@Getter
	List<ChildCommand> childs = new ArrayList<>();

	protected ParentCommand(String name) {
		super(name);
		this.name = name;
		childs.add(new HelpCommand());

	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {

		/*
		 *
		 * Checking the Sender
		 *
		 */

		if (sender instanceof Player) {
			p = (Player) sender;
			console = null;

			// player performing the command, and the command is for console.
			if (flag == CommandAccess.CONSOLE) {
				tell("&4This command can only performed from the console.");
				return false;
			}
			//returnTell("&4This command can only performed from the console.");
		} else if (sender instanceof ConsoleCommandSender) {
			// console performing the command, and the command is for Console.
			console = (ConsoleCommandSender) sender;
			p = null;
			if (flag == CommandAccess.PLAYERS) {
				tell("&4&4You must be a player to perform this command.");
				return false;
				//returnTell("&4You must be a player to perform this command.");
			}
		}

		if (this.isAdminCommand() && p != null && !p.hasPermission(TitanLib.getPlugin().getName() + ".admin")) {
			Chat.tell(p, "&4You lack the proper permission.");
			return false;
		}

		// ===================================================================
		// ===================================================================

		String childs = "";
		for (ChildCommand child : getChilds()) {

			if (getChilds().size() > 1)
				childs = childs + "&8|&6" + child.getName();
			else
				childs = child.getName();

			if (childs.startsWith("|", 2))
				childs = childs.substring(3);

		}
		childsString = childs;

		if (args.length < 1) {

			Common.tell(sender, "&4Usage: &b/" + name + " &8<" + childs + "&8>");
			return false;
		}

		// ===================================================================
		// ===================================================================

		/*
		 *
		 * Checking Keywards
		 *
		 */
		boolean fail = true;
		for (ChildCommand child : getChilds())
			if (child.isParentAble())
				for (ChildsListCommand clc : child.getChilds()) {

					if (args[0].equals(child.getName()) && !args[0].contains(clc.getName())) {
						String clcs = "";
						for (ChildsListCommand clc_ : child.getChilds()) {
							if (child.getChilds().size() > 1)
								clcs = clcs + "&8|&6" + clc_.getName();
							else
								clcs = clc_.getName();

							if (clcs.startsWith("|", 2))
								clcs = clcs.substring(3);

						}
						Common.tell(sender, "&4Usage: &b/" + name + child.getName() + "<" + clcs + ">.");

					}

					if (clc.getAliases().contains(args[0].replace(child.getName(), ""))
							|| clc.getAliases() != null && args[0].equalsIgnoreCase(child.getName() + clc.getName())) {

						if (p != null) {

							if (clc.getPerm() == null)
								Common.log("No Permission Found for Command/subCommand {" + clc.getName() + "}",
										LogType.WARNING);
							if (clc.getPerm() != null && !p.hasPermission(clc.getPerm())) {
								Common.tell(p, "&4You lack the proper permission!");
								return false;
							}
						}

						clc.run(args, sender != null && p == null ? null : p,
								p != null && console == null ? null : console);
						fail = false;

					}
				}
			else if (!child.isParentAble())
				if (child.getName().equalsIgnoreCase(args[0])
						|| child.getAliases() != null && child.getAliases().contains(args[0])) {
					if (p != null) {
						if (child.getPerm() == null)
							Common.log("No Permission Found for Command/subCommand {" + child.getName() + "}",
									LogType.WARNING);
						if (child.getPerm() != null && !p.hasPermission(child.getPerm())) {
							Common.tell(p, "&4You lack the proper permission!");
							return false;
						}
					}

					child.run(args, sender, sender != null && p == null ? null : p,
							p != null && console == null ? null : console);
					fail = false;

				}

		if (fail)
			Chat.tell(sender, "&4Usage: &b/" + name + " &8<&6" + childs + "&8>");
		return true;

	}

	public final static Player getPlayer(String from) {
		Player p = Bukkit.getPlayer(from);
		if (p != null)
			return p;
		return null;
	}

	public static final World getWorld(String from) {
		World w = Bukkit.getWorld(from);
		if (w != null)
			return w;
		return null;
	}

	protected final void tell(String message) {
		Chat.tell(p != null && console == null ? p : console, message);

	}

	public void setChilds(ChildCommand... cmds) {
		for (ChildCommand cmd : cmds) {
			cmd.setParent(this);
			childs.add(cmd);
		}

	}

}
