package org.polyfrost.polyparticles.mixin;

import cc.polyfrost.oneconfig.gui.elements.ModCard;
import cc.polyfrost.oneconfig.gui.pages.ModsPage;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.ParticlePage;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ModsPage.class, remap = false)
public abstract class ModsPageMixin {

    @Shadow @Final private ArrayList<ModCard> modCards;

    @Inject(method = "reloadMods", at = @At(value = "TAIL"))
    private void remove(CallbackInfo ci) {
        ModsPage page = (ModsPage) ((Object) this);
        List<ModCard> cards = new ArrayList<>(modCards);
        cards.removeIf(card -> PolyParticles.INSTANCE.getMods().contains(card.getModData()));
        if (page instanceof ParticlePage) {
            modCards.removeAll(cards);
        } else {
            modCards.clear();
            modCards.addAll(cards);
        }
    }

}
