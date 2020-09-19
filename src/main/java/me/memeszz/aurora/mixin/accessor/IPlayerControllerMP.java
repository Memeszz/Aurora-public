package me.memeszz.aurora.mixin.accessor;

public interface IPlayerControllerMP {

    void setBlockHitDelay(int delay);

    void setIsHittingBlock(boolean hittingBlock);

    float getCurBlockDamageMP();

    void setCurBlockDamageMP(float curBlockDamageMP);

}
