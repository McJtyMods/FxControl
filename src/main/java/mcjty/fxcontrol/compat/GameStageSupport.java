package mcjty.fxcontrol.compat;

import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class GameStageSupport {

    public static boolean hasGameStage(Entity player, String stage) {
        if (player instanceof EntityPlayer) {
            IStageData stageData = GameStageHelper.getPlayerData((EntityPlayer) player);
            return stageData.hasStage(stage);
        } else {
            return false;
        }
    }
}
