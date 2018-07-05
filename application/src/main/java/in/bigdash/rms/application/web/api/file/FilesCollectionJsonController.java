package in.bigdash.rms.application.web.api.file;
import in.bigdash.rms.model.File;

import in.bigdash.rms.service.api.FileService;
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
@RequestMapping(value = "/api/files", name = "FilesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilesCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FileService fileService;


    @Autowired
    public FilesCollectionJsonController(FileService fileService) {
        this.fileService = fileService;
    }


    public FileService getFileService() {
        return fileService;
    }


    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<File>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<File> files = getFileService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(files);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FilesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody File file, BindingResult result) {
        log.debug("create: {}", file);
        if (file.getId() != null || file.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", file, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        File newFile = getFileService().save(file);
        log.debug("create saved: {}", file);
        UriComponents showURI = FilesItemJsonController.showURI(newFile);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<File> files, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(files.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(files.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getFileService().save(files);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<File> files, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(files.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(files.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getFileService().save(files);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getFileService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
