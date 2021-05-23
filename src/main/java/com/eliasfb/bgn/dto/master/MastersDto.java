package com.eliasfb.bgn.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MastersDto {
    private List<MasterDto> themes;
    private List<MasterDto> complexities;
    private List<MasterDto> locations;
    private List<MasterDto> media;
    private List<MasterDto> styles;
    private List<MasterDto> victories;
}
