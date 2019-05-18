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


