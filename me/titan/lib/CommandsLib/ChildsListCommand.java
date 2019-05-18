package me.titan.lib.CommandsLib;

import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@RequiredArgsConstructor
public abstract class ChildsListCommand {
	final String name;

	ChildCommand parent;

	String perm;

	public abstract void run(String[] args, Player p, ConsoleCommandSender console);

	public abstract List<String> getAliases();
}
