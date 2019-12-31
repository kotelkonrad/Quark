package vazkii.quark.base.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.quark.base.handler.BrewingHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author WireSegal
 * Created at 5:10 PM on 9/23/19.
 */
public class PotionIngredient extends Ingredient {
    private final Item item;
    private final Potion potion;

    public PotionIngredient(Item item, Potion potion) {
        super(Stream.of(new Ingredient.SingleItemList(BrewingHandler.of(item, potion))));
        this.item = item;
        this.potion = potion;
    }

    @Override
    public boolean test(@Nullable ItemStack input) {
        if (input == null)
            return false;
        //Can't use areItemStacksEqualUsingNBTShareTag because it compares stack size as well
        return item == input.getItem() && PotionUtils.getPotionFromItem(input) == potion;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Nonnull
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return PotionIngredient.Serializer.INSTANCE;
    }

    @Nonnull
    @Override
    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", Objects.toString(CraftingHelper.getID(PotionIngredient.Serializer.INSTANCE)));
        json.addProperty("item", Objects.toString(item.getRegistryName()));
        json.addProperty("potion", Objects.toString(potion.getRegistryName()));
        return json;
    }

    public static class Serializer implements IIngredientSerializer<PotionIngredient> {
        public static final PotionIngredient.Serializer INSTANCE = new PotionIngredient.Serializer();

        @Nonnull
        @Override
        public PotionIngredient parse(@Nonnull PacketBuffer buffer) {
            Item item = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
            Potion potion = ForgeRegistries.POTION_TYPES.getValue(buffer.readResourceLocation());
            return new PotionIngredient(item, potion);
        }

        @Nonnull
        @Override
        public PotionIngredient parse(@Nonnull JsonObject json) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsJsonPrimitive("item").getAsString()));
            Potion potion = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(json.getAsJsonPrimitive("item").getAsString()));
            return new PotionIngredient(item, potion);
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, @Nonnull PotionIngredient ingredient) {
            buffer.writeString(Objects.toString(ingredient.item.getRegistryName()));
            buffer.writeString(Objects.toString(ingredient.potion.getRegistryName()));
        }
    }
}
