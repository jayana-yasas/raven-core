package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "contact_segment")
public class ContactSegment extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    public Contact contactId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "segment_id", referencedColumnName = "id")
    public Segment segmentId;

}
