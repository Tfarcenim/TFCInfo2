package tfar.tfcinfo.command;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import tfar.tfcinfo.TerrafirmaCraftInfo;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MemorizationCommand extends CommandBase {

	private final String memorization;

	public MemorizationCommand(String memorization) {
		this.memorization = memorization;
	}


	@Override
	public String getName() {
		return memorization;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "null";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		EntityPlayerMP player = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[0]);

		GameStageHelper.addStage(player, TerrafirmaCraftInfo.MODID+":"+memorization);
		GameStageHelper.syncPlayer(player);

	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		} else {
			return Collections.emptyList();
		}
	}
}
