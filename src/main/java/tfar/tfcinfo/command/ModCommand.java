package tfar.tfcinfo.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import tfar.tfcinfo.TerrafirmaCraftInfo;

public class ModCommand extends CommandTreeBase {

	public ModCommand() {
		addSubcommand(new AddMemorizationCommand());
	}

	@Override
	public String getName() {
		return TerrafirmaCraftInfo.MODID;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "null";
	}
}
