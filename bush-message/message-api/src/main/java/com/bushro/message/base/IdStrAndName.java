package com.bushro.message.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdStrAndName implements Serializable {
    private static final long serialVersionUID = -8247431937045453249L;

    private String id;
    private String name;

}
