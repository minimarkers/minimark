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
