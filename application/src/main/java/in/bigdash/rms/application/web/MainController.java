package in.bigdash.rms.application.web;
import io.springlets.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        return "index";
    }


    @GetMapping("/accessibility")
    public String accessibility(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        return "accessibility";
    }


    @RequestMapping(value = "/js/{template}.js", method = RequestMethod.GET)
    public String javascriptTemplates(@PathVariable("template") String template) {
        if (StringUtils.hasLength(template)) {
            return template.concat(".js");
        }
        throw new NotFoundException("File not found");
    }
}
