package in.bigdash.rms.application.web.api.facility;
import in.bigdash.rms.model.Facility;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.FacilityService;
import io.springlets.data.domain.GlobalSearch;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 * = FacilitiesCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Facility.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/facilities", name = "FacilitiesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacilitiesCollectionJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FacilityService facilityService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param facilityService
     */
    @Autowired
    public FacilitiesCollectionJsonController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FacilityService
     */
    public FacilityService getFacilityService() {
        return facilityService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facilityService
     */
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<Facility>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<Facility> facilities = getFacilityService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(facilities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FacilitiesCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Facility facility, BindingResult result) {
        if (facility.getId() != null || facility.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Facility newFacility = getFacilityService().save(facility);
        UriComponents showURI = FacilitiesItemJsonController.showURI(newFacility);
        return ResponseEntity.created(showURI.toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facilities
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Facility> facilities, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getFacilityService().save(facilities);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facilities
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Facility> facilities, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getFacilityService().save(facilities);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getFacilityService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
