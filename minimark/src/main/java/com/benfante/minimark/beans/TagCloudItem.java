package com.benfante.minimark.beans;

/**
 * An item of the tag cloud
 *
 * @author lucio
 */
public class TagCloudItem {

    private String name;
    private Long weight;

    public TagCloudItem(String name, Long weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

}
