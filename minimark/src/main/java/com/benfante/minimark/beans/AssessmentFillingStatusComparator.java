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
package com.benfante.minimark.beans;

import com.benfante.minimark.po.AssessmentFilling;
import java.util.Comparator;

/**
 * A comparator for sorting assessement filling by the calculated current status.
 *
 * @author Lucio Benfante
 */
public class AssessmentFillingStatusComparator implements
        Comparator<AssessmentFilling> {

    public int compare(AssessmentFilling o1, AssessmentFilling o2) {
        int result = o1.getCurrentStateOrdered() - o2.getCurrentStateOrdered();
        if (result == 0 && o1.getLastName() != null) {
            result = o1.getLastName().compareToIgnoreCase(o2.getLastName());
        }
        if (result == 0 && o1.getFirstName() != null) {
            result = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
        }
        if (result == 0 && o1.getIdentifier() != null) {
            result = o1.getIdentifier().compareToIgnoreCase(o2.getIdentifier());
        }
        return result;
    }
}
