package com.brandon.rest.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by brandon Lee on 2016-08-30.
 */
@Data
@NoArgsConstructor
public class NoticeMessage implements Serializable {
    private static final long serialVersionUID = -1664953615678207821L;
    @NotEmpty
    private String message;

    public NoticeMessage(String message) {
        this.message = message;
    }
}
