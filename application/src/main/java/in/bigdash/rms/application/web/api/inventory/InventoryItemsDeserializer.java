package in.bigdash.rms.application.web.api.inventory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.QUser;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.inventory.InventoryItemStatus;
import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.InventoryItemService;
import io.springlets.web.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;


@JsonComponent
public class InventoryItemsDeserializer extends JsonObjectDeserializer<Set<InventoryItem>> {


    private InventoryItemService inventoryItemService;


    private ConversionService conversionService;


    @Autowired
    public InventoryItemsDeserializer(@Lazy InventoryItemService inventoryItemService, ConversionService conversionService) {
        this.inventoryItemService = inventoryItemService;
        this.conversionService = conversionService;
    }


    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }


    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }



    public Set<InventoryItem> deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode nodes) {
        Set<InventoryItem> inventoryItems = new HashSet<>();

        for (JsonNode node : nodes) {
            if(node.get("type").asText().equals("BOX")) {
                BoxInventoryItem item = getBoxInventoryItem(node);
                inventoryItems.add(item);
            }
        }

        User user = getCurrentUser();
        for(InventoryItem i : inventoryItems){
            i.setUserCreated(user);
        }

        return inventoryItems;
    }

    private Long getId(JsonNode node){

        if(node == null){
            return null;
        }

        String idText = node.asText();

        if(StringUtils.isBlank(idText) || idText.equals("null")){
            return null;
        }else{
            return Long.parseLong(idText);
        }
    }

    private BoxInventoryItem getBoxInventoryItem(JsonNode node){

        BoxInventoryItem boxInventoryItem = new BoxInventoryItem();

        boxInventoryItem.setId(getId(node.get("id")));
        boxInventoryItem.setRef1(node.get("ref1").asText());
        boxInventoryItem.setRef2(node.get("ref2").asText());
        boxInventoryItem.setRef3(node.get("ref3").asText());
        boxInventoryItem.setRef4(node.get("ref4").asText());
        boxInventoryItem.setRef5(node.get("ref5").asText());
        boxInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));
        boxInventoryItem.setBox(getBox(node.get("box")));

        return boxInventoryItem;
    }

    private Box getBox(JsonNode node) {
        Box box = new Box();

        box.setId(getId(node.get("id")));
        box.setBarcode(node.get("barcode").asText());
        box.setLocation(node.get("location").asText());

        return box;
    }


    private User getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return ( (JpaUserDetails) authentication.getPrincipal() ).getUser();
        }

        return null;
    }
}
