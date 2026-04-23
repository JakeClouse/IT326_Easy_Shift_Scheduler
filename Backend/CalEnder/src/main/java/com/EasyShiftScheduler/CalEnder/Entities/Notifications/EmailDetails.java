package com.EasyShiftScheduler.CalEnder.Entities.Notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String recipient;
    private String msgBody;
    private String subject;
}
