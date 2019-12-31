function injectForEachMethod(method, targetType, clazz, targetName, sig, callback) {
    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var target = ASM.findFirstMethodCall(method,
        targetType,
        clazz,
        targetName,
        sig);

    while (target !== null) {
        var index = method.instructions.indexOf(target);
        var indexShift = callback(target, index);

        var newIndex = method.instructions.indexOf(target);
        if (newIndex !== -1)
            index = newIndex;
        else if (typeof indexShift === 'number')
            index += indexShift;

        target = ASM.findFirstMethodCallAfter(method,
            targetType,
            clazz,
            targetName,
            sig,
            index + 1);
    }

    return method;
}

function initializeCoreMod() {
    return {
        'extract-color': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.ItemRenderer',
                'methodName': 'func_180454_a', // renderItem
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
                'methodName': 'func_191965_a', // renderModel
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
        },

        'use-color-teisr': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.ItemRenderer',
                'methodName': 'func_211271_a', // renderEffect
                'methodDesc': '(Ljava/lang/Runnable;)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                return injectForEachMethod(method,
                    ASM.MethodType.STATIC,
                    "com/mojang/blaze3d/platform/GlStateManager",
                    ASM.mapMethod("color3f"), // color3f
                    "(FFF)V",
                    function (target) {
                        var newInstructions = new InsnList();
                        newInstructions.add(ASM.buildMethodCall(
                            "vazkii/quark/base/handler/AsmHooks",
                            "applyRuneColor",
                            "()V",
                            ASM.MethodType.STATIC
                        ));
                        method.instructions.insert(target, newInstructions);
                    });
            }
        },

        'extract-elytra-color': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.layers.ElytraLayer',
                'methodName': 'func_212842_a_', // render
                'methodDesc': '(Lnet/minecraft/entity/LivingEntity;FFFFFFF)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                return injectForEachMethod(method,
                    ASM.MethodType.STATIC,
                    "net/minecraft/client/renderer/entity/layers/ArmorLayer",
                    ASM.mapMethod("func_215338_a"), // func_215338_a
                    "(Ljava/util/function/Consumer;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/entity/model/EntityModel;FFFFFFF)V",
                    function (target) {
                        var newInstructions = new InsnList();
                        newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 9));
                        newInstructions.add(ASM.buildMethodCall(
                            "vazkii/quark/base/handler/AsmHooks",
                            "setColorRuneTargetStack",
                            "(Lnet/minecraft/item/ItemStack;)V",
                            ASM.MethodType.STATIC
                        ));

                        method.instructions.insertBefore(target, newInstructions);
                    });
            }
        },

        'extract-armor-color': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.layers.ArmorLayer',
                'methodName': 'func_188361_a', // renderArmorLayer
                'methodDesc': '(Lnet/minecraft/entity/LivingEntity;FFFFFFFLnet/minecraft/inventory/EquipmentSlotType;)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                var newInstructions = new InsnList();
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 9));
                newInstructions.add(ASM.buildMethodCall(
                    "vazkii/quark/base/handler/AsmHooks",
                    "setColorRuneTargetStack",
                    "(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;)V",
                    ASM.MethodType.STATIC
                ));

                method.instructions.insertBefore(method.instructions.getFirst(), newInstructions);

                return method;
            }
        },

        'armor-recolor': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.layers.ArmorLayer',
                'methodName': 'func_215338_a',
                'methodDesc': '(Ljava/util/function/Consumer;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/entity/model/EntityModel;FFFFFFF)V'
            },
            'transformer': function(method) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

                return injectForEachMethod(method,
                    ASM.MethodType.STATIC,
                    "com/mojang/blaze3d/platform/GlStateManager",
                    ASM.mapMethod("color4f"), // color4f
                    "(FFFF)V",
                    function (target) {
                        var newInstructions = new InsnList();
                        newInstructions.add(ASM.buildMethodCall(
                            "vazkii/quark/base/handler/AsmHooks",
                            "applyRuneColor",
                            "()V",
                            ASM.MethodType.STATIC
                        ));
                        method.instructions.insert(target, newInstructions);
                    });
            }
        }
    }
}
