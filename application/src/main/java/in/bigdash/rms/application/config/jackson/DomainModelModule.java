package in.bigdash.rms.application.config.jackson;
import com.fasterxml.jackson.databind.module.SimpleModule;
import in.bigdash.rms.application.web.api.box.BoxJsonMixin;
import in.bigdash.rms.application.web.api.client.ClientJsonMixin;
import in.bigdash.rms.application.web.api.document.DocumentJsonMixin;
import in.bigdash.rms.application.web.api.facility.FacilityJsonMixin;
import in.bigdash.rms.application.web.api.file.FileJsonMixin;
import in.bigdash.rms.application.web.api.inventory.BoxInventoryItemJsonMixin;
import in.bigdash.rms.application.web.api.inventory.DocumentInventoryItemJsonMixin;
import in.bigdash.rms.application.web.api.inventory.FileInventoryItemJsonMixin;
import in.bigdash.rms.application.web.api.inventory.InventoryItemJsonMixin;
import in.bigdash.rms.application.web.api.request.destruction.DestructionRequestJsonMixin;
import in.bigdash.rms.application.web.api.request.insertion.InsertionRequestJsonMixin;
import in.bigdash.rms.application.web.api.request.permout.PermoutRequestJsonMixin;
import in.bigdash.rms.application.web.api.request.pickup.PickupRequestJsonMixin;
import in.bigdash.rms.application.web.api.request.RequestJsonMixin;
import in.bigdash.rms.application.web.api.request.refiling.RefilingRequestJsonMixin;
import in.bigdash.rms.application.web.api.request.retrieval.RetrievalRequestJsonMixin;
import in.bigdash.rms.application.web.api.request.transfer.TransferRequestJsonMixin;
import in.bigdash.rms.application.web.api.shelf.ShelfJsonMixin;
import in.bigdash.rms.application.web.api.user.UserJsonMixin;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.DestructionRequest;
import in.bigdash.rms.model.request.InsertionRequest;
import in.bigdash.rms.model.request.PermoutRequest;
import in.bigdash.rms.model.request.PickupRequest;
import in.bigdash.rms.model.request.RefilingRequest;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.model.request.RetrievalRequest;
import in.bigdash.rms.model.request.TransferRequest;
import org.springframework.boot.jackson.JsonComponent;


@JsonComponent
public class DomainModelModule extends SimpleModule {


    public DomainModelModule() {
        // Mixin registration
        setMixInAnnotation(Box.class, BoxJsonMixin.class);
        setMixInAnnotation(Client.class, ClientJsonMixin.class);
        setMixInAnnotation(Document.class, DocumentJsonMixin.class);
        setMixInAnnotation(Facility.class, FacilityJsonMixin.class);
        setMixInAnnotation(File.class, FileJsonMixin.class);
        setMixInAnnotation(Shelf.class, ShelfJsonMixin.class);
        setMixInAnnotation(User.class, UserJsonMixin.class);
        setMixInAnnotation(BoxInventoryItem.class, BoxInventoryItemJsonMixin.class);
        setMixInAnnotation(DocumentInventoryItem.class, DocumentInventoryItemJsonMixin.class);
        setMixInAnnotation(FileInventoryItem.class, FileInventoryItemJsonMixin.class);
        setMixInAnnotation(InventoryItem.class, InventoryItemJsonMixin.class);
        setMixInAnnotation(DestructionRequest.class, DestructionRequestJsonMixin.class);
        setMixInAnnotation(InsertionRequest.class, InsertionRequestJsonMixin.class);
        setMixInAnnotation(PermoutRequest.class, PermoutRequestJsonMixin.class);
        setMixInAnnotation(PickupRequest.class, PickupRequestJsonMixin.class);
        setMixInAnnotation(RefilingRequest.class, RefilingRequestJsonMixin.class);
        setMixInAnnotation(Request.class, RequestJsonMixin.class);
        setMixInAnnotation(RetrievalRequest.class, RetrievalRequestJsonMixin.class);
        setMixInAnnotation(TransferRequest.class, TransferRequestJsonMixin.class);
    }
}
