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

import java.io.IOException;
import java.io.StringReader;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * A converter from HTML to FO.
 *
 * @author Lucio Benfante (<A HREF="mailto:benfante@dei.unipd.it">benfante@dei.unipd.it</A>)
 * @version $Id: HTMLFOConverter.java,v 307ad60f2f38 2009/09/04 07:37:53 lucio $
 */
public class HTMLFOConverter {
    private StringBuffer foString;
    
    /** Creates a new instance of HTMLFOConverter */
    public HTMLFOConverter() {
    }
    
    public String convert(String htmlString) {
        MyParserCallback callback = new HTMLFOConverter.MyParserCallback();
        StringReader reader = new StringReader(htmlString);
        this.foString = new StringBuffer();
        try {
            (new ParserDelegator()).parse(reader, callback, false);            
        } catch (IOException ioe) {}
        return this.foString.toString();
    }
    
    public class MyParserCallback extends HTMLEditorKit.ParserCallback {
        public void handleText(char[] data, int pos) {
            String s = new String(data);
            s = s.replaceAll("&", "&amp;");
            s = s.replaceAll("<", "&lt;");
            s = s.replaceAll(">", "&gt;");
            s = s.replaceAll("\"", "&quot;");
            foString.append(s);
        }
        
        public void handleComment(char[] data, int pos) {
        }
        
        public void handleStartTag(HTML.Tag t, 
                                   MutableAttributeSet a, int pos) {
            if (t == HTML.Tag.B) {
                foString.append("<fo:inline font-weight=\"bold\">");
            } else if (t == HTML.Tag.I) {
                foString.append("<fo:inline font-style=\"italic\">");
            } else if (t == HTML.Tag.P) {
                foString.append("<fo:block>");
            }
        }
        
        public void handleEndTag(HTML.Tag t, int pos) {
            if (t == HTML.Tag.B) {
                foString.append("</fo:inline>");
            } else if (t == HTML.Tag.I) {
                foString.append("</fo:inline>");
            } else if (t == HTML.Tag.P) {
                foString.append("</fo:block>");
            }
        }
        
        public void handleSimpleTag(HTML.Tag t, 
                                    MutableAttributeSet a, int pos) {
            if (t == HTML.Tag.BR) {
                foString.append("<fo:block></fo:block>");
            }
        }
    }
    
}
