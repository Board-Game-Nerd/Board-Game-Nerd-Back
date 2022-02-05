package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.challenge.ChallengeDetailDto;
import com.eliasfb.bgn.dto.challenge.ChallengeDto;
import com.eliasfb.bgn.model.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.eliasfb.bgn.service.DateService.STANDARD_DATE_FORMAT;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "dueDate", ignore = true)
  ChallengeDto basicChallengeToChallengeDto(Challenge challenge);

  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "dueDate", ignore = true)
  ChallengeDetailDto basicChallengeToChallengeDetailDto(Challenge challenge);

  default ChallengeDetailDto challengeToChallengeDetailDto(Challenge challenge) {
    ChallengeDetailDto challengeDetailDto = this.basicChallengeToChallengeDetailDto(challenge);
    challengeDetailDto.setCreatedDate(STANDARD_DATE_FORMAT.format(challenge.getCreatedDate()));
    if (challenge.getDueDate() != null) {
      challengeDetailDto.setDueDate(STANDARD_DATE_FORMAT.format(challenge.getDueDate()));
    }
    return challengeDetailDto;
  }

  default ChallengeDto challengeToChallengeDto(Challenge challenge) {
    ChallengeDto challengeDto = this.basicChallengeToChallengeDto(challenge);
    challengeDto.setCreatedDate(STANDARD_DATE_FORMAT.format(challenge.getCreatedDate()));
    if (challenge.getDueDate() != null) {
      challengeDto.setDueDate(STANDARD_DATE_FORMAT.format(challenge.getDueDate()));
    }
    return challengeDto;
  }

  default List<ChallengeDto> challengeListToChallengeListDto(List<Challenge> challenge) {
    return challenge.stream()
        .map(challenge1 -> this.challengeToChallengeDto(challenge1))
        .collect(Collectors.toList());
  }
}
