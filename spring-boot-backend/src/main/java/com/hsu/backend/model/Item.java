package com.hsu.backend.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Item")
public class Item {

    @Id
    @NotNull
    @ApiModelProperty(notes = "Id of a item",name="id",required=true,value="test id")
    private Long id;

    @ApiModelProperty(notes = "A combination",name="text",required=true,value="test name")
    private String text;

    public Item() {
    }

    public Item(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Item [text=")
                .append(text)
                .append(", id=")
                .append(id)
                .append("]");
        return builder.toString();
    }
}
