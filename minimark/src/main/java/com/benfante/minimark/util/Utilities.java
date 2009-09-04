/**
 * Copyright (C) 2009 Lucio Benfante <lucio.benfante@gmail.com>
 *
 * This file is part of minimark Web Application.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
