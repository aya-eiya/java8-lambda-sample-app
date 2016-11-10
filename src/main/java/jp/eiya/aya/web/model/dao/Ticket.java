package jp.eiya.aya.web.model.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private Integer id;
}
