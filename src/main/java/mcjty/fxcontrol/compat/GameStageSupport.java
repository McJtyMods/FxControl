package mcjty.fxcontrol.compat;

import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class GameStageSupport {

    public static boolean hasGameStage(EntityPlayer player, String stage) {
        if (player != null) {
            IStageData stageData = GameStageHelper.getPlayerData(player);
            return stageData.hasStage(stage);
        } else {
            return false;
        }
    }
}
