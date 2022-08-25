package com.eliasfb.bgn.service;

import com.eliasfb.bgn.mapper.MasterMapper;
import com.eliasfb.bgn.openapi.model.MastersDto;
import com.eliasfb.bgn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {
  @Autowired private ThemeRepository themeRepository;
  @Autowired private ComplexityRepository complexityRepository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private MediumRepository mediumRepository;
  @Autowired private StyleRepository styleRepository;
  @Autowired private VictoryRepository victoryRepository;
  @Autowired private MasterMapper mapper;

  public MastersDto findAll() {
    List<String> themesDto = this.mapper.themeToString(this.themeRepository.findByOrderByNameAsc());
    List<String> complexitiesDto =
        this.mapper.complexityToString(this.complexityRepository.findAll());
    List<String> locationsDto =
        this.mapper.locationToString(this.locationRepository.findByOrderByNameAsc());
    List<String> mediaDto =
        this.mapper.mediumToString(this.mediumRepository.findByOrderByNameAsc());
    List<String> stylesDto = this.mapper.styleToString(this.styleRepository.findByOrderByNameAsc());
    List<String> victoriesDto =
        this.mapper.victoryToString(this.victoryRepository.findByOrderByNameAsc());

    return new MastersDto()
        .themes(themesDto)
        .complexities(complexitiesDto)
        .locations(locationsDto)
        .media(mediaDto)
        .styles(stylesDto)
        .victories(victoriesDto);
  }
}
