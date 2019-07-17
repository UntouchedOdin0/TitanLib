package me.titan.lib;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Class allow you to work with Spigot Chat Component easily.
 * 
 * @author TitanDev / JustAgamer
 *
 */
public class TitanText {
	private BaseComponent current;
	private final List<BaseComponent> parts = new ArrayList<>();

	public TitanText(TitanText original) {
		current = original.current.duplicate();

		for (final BaseComponent baseComponent : original.parts)
			parts.add(baseComponent.duplicate());
	}

	public TitanText(String text) {
		current = new TextComponent(TextComponent.fromLegacyText(Chat.colorize(text)));
	}

	public TitanText(BaseComponent component) {
		current = component.duplicate();
	}

	public TitanText append(BaseComponent component) {
		return append(component, FormatRetention.ALL);
	}

	public TitanText append(BaseComponent component, FormatRetention retention) {
		parts.add(current);

		final BaseComponent previous = current;
		current = component.duplicate();
		current.copyFormatting(previous, retention, false);
		return this;
	}

	public TitanText append(BaseComponent[] components) {
		return append(components, FormatRetention.ALL);
	}

	public TitanText append(BaseComponent[] components, FormatRetention retention) {
		Preconditions.checkArgument(components.length != 0, "No components to append");

		final BaseComponent previous = current;
		for (final BaseComponent component : components) {
			parts.add(current);

			current = component.duplicate();
			current.copyFormatting(previous, retention, false);
		}

		return this;
	}

	public TitanText append(String text) {
		return append(text, FormatRetention.FORMATTING);
	}

	public TitanText append(String text, FormatRetention retention) {
		parts.add(current);

		final BaseComponent old = current;
		current = new TextComponent(TextComponent.fromLegacyText(Chat.colorize(text)));
		current.copyFormatting(old, retention, false);

		return this;
	}

	public TitanText onClickRunCmd(String text) {
		return onClick(Action.RUN_COMMAND, text);
	}

	public TitanText onClickSuggestCmd(String text) {
		return onClick(Action.SUGGEST_COMMAND, text);
	}

	public TitanText onClick(Action action, String text) {
		current.setClickEvent(new ClickEvent(action, Chat.colorize(text)));
		return this;
	}

	public TitanText onHover(String text) {
		return onHover(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, text);
	}

	public TitanText onHover(net.md_5.bungee.api.chat.HoverEvent.Action action, String text) {
		current.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(Chat.colorize(text))));

		return this;
	}

	public TitanText retain(FormatRetention retention) {
		current.retain(retention);
		return this;
	}

	public BaseComponent[] create() {
		final BaseComponent[] result = parts.toArray(new BaseComponent[parts.size() + 1]);
		result[parts.size()] = current;

		return result;
	}

	public void send(Player... players) {
		final BaseComponent[] comp = create();

		for (final Player player : players)
			player.spigot().sendMessage(comp);
	}

	public static final TitanText builder(String text) {
		return new TitanText(text);
	}
}
