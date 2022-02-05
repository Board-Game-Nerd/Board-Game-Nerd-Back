package com.eliasfb.bgn.dto.score;

public enum ChallengeTypeEnum {
  COUNTPLAYS("COUNT-PLAYS"),
  PLAYEVERYGAME("PLAY-EVERY-GAME"),
  HINDEX("H-INDEX");

  private String bdType;

  ChallengeTypeEnum(String bdType) {
    this.bdType = bdType;
  }

  public String getBdType() {
    return bdType;
  }

  public ChallengeTypeEnum getByBdType(String bdType) {
    for (ChallengeTypeEnum challengeTypeEnum : ChallengeTypeEnum.values()) {
      if (challengeTypeEnum.getBdType().equals(bdType)) {
        return challengeTypeEnum;
      }
    }
    return null;
  }
}
