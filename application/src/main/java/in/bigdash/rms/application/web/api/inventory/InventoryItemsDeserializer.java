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

import java.util.*;


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

        Map<String, Box> boxMap = new HashMap<>();
        Map<String, File> fileMap = new HashMap<>();
        Map<String, Document> docMap = new HashMap<>();

        Map<String, Map> xMap = new HashMap();
        xMap.put("box", boxMap);
        xMap.put("file", fileMap);
        xMap.put("doc", docMap);

        for (JsonNode node : nodes) {
            if(node.get("type").asText().equals("BOX")) {
                BoxInventoryItem item = getBoxInventoryItem(node, xMap);
                inventoryItems.add(item);
            }
            else if(node.get("type").asText().equals("FILE")){
                FileInventoryItem item = getFileInventoryItem(node, xMap);
                inventoryItems.add(item);
            }
            else{
                DocumentInventoryItem item = getDocumentInventoryItem(node, xMap);
                inventoryItems.add(item);
            }
        }

        User user = getCurrentUser();
        for(InventoryItem i : inventoryItems){
            i.setUserCreated(user);
        }

        //Request request = (Request) jsonParser.getCurrentValue();
        Long requestId = ((Request)jsonParser.getCurrentValue()).getId();
        Request request = requestService.findOne(requestId);
        request.setInventoryItems(new HashSet<>());

        request.addToInventoryItems(inventoryItems);

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

    private BoxInventoryItem getBoxInventoryItem(JsonNode node, Map<String, Map> xMap){

        BoxInventoryItem boxInventoryItem;

        Long id = getLong(node.get("id"));
        if(id != null){
            boxInventoryItem = boxInventoryItemService.findOne(id);
        }
        else{
            boxInventoryItem = new BoxInventoryItem();
        }

        boxInventoryItem.setRef1(node.get("ref1").asText());
        boxInventoryItem.setRef2(node.get("ref2").asText());
        boxInventoryItem.setRef3(node.get("ref3").asText());
        boxInventoryItem.setRef4(node.get("ref4").asText());
        boxInventoryItem.setRef5(node.get("ref5").asText());
        boxInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));

        Box box = getBox(node.get("box"), xMap);
        box.addToInventoryItem(boxInventoryItem);

        return boxInventoryItem;
    }

    private FileInventoryItem getFileInventoryItem(JsonNode node, Map<String, Map> xMap){

        FileInventoryItem fileInventoryItem;

        Long id = getLong(node.get("id"));
        if(id != null){
            fileInventoryItem = fileInventoryItemService.findOne(id);
        }
        else{
            fileInventoryItem = new FileInventoryItem();
        }

        fileInventoryItem.setRef1(node.get("ref1").asText());
        fileInventoryItem.setRef2(node.get("ref2").asText());
        fileInventoryItem.setRef3(node.get("ref3").asText());
        fileInventoryItem.setRef4(node.get("ref4").asText());
        fileInventoryItem.setRef5(node.get("ref5").asText());
        fileInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));

        File file = getFile(node.get("file"), xMap);
        file.addToInventoryItem(fileInventoryItem);

        return fileInventoryItem;
    }

    private DocumentInventoryItem getDocumentInventoryItem(JsonNode node, Map<String, Map> xMap){

        DocumentInventoryItem docInventoryItem;

        Long id = getLong(node.get("id"));
        if(id != null){
            docInventoryItem = documentInventoryItemService.findOne(id);
        }
        else{
            docInventoryItem = new DocumentInventoryItem();
        }

        docInventoryItem.setRef1(node.get("ref1").asText());
        docInventoryItem.setRef2(node.get("ref2").asText());
        docInventoryItem.setRef3(node.get("ref3").asText());
        docInventoryItem.setRef4(node.get("ref4").asText());
        docInventoryItem.setRef5(node.get("ref5").asText());
        docInventoryItem.setStatus(InventoryItemStatus.valueOf(node.get("status").asText()));

        Document document = getDocument(node.get("document"), xMap);
        document.addToInventoryItem(docInventoryItem);

        return docInventoryItem;
    }


    private Box getBox(JsonNode node, Map<String, Map> xMap) {

        Box box = (Box) xMap.get("box").get(node.get("barcode").asText());
        if(box != null){
            return box;
        }

        Long id = getLong(node.get("id"));
        if(id != null){
            box = boxService.findOne(id);
            box.setFiles(new HashSet<>());
        }
        else{
            box = new Box();
        }

        box.setBarcode(node.get("barcode").asText());
        box.setLocation(node.get("location").asText());

        xMap.get("box").put(box.getBarcode(), box);

        return box;
    }

    private File getFile(JsonNode node, Map<String, Map> xMap) {
        File file = (File) xMap.get("file").get(node.get("barcode").asText());
        if(file != null){
            return file;
        }

        Long id = getLong(node.get("id"));
        if(id != null){
            file = fileService.findOne(id);
            file.setDocuments(new HashSet<>());
        }
        else{
            file = new File();
        }

        file.setBarcode(node.get("barcode").asText());
        file.setLocation(node.get("location").asText());

        Box box = getBox(node.get("box"), xMap);
        box.addToFiles(Collections.singleton(file));

        xMap.get("file").put(file.getBarcode(), file);

        return file;
    }


    private Document getDocument(JsonNode node, Map<String, Map> xMap) {
        Document doc = (Document) xMap.get("doc").get(node.get("barcode").asText());
        if(doc != null){
            return doc;
        }

        Long id = getLong(node.get("id"));
        if(id != null){
            doc = documentService.findOne(id);
        }
        else{
            doc = new Document();
        }

        doc.setBarcode(node.get("barcode").asText());
        doc.setLocation(node.get("location").asText());

        File file = getFile(node.get("file"), xMap);
        file.addToDocuments(Collections.singleton(doc));

        xMap.get("doc").put(doc.getBarcode(), doc);

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
