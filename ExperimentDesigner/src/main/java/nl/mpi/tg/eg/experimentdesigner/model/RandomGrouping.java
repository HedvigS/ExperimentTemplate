/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experimentdesigner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @since Jun 8, 2016 11:24:00 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
@Entity
public class RandomGrouping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String storageField;

    @ElementCollection
    private List<String> randomTags = new ArrayList<>();

    @XmlElement(name = "tag")
    public List<String> getRandomTags() {
        return randomTags;
    }

    public void addRandomTag(String tag) {
        randomTags.add(Stimulus.cleanTagString(tag));
    }

    public void setRandomTags(List<String> randomTags) {
        this.randomTags = randomTags;
    }

    @XmlAttribute
    public String getStorageField() {
        return storageField;
    }

    public void setStorageField(String storageField) {
        this.storageField = storageField;
    }
}
