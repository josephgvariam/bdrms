package in.bigdash.rms.application.web.advices;

import io.springlets.web.mvc.util.concurrency.ConcurrencyException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class ConcurrencyExceptionManager {


    @ExceptionHandler(ConcurrencyException.class)
    public ModelAndView concurrencyException(final ConcurrencyException ex) {
        return ex.populateAndGetFormView();
    }


}
