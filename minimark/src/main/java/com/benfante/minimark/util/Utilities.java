package com.benfante.minimark.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;

/**
 * Defines useful functions.
 *
 * @author Lucio Benfante
 *
 */
public class Utilities {

    /**
     * Compose the base URL from an HttpRequest.
     */
    public static String getBaseUrl(HttpServletRequest req) {
        return "http://" + req.getServerName() + ":" + req.getServerPort() +
                req.getContextPath()+"/";
    }

    /**
     * Append a parameter to an URL.
     * 
     * @param sb The StringBuilder containing the URL where to append.
     * @param parameterName The name of the parameter
     * @param parameterValue The value of the parameter. It will be url encoded.
     * @param ifNotNull if true the parameter is appended only if its vale is not null.
     * @return The passed StringBuilder
     */
    public static StringBuilder appendUrlParameter(StringBuilder sb,
            String parameterName, String parameterValue, boolean ifNotNull)
            throws UnsupportedEncodingException {
        if (ifNotNull && parameterValue == null) {
            return sb;
        }
        if (sb.indexOf("?") == -1) {
            sb.append('?');
        }
        if (sb.charAt(sb.length() - 1) != '?') {
            sb.append('&');
        }
        sb.append(parameterName).append('=');
        if (parameterValue != null) {
            sb.append(URLEncoder.encode(parameterValue, "UTF-8"));
        }
        return sb;
    }

}
