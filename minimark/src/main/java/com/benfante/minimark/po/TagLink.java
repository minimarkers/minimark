package com.benfante.minimark.po;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A link of a tag to another entity. Links are managed by subclasses.
 *
 * @author lucio
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "linkType", discriminatorType =
DiscriminatorType.STRING)
public class TagLink extends EntityBase implements Comparable<TagLink> {

    protected Tag tag;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int compareTo(TagLink o) {
        return this.getTag().getName().compareTo(o.getTag().getName());
    }
}
