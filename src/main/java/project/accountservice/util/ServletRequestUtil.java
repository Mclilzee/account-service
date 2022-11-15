package project.accountservice.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletRequestUtil {

    public static String getUrl() {
        ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (request == null) {
            return "/";
        }
        return request.getRequest().getRequestURI();
    }
}
