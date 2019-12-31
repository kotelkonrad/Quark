package vazkii.quark.base.module;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.quark.base.Quark;

public final class ModuleLoader {
	
	public static final ModuleLoader INSTANCE = new ModuleLoader(); 
	
	private Map<Class<? extends Module>, Module> foundModules = new HashMap<>();
	
	private ConfigResolver config;
	
	private ModuleLoader() { }
	
	public void start() {
		findModules();
		dispatch(Module::construct);
		dispatch(Module::modulesStarted);
		resolveConfigSpec();
	}
	
	private void findModules() {
		ModuleFinder finder = new ModuleFinder();
		finder.findModules();
		foundModules = finder.getFoundModules();
	}
	
	private void resolveConfigSpec() {
		config = new ConfigResolver();
		config.makeSpec();
	}
	
	public void configChanged() {
		config.configChanged();
		dispatch(Module::configChanged);
	}

	@OnlyIn(Dist.CLIENT)
	public void configChangedClient() {
		dispatch(Module::configChangedClient);
	}
	
	public void setup() {
		dispatch(Module::earlySetup);
		Quark.proxy.handleQuarkConfigChange();
		dispatch(Module::setup);
	}

	@OnlyIn(Dist.CLIENT)
	public void clientSetup() {
		dispatch(Module::clientSetup);
	}

	@OnlyIn(Dist.CLIENT)
	public void modelRegistry() {
		dispatch(Module::modelRegistry);
	}

	public void loadComplete() {
		dispatch(Module::loadComplete);
	}
	
	private void dispatch(Consumer<Module> run) {
		foundModules.values().forEach(run);
	}
	
	public boolean isModuleEnabled(Class<? extends Module> moduleClazz) {
		Module module = getModuleInstance(moduleClazz);
		return module != null && module.enabled;
	}
	
	public Module getModuleInstance(Class<? extends Module> moduleClazz) {
		return foundModules.get(moduleClazz);
	}
	
}
