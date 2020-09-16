package tfar.tfcinfo.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import tfar.tfcinfo.Stages;

public class AddMemorizationCommand extends CommandTreeBase {

	public AddMemorizationCommand() {
		addSubcommand(new MemorizationCommand(Stages.averageTemp.memory()));
		addSubcommand(new MemorizationCommand(Stages.minTemp.memory()));
		addSubcommand(new MemorizationCommand(Stages.maxTemp.memory()));
		addSubcommand(new MemorizationCommand(Stages.regionalTemp.memory()));
	}

	@Override
	public String getName() {
		return "addMemorization";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "";
	}
}
