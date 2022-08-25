package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterMapper {

  default String themeToString(Theme theme) {
    return theme.getName();
  }

  List<String> themeToString(List<Theme> theme);

  default String complexityToString(Complexity complexity) {
    return complexity.getName();
  }

  List<String> complexityToString(List<Complexity> complexity);

  default String locationToString(Location location) {
    return location.getName();
  }

  List<String> locationToString(List<Location> location);

  default String mediumToString(Medium medium) {
    return medium.getName();
  }

  List<String> mediumToString(List<Medium> medium);

  default String styleToString(Style style) {
    return style.getName();
  }

  List<String> styleToString(List<Style> style);

  default String victoryToString(Victory victory) {
    return victory.getName();
  }

  List<String> victoryToString(List<Victory> victory);
}
