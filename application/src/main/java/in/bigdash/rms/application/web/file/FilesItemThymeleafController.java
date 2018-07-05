package in.bigdash.rms.application.web.file;
import in.bigdash.rms.model.File;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.service.api.FileService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
import java.util.Locale;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RequestMapping(value = "/files/{file}", name = "FilesItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class FilesItemThymeleafController implements ConcurrencyManager<File> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<File> concurrencyTemplate = new ConcurrencyTemplate<File>(this);


    private MethodLinkBuilderFactory<FilesItemThymeleafController> itemLink;


    private FileService fileService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<FilesCollectionThymeleafController> collectionLink;


    @Autowired
    public FilesItemThymeleafController(FileService fileService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setFileService(fileService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(FilesItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(FilesCollectionThymeleafController.class));
    }


    public FileService getFileService() {
        return fileService;
    }


    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<FilesItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<FilesItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<FilesCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<FilesCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public File getFile(@PathVariable("file") Long id, Locale locale, HttpMethod method) {
        log.debug("{} {}", method, id);
        File file = null;
        if (HttpMethod.PUT.equals(method)) {
            file = fileService.findOneForUpdate(id);
        } else {
            file = fileService.findOne(id);
        }
        if (file == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "File", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return file;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute File file, Model model) {
        log.debug("show: {}", file);
        model.addAttribute("file", file);
        return new ModelAndView("files/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute File file, Model model) {
        model.addAttribute("file", file);
        return new ModelAndView("files/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("modifiedDate_date_format", "yyyy-MM-dd HH:mm:ss");
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<File> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "file";
    }


    public String getEditViewPath() {
        return "files/edit";
    }


    public Integer getLastVersion(File record) {
        Long versionValue = getFileService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(File entity, Model model) {
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


    @InitBinder("file")
    public void initFileBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        binder.setDisallowedFields("inventoryItem.id");
        // Register validators
        GenericValidator validator = new GenericValidator(File.class, getFileService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute File file, Model model) {
        log.debug("get edit form");
        populateForm(model);
        model.addAttribute("file", file);
        return new ModelAndView("files/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute File file, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        log.debug("update: {}", file);
        // Check if provided form contain errors
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", file, result.getAllErrors());
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        File savedFile = getConcurrencyTemplate().execute(file, model, new ConcurrencyCallback<File>() {

            @Override
            public File doInConcurrency(File file) throws Exception {
                File updatedFile =  getFileService().save(file);
                log.debug("update saved: {}", updatedFile);
                return updatedFile;
            }
        });
        UriComponents showURI = getItemLink().to(FilesItemThymeleafLinkFactory.SHOW).with("file", savedFile.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute File file) {
        log.debug("delete: {}", file);
        getFileService().delete(file);
        return ResponseEntity.ok().build();
    }
}
