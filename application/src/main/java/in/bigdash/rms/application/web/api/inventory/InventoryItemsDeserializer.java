package in.bigdash.rms.application.web.api.inventory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.*;
import in.bigdash.rms.model.inventory.*;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.*;
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
    private RequestService requestService;
    private BoxInventoryItemService boxInventoryItemService;
    private FileInventoryItemService fileInventoryItemService;
    private DocumentInventoryItemService documentInventoryItemService;
    private BoxService boxService;
    private FileService fileService;
    private DocumentService documentService;


    private ConversionService conversionService;


    @Autowired
    public InventoryItemsDeserializer(@Lazy InventoryItemService inventoryItemService,
                                      @Lazy RequestService requestService,
                                      @Lazy BoxInventoryItemService boxInventoryItemService,
                                      @Lazy FileInventoryItemService fileInventoryItemService,
                                      @Lazy DocumentInventoryItemService documentInventoryItemService,
                                      @Lazy BoxService boxService,
                                      @Lazy FileService fileService,
                                      @Lazy DocumentService documentService,
                                      ConversionService conversionService) {
        this.inventoryItemService = inventoryItemService;
        this.requestService = requestService;
        this.boxInventoryItemService = boxInventoryItemService;
        this.fileInventoryItemService = fileInventoryItemService;
        this.documentInventoryItemService = documentInventoryItemService;
        this.boxService = boxService;
        this.fileService = fileService;
        this.documentService = documentService;
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
            else if(node.get("type").asText().equals("FILE")){
                FileInventoryItem item = getFileInventoryItem(node);
                inventoryItems.add(item);
            }
            else{
                DocumentInventoryItem item = getDocumentInventoryItem(node);
                inventoryItems.add(item);
            }
        }

        User user = getCurrentUser();
        Set<Request> requests = new HashSet<>();

        Long requestId = ((Request)jsonParser.getCurrentValue()).getId();

        requests.add(requestService.findOne(requestId));

        for(InventoryItem i : inventoryItems){
            i.setUserCreated(user);
            i.setRequests(requests);
        }

        return inventoryItems;
    }

    private Long getLong(JsonNode node){

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

        BoxInventoryItem boxInventoryItem;

        Long id = getLong(node.get("id"));
        if(id != null){
            boxInventoryItem = boxInventoryItemService.findOne(id);
        }
        else{
            boxInventoryItem = new BoxInventoryItem();
            boxInventoryItem.setBox(getBox(node.get("box")));
        }

        boxInventoryItem.setRef1(node.get("ref1").asText());
        boxInventoryItem.setRef2(node.get("ref2").asText());
        boxInventoryItem.setRef3(node.get("ref3").asText());
        boxInventoryItem.setRef4(node.get("ref4").asText());
        boxInventoryItem.setRef5(node.get("ref5").asText());
        boxInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));

        return boxInventoryItem;
    }

    private FileInventoryItem getFileInventoryItem(JsonNode node){

        FileInventoryItem fileInventoryItem;

        Long id = getLong(node.get("id"));
        if(id != null){
            fileInventoryItem = fileInventoryItemService.findOne(id);
        }
        else{
            fileInventoryItem = new FileInventoryItem();
            fileInventoryItem.setFile(getFile(node.get("file")));
        }

        fileInventoryItem.setRef1(node.get("ref1").asText());
        fileInventoryItem.setRef2(node.get("ref2").asText());
        fileInventoryItem.setRef3(node.get("ref3").asText());
        fileInventoryItem.setRef4(node.get("ref4").asText());
        fileInventoryItem.setRef5(node.get("ref5").asText());
        fileInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));

        return fileInventoryItem;
    }

    private DocumentInventoryItem getDocumentInventoryItem(JsonNode node){

        DocumentInventoryItem docInventoryItem;

        Long id = getLong(node.get("id"));
        if(id != null){
            docInventoryItem = documentInventoryItemService.findOne(id);
        }
        else{
            docInventoryItem = new DocumentInventoryItem();
            docInventoryItem.setDocument(getDocument(node.get("document")));
        }

        docInventoryItem.setRef1(node.get("ref1").asText());
        docInventoryItem.setRef2(node.get("ref2").asText());
        docInventoryItem.setRef3(node.get("ref3").asText());
        docInventoryItem.setRef4(node.get("ref4").asText());
        docInventoryItem.setRef5(node.get("ref5").asText());
        docInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));

        return docInventoryItem;
    }


    private Box getBox(JsonNode node) {
        Box box;

        Long id = getLong(node.get("id"));
        if(id != null){
            box = boxService.findOne(id);
        }
        else{
            box = new Box();
            box.setBarcode(node.get("barcode").asText());
        }

        box.setLocation(node.get("location").asText());

        return box;
    }

    private File getFile(JsonNode node) {
        File file;

        Long id = getLong(node.get("id"));
        if(id != null){
            file = fileService.findOne(id);
        }
        else{
            file = new File();
            file.setBarcode(node.get("barcode").asText());
            file.setBox(getBox(node.get("box")));
        }

        file.setLocation(node.get("location").asText());

        return file;
    }


    private Document getDocument(JsonNode node) {
        Document doc;

        Long id = getLong(node.get("id"));
        if(id != null){
            doc = documentService.findOne(id);
        }
        else{
            doc = new Document();
            doc.setBarcode(node.get("barcode").asText());
            doc.setFile(getFile(node.get("file")));
        }

        doc.setLocation(node.get("location").asText());

        return doc;
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
