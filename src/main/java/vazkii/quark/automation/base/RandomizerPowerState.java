package vazkii.quark.automation.base;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

/**
 * @author WireSegal
 * Created at 10:12 AM on 8/26/19.
 */
public enum RandomizerPowerState implements IStringSerializable {
    OFF, LEFT, RIGHT;


    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
