package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.master.MasterDto;
import com.eliasfb.bgn.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterMapper {

  List<MasterDto> themeToMasterDto(List<Theme> theme);

  List<MasterDto> complexityToMasterDto(List<Complexity> complexity);

  List<MasterDto> locationToMasterDto(List<Location> location);

  List<MasterDto> mediumToMasterDto(List<Medium> medium);

  List<MasterDto> styleToMasterDto(List<Style> style);

  List<MasterDto> victoryToMasterDto(List<Victory> victory);
}
