package vazkii.quark.base.module;

import net.minecraftforge.api.distmarker.Dist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadModule {

	ModuleCategory category();
	
	String name() default "";
	String requiredMod() default "";
	String description() default "";
	String[] antiOverlap() default { };

	boolean hasSubscriptions() default false;
	Dist[] subscribeOn() default { Dist.CLIENT, Dist.DEDICATED_SERVER };

	boolean enabledByDefault() default true;
	
}
