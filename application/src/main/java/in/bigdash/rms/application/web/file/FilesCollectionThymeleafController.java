package in.bigdash.rms.application.web.file;
import in.bigdash.rms.model.File;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import in.bigdash.rms.application.web.reports.ExportingErrorException;
import in.bigdash.rms.application.web.reports.JasperReportsCsvExporter;
import in.bigdash.rms.application.web.reports.JasperReportsExporter;
import in.bigdash.rms.application.web.reports.JasperReportsPdfExporter;
import in.bigdash.rms.application.web.reports.JasperReportsXlsExporter;
import in.bigdash.rms.service.api.FileService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.ConvertedDatatablesData;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesColumns;
import io.springlets.data.web.datatables.DatatablesPageable;
import io.springlets.data.web.select2.Select2DataSupport;
import io.springlets.data.web.select2.Select2DataWithConversion;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;


@Controller
@RequestMapping(value = "/files", name = "FilesCollectionThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class FilesCollectionThymeleafController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MethodLinkBuilderFactory<FilesItemThymeleafController> itemLink;


    private FileService fileService;


    private MessageSource messageSource;


    private ConversionService conversionService;


    private MethodLinkBuilderFactory<FilesCollectionThymeleafController> collectionLink;


    @Autowired
    public FilesCollectionThymeleafController(FileService fileService, ConversionService conversionService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setFileService(fileService);
        setConversionService(conversionService);
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


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @GetMapping(name = "list")
    public ModelAndView list(Model model) {
        log.debug("list");
        return new ModelAndView("files/list");
    }


    @GetMapping(produces = Datatables.MEDIA_TYPE, name = "datatables", value = "/dt")
    @ResponseBody
    public ResponseEntity<ConvertedDatatablesData<File>> datatables(DatatablesColumns datatablesColumns, GlobalSearch search, DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
        log.debug("datatables");
        Page<File> files = getFileService().findAll(search, pageable);
        long totalFilesCount = files.getTotalElements();
        if (search != null && StringUtils.isNotBlank(search.getText())) {
            totalFilesCount = getFileService().count();
        }
        ConvertedDatatablesData<File> datatablesData = new ConvertedDatatablesData<File>(files, totalFilesCount, draw, getConversionService(), datatablesColumns);
        return ResponseEntity.ok(datatablesData);
    }


    @GetMapping(produces = Datatables.MEDIA_TYPE, name = "datatablesByIdsIn", value = "/dtByIdsIn")
    @ResponseBody
    public ResponseEntity<ConvertedDatatablesData<File>> datatablesByIdsIn(@RequestParam("ids") List<Long> ids, DatatablesColumns datatablesColumns, GlobalSearch search, DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
        log.debug("datatablesByIdsIn");
        Page<File> files = getFileService().findAllByIdsIn(ids, search, pageable);
        long totalFilesCount = files.getTotalElements();
        if (search != null && StringUtils.isNotBlank(search.getText())) {
            totalFilesCount = getFileService().count();
        }
        ConvertedDatatablesData<File> datatablesData = new ConvertedDatatablesData<File>(files, totalFilesCount, draw, getConversionService(), datatablesColumns);
        return ResponseEntity.ok(datatablesData);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, name = "select2", value = "/s2")
    @ResponseBody
    public ResponseEntity<Select2DataSupport<File>> select2(GlobalSearch search, Pageable pageable, Locale locale) {
        log.debug("select2");
        Page<File> files = getFileService().findAll(search, pageable);
        String idExpression = "#{id}";
        Select2DataSupport<File> select2Data = new Select2DataWithConversion<File>(files, idExpression, getConversionService());
        return ResponseEntity.ok(select2Data);
    }


    @InitBinder("file")
    public void initFileBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        binder.setDisallowedFields("inventoryItem.id");
        // Register validators
        GenericValidator validator = new GenericValidator(File.class, getFileService());
        binder.addValidators(validator);
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("modifiedDate_date_format", "yyyy-MM-dd HH:mm:ss");
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    @PostMapping(name = "create")
    public ModelAndView create(@Valid @ModelAttribute File file, BindingResult result, Model model) {
        log.debug("create: {}", file);
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", file, result.getAllErrors());
            populateForm(model);
            return new ModelAndView("files/create");
        }
        File newFile = getFileService().save(file);
        log.debug("create saved: {}", file);
        UriComponents showURI = getItemLink().to(FilesItemThymeleafLinkFactory.SHOW).with("file", newFile.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @GetMapping(value = "/create-form", name = "createForm")
    public ModelAndView createForm(Model model) {
        log.debug("get create form");
        populateForm(model);
        model.addAttribute("file", new File());
        return new ModelAndView("files/create");
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    @ResponseBody
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", ids);
        getFileService().delete(ids);
        return ResponseEntity.ok().build();
    }


    public void export(GlobalSearch search, @PageableDefault(size = 2147483647) Pageable pageable, String[] datatablesColumns, HttpServletResponse response, JasperReportsExporter exporter, String fileName, Locale locale) {
        // Obtain the filtered and ordered elements
        Page<File> files = getFileService().findAll(search, pageable);
        // Prevent generation of reports with empty data
        if (files == null || files.getContent().isEmpty()) {
            return;
        }
        // Creates a new ReportBuilder using DynamicJasper library
        FastReportBuilder builder = new FastReportBuilder();
        // IMPORTANT: By default, this application uses "export_default.jrxml"
        // to generate all reports. If you want to customize this specific report,
        // create a new ".jrxml" template and customize it. (Take in account the
        // DynamicJasper restrictions:
        // http://dynamicjasper.com/2010/10/06/how-to-use-custom-jrxml-templates/)
        builder.setTemplateFile("templates/reports/export_default.jrxml");
        // The generated report will display the same columns as the Datatables component.
        // However, this is not mandatory. You could edit this code if you want to ignore
        // the provided datatablesColumns
        if (datatablesColumns != null) {
            for (String column : datatablesColumns) {
                // Delegates in addColumnToReportBuilder to include each datatables column
                // to the report builder
                addColumnToReportBuilder(column, builder, locale, fileName);
            }
        }
        // This property resizes the columns to use full width page.
        // Set false value if you want to use the specific width of each column.
        builder.setUseFullPageWidth(true);
        // Creates a new Jasper Reports Datasource using the obtained elements
        JRDataSource ds = new JRBeanCollectionDataSource(files.getContent());
        // Generates the JasperReport
        JasperPrint jp;
        try {
            jp = DynamicJasperHelper.generateJasperPrint(builder.build(), new ClassicLayoutManager(), ds);
        } catch (JRException e) {
            String errorMessage = getMessageSource().getMessage("error_exportingErrorException", new Object[] { StringUtils.substringAfterLast(fileName, ".").toUpperCase() }, String.format("Error while exporting data to StringUtils file", StringUtils.substringAfterLast(fileName, ".").toUpperCase()), locale);
            throw new ExportingErrorException(errorMessage);
        }
        // Converts the JaspertReport element to a ByteArrayOutputStream and
        // write it into the response stream using the provided JasperReportExporter
        try {
            exporter.export(jp, fileName, response);
        } catch (JRException e) {
            String errorMessage = getMessageSource().getMessage("error_exportingErrorException", new Object[] { StringUtils.substringAfterLast(fileName, ".").toUpperCase() }, String.format("Error while exporting data to StringUtils file", StringUtils.substringAfterLast(fileName, ".").toUpperCase()), locale);
            throw new ExportingErrorException(errorMessage);
        } catch (IOException e) {
            String errorMessage = getMessageSource().getMessage("error_exportingErrorException", new Object[] { StringUtils.substringAfterLast(fileName, ".").toUpperCase() }, String.format("Error while exporting data to StringUtils file", StringUtils.substringAfterLast(fileName, ".").toUpperCase()), locale);
            throw new ExportingErrorException(errorMessage);
        }
    }


    @GetMapping(name = "exportCsv", value = "/export/csv")
    @ResponseBody
    public ResponseEntity<?> exportCsv(GlobalSearch search, @PageableDefault(size = 2147483647) Pageable pageable, @RequestParam("datatablesColumns") String[] datatablesColumns, HttpServletResponse response, Locale locale) {
        export(search, pageable, datatablesColumns, response, new JasperReportsCsvExporter(), "files_report.csv", locale);
        return ResponseEntity.ok().build();
    }


    @GetMapping(name = "exportPdf", value = "/export/pdf")
    @ResponseBody
    public ResponseEntity<?> exportPdf(GlobalSearch search, @PageableDefault(size = 2147483647) Pageable pageable, @RequestParam("datatablesColumns") String[] datatablesColumns, HttpServletResponse response, Locale locale) {
        export(search, pageable, datatablesColumns, response, new JasperReportsPdfExporter(), "files_report.pdf", locale);
        return ResponseEntity.ok().build();
    }


    @GetMapping(name = "exportXls", value = "/export/xls")
    @ResponseBody
    public ResponseEntity<?> exportXls(GlobalSearch search, @PageableDefault(size = 2147483647) Pageable pageable, @RequestParam("datatablesColumns") String[] datatablesColumns, HttpServletResponse response, Locale locale) {
        export(search, pageable, datatablesColumns, response, new JasperReportsXlsExporter(), "files_report.xls", locale);
        return ResponseEntity.ok().build();
    }


    public void addColumnToReportBuilder(String columnName, FastReportBuilder builder, Locale locale, String fileName) {
        try {
            if (columnName.equals("id")) {
                builder.addColumn(getMessageSource().getMessage("label_file_id", null, "Id", locale), "id", Long.class.getName(), 50);
            } else if (columnName.equals("version")) {
                builder.addColumn(getMessageSource().getMessage("label_file_version", null, "Version", locale), "version", Long.class.getName(), 100);
            } else if (columnName.equals("barcode")) {
                builder.addColumn(getMessageSource().getMessage("label_file_barcode", null, "Barcode", locale), "barcode", String.class.getName(), 100);
            } else if (columnName.equals("location")) {
                builder.addColumn(getMessageSource().getMessage("label_file_location", null, "Location", locale), "location", String.class.getName(), 100);
            } else if (columnName.equals("createdDate")) {
                builder.addColumn(getMessageSource().getMessage("label_file_createddate", null, "Created Date", locale), "createdDate", Calendar.class.getName(), 100);
            } else if (columnName.equals("createdBy")) {
                builder.addColumn(getMessageSource().getMessage("label_file_createdby", null, "Created By", locale), "createdBy", String.class.getName(), 100);
            } else if (columnName.equals("modifiedDate")) {
                builder.addColumn(getMessageSource().getMessage("label_file_modifieddate", null, "Modified Date", locale), "modifiedDate", Calendar.class.getName(), 100);
            } else if (columnName.equals("modifiedBy")) {
                builder.addColumn(getMessageSource().getMessage("label_file_modifiedby", null, "Modified By", locale), "modifiedBy", String.class.getName(), 100);
            }
        } catch (ColumnBuilderException e) {
            String errorMessage = getMessageSource().getMessage("error_exportingErrorException", new Object[] { StringUtils.substringAfterLast(fileName, ".").toUpperCase() }, String.format("Error while exporting data to StringUtils file", StringUtils.substringAfterLast(fileName, ".").toUpperCase()), locale);
            throw new ExportingErrorException(errorMessage);
        } catch (ClassNotFoundException e) {
            String errorMessage = getMessageSource().getMessage("error_exportingErrorException", new Object[] { StringUtils.substringAfterLast(fileName, ".").toUpperCase() }, String.format("Error while exporting data to StringUtils file", StringUtils.substringAfterLast(fileName, ".").toUpperCase()), locale);
            throw new ExportingErrorException(errorMessage);
        }
    }
}
