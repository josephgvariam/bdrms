package in.bigdash.rms.application.web.api.file;
import in.bigdash.rms.model.File;

import in.bigdash.rms.service.api.FileService;
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


@RestController
@RequestMapping(value = "/api/files/{file}", name = "FilesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilesItemJsonController {


    private FileService fileService;


    @Autowired
    public FilesItemJsonController(FileService fileService) {
        this.fileService = fileService;
    }


    public FileService getFileService() {
        return fileService;
    }


    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }


    @ModelAttribute
    public File getFile(@PathVariable("file") Long id) {
        File file = fileService.findOne(id);
        if (file == null) {
            throw new NotFoundException(String.format("File with identifier '%s' not found", id));
        }
        return file;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute File file) {
        return ResponseEntity.ok(file);
    }


    public static UriComponents showURI(File file) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FilesItemJsonController.class).show(file)).buildAndExpand(file.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute File storedFile, @Valid @RequestBody File file, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        file.setId(storedFile.getId());
        getFileService().save(file);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute File file) {
        getFileService().delete(file);
        return ResponseEntity.ok().build();
    }
}
