package com.benfante.minimark.po;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * A tag
 *
 * @author lucio
 */
@Entity
public class Tag extends EntityBase {

    private String name;
    private Set<TagLink> tagLinks = new HashSet<TagLink>();

    public Tag() {}
    
    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy="tag")
    public Set<TagLink> getTagLinks() {
        return tagLinks;
    }

    public void setTagLinks(Set<TagLink> tagLinks) {
        this.tagLinks = tagLinks;
    }
}
