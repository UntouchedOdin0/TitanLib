package me.titan.lib.CommandsLib;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class ChildCommand {

	@Getter
	final String name;
	@Getter
	final boolean ParentAble;

	@Setter
	@Getter
	String perm;

	@Getter
	@Setter
	List<ChildsListCommand> childs;

	@Setter
	@Getter
	ParentCommand parent;

	public abstract void run(String[] args, CommandSender sender, Player p, ConsoleCommandSender console);

	public abstract List<String> getAliases();

}
