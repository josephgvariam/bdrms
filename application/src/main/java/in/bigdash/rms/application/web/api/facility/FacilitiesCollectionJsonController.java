package in.bigdash.rms.application.web.api.facility;
import in.bigdash.rms.model.Facility;

import in.bigdash.rms.service.api.FacilityService;
import io.springlets.data.domain.GlobalSearch;

import java.util.Arrays;
import java.util.Collection;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@RestController
@RequestMapping(value = "/api/facilities", name = "FacilitiesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacilitiesCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FacilityService facilityService;


    @Autowired
    public FacilitiesCollectionJsonController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    public FacilityService getFacilityService() {
        return facilityService;
    }


    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Facility>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<Facility> facilities = getFacilityService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(facilities);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FacilitiesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Facility facility, BindingResult result) {
        log.debug("create: {}", facility);
        if (facility.getId() != null || facility.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", facility, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Facility newFacility = getFacilityService().save(facility);
        log.debug("create saved: {}", facility);
        UriComponents showURI = FacilitiesItemJsonController.showURI(newFacility);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Facility> facilities, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(facilities.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(facilities.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getFacilityService().save(facilities);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Facility> facilities, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(facilities.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(facilities.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getFacilityService().save(facilities);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getFacilityService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
