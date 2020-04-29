package be.dezijwegel.commands;

import be.dezijwegel.BetterSleeping;
import be.dezijwegel.events.SleepTracker;
import be.dezijwegel.interfaces.BsCommand;
import be.dezijwegel.interfaces.Reloadable;
import be.dezijwegel.management.Management;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;

public class CommandHandler implements CommandExecutor {

    private BetterSleeping plugin;
    private Management management;
    private SleepTracker sleepTracker;

    // Subcommands
    private BsCommand reload;
    private BsCommand skipNight;
    private BsCommand help;
    private BsCommand status;
    private BsCommand night;


    /**
     * Creates an object that reloads given objects IN ORDER!!
     * @param reloadables
     * @param management
     * @param plugin
     */
    public CommandHandler (LinkedList<Reloadable> reloadables, Management management, SleepTracker sleepTracker, BetterSleeping plugin)
    {
        this.management = management;
        this.sleepTracker = sleepTracker;
        this.plugin = plugin;

        reload = new Reload(reloadables, management, plugin);
        help = new Help(management);
        skipNight = new SkipNight(management, sleepTracker);
        status = new Status(sleepTracker, management);
        night = new Night(management, sleepTracker);

    }


    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] strings) {

        if (cmd.getName().equalsIgnoreCase("bettersleeping"))
        {
            if (strings.length > 0)
            {
                switch(strings[0].toLowerCase())
                {
                    case "reload":
                        return reload.execute(cs, cmd, string, strings);
                    case "help":
                        return help.execute(cs, cmd, string, strings);
                    case "skip":
                        return skipNight.execute(cs, cmd, string, strings);
                    case "status":
                    case "s":
                        return status.execute(cs, cmd, string, strings);
                    case "night":
                    case "n":
                        return night.execute(cs, cmd, string, strings);
                }
            } else {
                return help.execute(cs, cmd, string, strings);
            }
        }

        // No existing command was found
        return false;
    }
}
