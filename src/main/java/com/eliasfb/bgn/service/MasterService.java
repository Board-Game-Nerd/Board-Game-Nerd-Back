package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.master.MasterDto;
import com.eliasfb.bgn.dto.master.MastersDto;
import com.eliasfb.bgn.mapper.MasterMapper;
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
    List<MasterDto> themesDto = this.mapper.themeToMasterDto(this.themeRepository.findAll());
    List<MasterDto> complexitiesDto =
        this.mapper.complexityToMasterDto(this.complexityRepository.findAll());
    List<MasterDto> locationsDto =
        this.mapper.locationToMasterDto(this.locationRepository.findAll());
    List<MasterDto> mediaDto = this.mapper.mediumToMasterDto(this.mediumRepository.findAll());
    List<MasterDto> stylesDto = this.mapper.styleToMasterDto(this.styleRepository.findAll());
    List<MasterDto> victoriesDto = this.mapper.victoryToMasterDto(this.victoryRepository.findAll());

    return new MastersDto(
        themesDto, complexitiesDto, locationsDto, mediaDto, stylesDto, victoriesDto);
  }
}
