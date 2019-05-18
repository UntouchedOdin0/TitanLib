package me.titan.lib.CommandsLib;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.titan.lib.TitanLib;

public class HelpCommand extends ChildCommand {

	public HelpCommand() {
		super("help", false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String[] args, CommandSender sender, Player p, ConsoleCommandSender console) {
		String childs = "";
		String list = null;
		for (ChildCommand child : parent.getChilds())
			list = list + "\n" + String.format("%s : %s", child.getName());
		String[] messges = new String[] {
				"&6---- &c" + TitanLib.getPlugin().getName() + " Help &6----",
				""
		};

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return null;
	}

}
