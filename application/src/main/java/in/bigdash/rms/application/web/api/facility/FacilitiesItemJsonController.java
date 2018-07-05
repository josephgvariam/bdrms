package in.bigdash.rms.application.web.api.facility;
import in.bigdash.rms.model.Facility;

import in.bigdash.rms.service.api.FacilityService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;


@RestController
@RequestMapping(value = "/api/facilities/{facility}", name = "FacilitiesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacilitiesItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FacilityService facilityService;


    @Autowired
    public FacilitiesItemJsonController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    public FacilityService getFacilityService() {
        return facilityService;
    }


    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    @ModelAttribute
    public Facility getFacility(@PathVariable("facility") Long id) {
        Facility facility = facilityService.findOne(id);
        if (facility == null) {
            throw new NotFoundException(String.format("Facility with identifier '%s' not found", id));
        }
        return facility;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Facility facility) {
        log.debug("show: {}", facility);
        return ResponseEntity.ok(facility);
    }


    public static UriComponents showURI(Facility facility) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FacilitiesItemJsonController.class).show(facility)).buildAndExpand(facility.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Facility storedFacility, @Valid @RequestBody Facility facility, BindingResult result) {
        log.debug("update: {}", facility);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", facility, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        facility.setId(storedFacility.getId());
        Facility updatedFacility = getFacilityService().save(facility);
        log.debug("update saved: {}", updatedFacility);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Facility facility) {
        log.debug("delete: {}", facility);
        getFacilityService().delete(facility);
        return ResponseEntity.ok().build();
    }
}
