package in.bigdash.rms.application.web.api.facility;
import in.bigdash.rms.model.Facility;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.FacilityService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;
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

/**
 * = FacilitiesItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Facility.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/facilities/{facility}", name = "FacilitiesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacilitiesItemJsonController {

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
    public FacilitiesItemJsonController(FacilityService facilityService) {
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
     * @param id
     * @return Facility
     */
    @ModelAttribute
    public Facility getFacility(@PathVariable("facility") Long id) {
        Facility facility = facilityService.findOne(id);
        if (facility == null) {
            throw new NotFoundException(String.format("Facility with identifier '%s' not found", id));
        }
        return facility;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Facility facility) {
        return ResponseEntity.ok(facility);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return UriComponents
     */
    public static UriComponents showURI(Facility facility) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FacilitiesItemJsonController.class).show(facility)).buildAndExpand(facility.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedFacility
     * @param facility
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Facility storedFacility, @Valid @RequestBody Facility facility, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        facility.setId(storedFacility.getId());
        getFacilityService().save(facility);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Facility facility) {
        getFacilityService().delete(facility);
        return ResponseEntity.ok().build();
    }
}
