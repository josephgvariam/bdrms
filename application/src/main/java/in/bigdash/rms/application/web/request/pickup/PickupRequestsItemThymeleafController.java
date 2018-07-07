package in.bigdash.rms.application.web.request.pickup;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import in.bigdash.rms.application.web.reports.*;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.PickupRequest;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.PickupRequestService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;


@Controller
@RequestMapping(value = "/pickuprequests/{pickupRequest}", name = "PickupRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class PickupRequestsItemThymeleafController implements ConcurrencyManager<PickupRequest> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<PickupRequest> concurrencyTemplate = new ConcurrencyTemplate<PickupRequest>(this);


    private MethodLinkBuilderFactory<PickupRequestsItemThymeleafController> itemLink;


    private PickupRequestService pickupRequestService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<PickupRequestsCollectionThymeleafController> collectionLink;


    @Autowired
    public PickupRequestsItemThymeleafController(PickupRequestService pickupRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setPickupRequestService(pickupRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(PickupRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(PickupRequestsCollectionThymeleafController.class));
    }


    public PickupRequestService getPickupRequestService() {
        return pickupRequestService;
    }


    public void setPickupRequestService(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<PickupRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<PickupRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<PickupRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<PickupRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public PickupRequest getPickupRequest(@PathVariable("pickupRequest") Long id, Locale locale, HttpMethod method) {
        log.debug("{} {}", method, id);
        PickupRequest pickupRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            pickupRequest = pickupRequestService.findOneForUpdate(id);
        } else {
            pickupRequest = pickupRequestService.findOne(id);
        }
        if (pickupRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "PickupRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return pickupRequest;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute PickupRequest pickupRequest, Model model) {
        log.debug("show: {}", pickupRequest);
        model.addAttribute("pickupRequest", pickupRequest);
        return new ModelAndView("pickuprequests/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute PickupRequest pickupRequest, Model model) {
        model.addAttribute("pickupRequest", pickupRequest);
        return new ModelAndView("pickuprequests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("pickupDateTime_date_format", "yyyy-MM-dd HH:mm a");
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(RequestStatus.values()));
    }


    public ConcurrencyTemplate<PickupRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "pickupRequest";
    }


    public String getEditViewPath() {
        return "pickuprequests/edit";
    }


    public Integer getLastVersion(PickupRequest record) {
        Long versionValue = getPickupRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(PickupRequest entity, Model model) {
        // Populate the form with all the necessary elements
        populateForm(model);
        // Add concurrency attribute to the model to show the concurrency form
        // in the current edit view
        model.addAttribute("concurrency", true);
        // Add the new version value to the model.
        model.addAttribute("newVersion", getLastVersion(entity));
        // Add the current pet values to maintain the values introduced by the user
        model.addAttribute(getModelName(), entity);
        // Return the edit view path
        return new org.springframework.web.servlet.ModelAndView(getEditViewPath(), model.asMap());
    }


    @InitBinder("pickupRequest")
    public void initPickupRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(PickupRequest.class, getPickupRequestService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute PickupRequest pickupRequest, Model model) {
        log.debug("get edit form: {}", pickupRequest);
        populateForm(model);
        model.addAttribute("pickupRequest", pickupRequest);
        return new ModelAndView("pickuprequests/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute PickupRequest pickupRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        log.debug("update: {}", pickupRequest);
        // Check if provided form contain errors
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", pickupRequest, result.getAllErrors());
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        PickupRequest savedPickupRequest = getConcurrencyTemplate().execute(pickupRequest, model, new ConcurrencyCallback<PickupRequest>() {

            @Override
            public PickupRequest doInConcurrency(PickupRequest pickupRequest) throws Exception {
                PickupRequest updatedPickupRequest =  getPickupRequestService().save(pickupRequest);
                log.debug("update saved: {}", updatedPickupRequest);
                return updatedPickupRequest;
            }
        });
        UriComponents showURI = getItemLink().to(PickupRequestsItemThymeleafLinkFactory.SHOW).with("pickupRequest", savedPickupRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute PickupRequest pickupRequest) {
        log.debug("delete: {}", pickupRequest);
        getPickupRequestService().delete(pickupRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/workflow/**", name = "workflow")
    public ModelAndView workflow(@ModelAttribute PickupRequest pickupRequest, Model model) {
        log.debug("get workflow: {}", pickupRequest);
        return new ModelAndView("pickuprequests/workflow");
    }


    @GetMapping(name = "/loadchart", value = "loadchart")
    @ResponseBody
    public ResponseEntity<?> loadchart(@ModelAttribute PickupRequest request, HttpServletResponse response) {
        log.debug("Generating loadchart for {}", request);
        try {
            InputStream jasperStream = this.getClass().getResourceAsStream("/templates/reports/loadchart.jasper");

            Map<String, Object> params = new HashMap<>();
            params.put("requestId", request.getId());
            params.put("clientName", request.getClient().getName());
            params.put("clientDepartment", request.getClient().getDepartment());
            params.put("clientAddress", request.getClient().getAddress());

            Map<String, BoxReport> boxReportsMap = new HashMap<>();

            for(InventoryItem inventoryItem: request.getInventoryItems()){
                String boxBarcode = inventoryItem.getBoxBarcode();

                if(inventoryItem.getType().equals("BOX")){
                    BoxReport br = new BoxReport(boxBarcode, 0, 0);
                    boxReportsMap.put(boxBarcode, br);
                }
                else if(inventoryItem.getType().equals("FILE")){
                    if(!boxReportsMap.containsKey(boxBarcode)){
                        int numberFiles = ((FileInventoryItem) inventoryItem).getFile().getBox().getFiles().size();
                        BoxReport br = new BoxReport(boxBarcode, numberFiles, 0);
                        boxReportsMap.put(boxBarcode, br);
                    }
                }
                else if(inventoryItem.getType().equals("DOCUMENT")){
                    if(!boxReportsMap.containsKey(boxBarcode)){
                        int numberFiles = ((DocumentInventoryItem) inventoryItem).getDocument().getFile().getBox().getFiles().size();
                        int numberDocs = ((DocumentInventoryItem) inventoryItem).getDocument().getFile().getDocuments().size();
                        BoxReport br = new BoxReport(boxBarcode, numberFiles, numberDocs);
                        boxReportsMap.put(boxBarcode, br);
                    }
                }
            }

            List data = new ArrayList<>(boxReportsMap.values());
            Collections.sort(data);

            params.put("totalBoxes", data.size());

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(data));

            String fileName = "Loadchart-Request-" + request.getId() + ".pdf";
            JasperReportsExporter exporter = new JasperReportsPdfExporter();
            exporter.export(jasperPrint, fileName, response);
        }catch (Exception e){
            log.error("Error generating load chart!", e);
            throw new ExportingErrorException(e.getMessage());
        }

        log.debug("loadchart generated for {}", request);
        return ResponseEntity.ok().build();
    }
}
