package tfar.tfcinfo.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import tfar.tfcinfo.Stages;

public class AddMemorizationCommand extends CommandTreeBase {

	public AddMemorizationCommand() {
		addSubcommand(new MemorizationCommand(Stages.memorizedAverageTemp));
		addSubcommand(new MemorizationCommand(Stages.memorizedMinTemp));
		addSubcommand(new MemorizationCommand(Stages.memorizedMaxTemp));
		addSubcommand(new MemorizationCommand(Stages.memorizedRegionalTemp));
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
