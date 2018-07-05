package in.bigdash.rms.application.config;

import org.apache.log4j.MDC;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class MDCFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                MDC.put("user", authentication.getName());
            }

            HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
            if(session != null){
                MDC.put("sessionId", session.getId());
            }

            filterChain.doFilter(servletRequest, servletResponse);

        }finally {
            MDC.remove("user");
            MDC.remove("sessionId");
        }

    }

}
