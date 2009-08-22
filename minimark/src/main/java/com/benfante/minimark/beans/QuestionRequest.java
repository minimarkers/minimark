package com.benfante.minimark.beans;

import java.io.Serializable;

/**
 * A bean for requesting a question with some characteristics.
 *
 * @author Lucio Benfante
 */
public class QuestionRequest implements Serializable {
    private String tag;
    private int howMany;

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag+"["+howMany+"]";
    }



}
