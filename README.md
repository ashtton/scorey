## scorey
An advanced, yet super lightweight spigot scoreboard api, created by yours truly.\
Meant for easily creating scoreboards especially when dealing with multiple different boards.

#### How to use

```java
// Main Class
public class ExamplePlugin extends JavaPlugin {
    public void onEnable() {
        // Register a scorey helper in on-enable
        // Also provide a default board
        new Scorey(this, new ScoreboardHelper(), "default-board");
    }
}

// Scoreboard Helper Class
public class ScoreboardHelper extends ScoreyHelper {
    public ScoreboardHelper() {
        super("&6&lScoreboard Title");
    }

    @ScoreyBoard(name = "default-board")
    public List<String> defaultBoard(Player player) {
        List<String> lines = new ArrayList<>();

        lines.add("&7&m------------------------");
        lines.add("&fHello, &4" + player.getName() + "&f, this");
        lines.add("&fis the default scoreboard.");
        lines.add("&7&m------------------------");

        return lines;
    }

    @ScoreyBoard(name = "cool-board")
    public List<String> defaultBoard(Player player) {
        List<String> lines = new ArrayList<>();

        lines.add("&7&m------------------------");
        lines.add("&fHello, &4" + player.getName() + "&f, this");
        lines.add("&fis the cool scoreboard.");
        lines.add("&7&m------------------------");

        return lines;
    }
}

// Listener to change scoreboard
public class ScoreboardListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().getMessage().equals("i'm cool")) {
            // You can update a single players scoreboard using
            ScoreyPlayer.setScoreboard(e.getPlayer().getUniqueId(), "cool-board");
        }

        if (e.getPlayer().getMessage().equals("we're all cool")) {
            // You can update all players scoreboards 
            // including new player joins by using
            ScoreyPlayer.setDefaultScoreboard("cool-board");
            ScoreyPlayer.updateAllDefault();
        }
    }
}
```