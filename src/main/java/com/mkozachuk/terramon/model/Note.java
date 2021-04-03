package com.mkozachuk.terramon.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Note implements Exportable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private Date addAt;

    @Override
    public String[] getTableHeaders() {
        return new String[]{"ID", "Add Date", "Title", "Text"};
    }

    @Override
    public String[] getNameMapping() {
        return new String[]{"id", "addAt", "title", "text"};
    }
}
