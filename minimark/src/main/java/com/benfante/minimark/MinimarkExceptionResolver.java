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
package com.benfante.minimark;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.web.ExceptionResolver;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Ovverride ExceptionResolver to define custom web application Exception.
 * Should be mapped in parancoe-servlet.xml
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public class MinimarkExceptionResolver extends ExceptionResolver {

    private static final Logger logger =
            Logger.getLogger(MinimarkExceptionResolver.class);
    private CommonsMultipartResolver multipartResolver;

    public CommonsMultipartResolver getMultipartResolver() {
        return multipartResolver;
    }

    public void setMultipartResolver(CommonsMultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest req,
            HttpServletResponse res, Object object, Exception e) {
        try {
            if (!interceptedWithMinimalLogging(e)) {
                logger.error("Unexpected exception", e);
            }
            logger.info("Exception requesting URL: " + req.getRequestURL().
                    toString());
            logger.info("  request from " + req.getRemoteHost() + "(" + req.
                    getRemoteAddr() + ")");
            return super.resolveException(req, res, object, e);
        } catch (Exception ex) {
            logger.error("Error resolving exception", ex);
        }
        return new ModelAndView("redirect:/home/500page.html");
    }

    private boolean interceptedWithMinimalLogging(Exception e) {
        boolean intercepted = false;
        // Discarding too generic exception, with unuseful stacktrace...so minimal log output.
        // Maybe some could be moved to the base class.
        if ((e instanceof HttpSessionRequiredException)
                || (e instanceof MultipartException)) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getLocalizedMessage(), e);
            } else {
                logger.error("[" + e.getClass().getName() + "] " + e.
                        getLocalizedMessage());
            }
            intercepted = true;
        }
        return intercepted;
    }
}
