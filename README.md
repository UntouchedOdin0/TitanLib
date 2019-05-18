# TitanLib
Java Library that every developer needs!

__YOU MAY USE THIS LIBRARY AS YOU WANT FREELY__

# Get Started
You may need to add this to your class path by:
__Maven Repostitory:__
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```
	<dependency>
	    <groupId>com.github.TitanDevX</groupId>
	    <artifactId>TitanLib</artifactId>
	    <version>Compile</version>
	</dependency>
```

__Gradle:__
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```dependencies {
		implementation 'com.github.TitanDevX:TitalLib:combile'
	}
```

You can get started Just by adding this to your onEnable on your main class: 
```Java
TitanLib.setPlugin(this);
```
__Make Sure You put this line in the top of your onEnable!__
And after this you are ready to get started you can use any class you want from this plugin.

# Examples
__Example 1:__
Want to register your command? You can easily register, You don't need to put anything in the plugin.yml just this line:
```Java
Common.registerCommand(Your Command);
```
__Example 2__
Want to make a command that have some childs, like /titan [gift/die]
You Can easily do that with the __ParentCommand__ Class By Doing this:
```Java
public class titanCommand extends ParentCommand{

public titanCommand() {
		super("titan"); //The name of your command 
		
		setChilds(new GiftCommand(), new DieCommand()); // Your command childs 
		
		//Who Can type the command? (ALL/PLAYERS/CONSOLE)
		// YOU MAY ONLY USE ONE OF THESE
		
		// It will tell the sender a this message if he cant use this command:
		// Only players can use this command.
		setFlag(CommandAccess.PLAYERS);
		// Conolse and players can use this command
		setFlag(CommandAccess.ALL);
		// Only Console can use this command.
		setFlag(CommandAccess.CONSOLE);

	}

}
class GiftCommand extends ChildCommand{
public GiftCommand() {
		super("gift", false);// Name of child

	}

	@Override
	public void run(String[] args, CommandSender sender, Player p, ConsoleCommandSender console) {

		if(args.length < 3){
			Chat.tell(p, "&4Usage: /titan gift [player] [item]");
			return;
			}

		String player = args[1];
		String item = args[2];
		Player target = Bukkit.getPlayer(player);
		if(target == null) {
			
			Chat.tell(p, "&4This Player does not exist.");
			return;
		}
			try{
				target.getInventory().addItem(new ItemStack(Material.getMaterial(item.toUpperCase())));
			}catch(NullPointerException ex) {
				Chat.tell(p, "&4This item does not exist.");
				return;
			}
			
			Chat.tell(target,"&6" + p.getName() + " &chas gift you a &6" + item + "!");
			Chat.tell(target, "&cYou gift &6" + player + " &ca &6" + item);


	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("g");
	}
}
class DieCommand extends ChildCommand{
public DieCommand() {
		super("die", false);// Name of child

	}

	@Override
	public void run(String[] args, CommandSender sender, Player p, ConsoleCommandSender console) {

		
	Chat.tell(p, "&4Usage: /titan die [player]" );
			return;
		}
		String player = args[1];
		Player target = Bukkit.getPlayer(player);
		if(target == null) {
			
			Chat.tell(p, "&4This Player does not exist.");
			return;
		}
		
		target.setHealth(0);
		target.spigot().respawn();

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("d");
	}
}
```

# help Us improve this library
Im not good at naming classes, so if you found any class with wrong name please tell me.


