function initializeCoreMod() {
    return {
        'extract-color': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.ItemRenderer',
                'methodName': 'renderItem',
                'methodDesc': '(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/IBakedModel;)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                var newInstructions = new InsnList();
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                newInstructions.add(ASM.buildMethodCall(
                    "vazkii/quark/base/handler/AsmHooks", 
                    "setColorRuneTargetStack",
                    "(Lnet/minecraft/item/ItemStack;)V", 
                    ASM.MethodType.STATIC
                ));

                method.instructions.insertBefore(method.instructions.getFirst(), newInstructions);

                return method;
            }
        },

        'use-color': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.ItemRenderer',
                'methodName': 'renderModel',
                'methodDesc': '(Lnet/minecraft/client/renderer/model/IBakedModel;I)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                var newInstructions = new InsnList();
                newInstructions.add(new VarInsnNode(Opcodes.ILOAD, 2));
                newInstructions.add(ASM.buildMethodCall(
                    "vazkii/quark/base/handler/AsmHooks", 
                    "changeColor",
                    "(I)I", 
                    ASM.MethodType.STATIC
                ));
                newInstructions.add(new VarInsnNode(Opcodes.ISTORE, 2));

                method.instructions.insertBefore(method.instructions.getFirst(), newInstructions);

                return method;
            }
        }
    }
}
