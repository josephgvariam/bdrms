package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.InventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;


@RooJsonMixin(entity = InventoryItem.class)
public abstract class InventoryItemJsonMixin {
}
