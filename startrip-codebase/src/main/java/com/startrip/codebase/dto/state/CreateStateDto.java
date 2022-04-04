package com.startrip.codebase.dto.state;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateStateDto {
    private UUID stateID;
    private Integer stateNum;
}
