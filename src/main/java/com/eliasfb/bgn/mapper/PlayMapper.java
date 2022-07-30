package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.play.CreatePlayDto;
import com.eliasfb.bgn.model.Play;
import com.eliasfb.bgn.model.PlayPlayerRel;
import com.eliasfb.bgn.model.PlayPlayerRelId;
import com.eliasfb.bgn.model.Player;
import com.eliasfb.bgn.openapi.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.eliasfb.bgn.mapper.GameMapper.BACKEND_HOST;
import static com.eliasfb.bgn.service.DateService.STANDARD_DATE_FORMAT;

@Mapper(componentModel = "spring")
public abstract class PlayMapper {

  @Autowired private PlayerMapper playerMapper;

  public List<PlayDto> playsToPlaysDto(List<Play> plays) {
    return plays.stream().map(di -> playToPlayDto(di)).collect(Collectors.toList());
  }

  @Mapping(source = "players", target = "players", ignore = true)
  @Mapping(source = "date", target = "date", ignore = true)
  abstract PlayDto basicPlayToPlayDto(Play play);

  public PlayDto playToPlayDto(Play play) {
    PlayDto playDto = basicPlayToPlayDto(play);
    playDto.setPlayers(
        play.getPlayers().stream()
            .map(
                player ->
                    new PlayPlayerDto()
                        .player(this.playerMapper.playerToPlayerDto(player.getId().getPlayer()))
                        .score(player.getScore())
                        .winner(player.isWinner()))
            .collect(Collectors.toList()));
    playDto.setGame(new BasicGameInfoDto());
    playDto.getGame().setId(play.getGame().getId());
    playDto.getGame().setName(play.getGame().getName());
    playDto
        .getGame()
        .setImageUrl(
            play.getGame().getImages().stream()
                .map(g -> BACKEND_HOST + "/images/" + g.getName())
                .findFirst()
                .orElse(null));
    playDto.setDate(STANDARD_DATE_FORMAT.format(play.getDate()));
    return playDto;
  }

  @Mapping(source = "players", target = "players", ignore = true)
  @Mapping(source = "date", target = "date", ignore = true)
  @Mapping(source = "game.scorable", target = "scorable")
  abstract PlayDetailDto basicPlayToPlayDetailDto(Play play);

  public PlayDetailDto playToPlayDetailDto(Play play) {
    PlayDetailDto playDetailDto = basicPlayToPlayDetailDto(play);
    playDetailDto.setPlayers(
        play.getPlayers().stream()
            .map(
                player ->
                    new PlayDetailPlayerDto()
                        .player(this.playerMapper.playerToPlayerDto(player.getId().getPlayer()))
                        .score(player.getScore())
                        .winner(player.isWinner()))
            .collect(Collectors.toList()));
    playDetailDto.setGame(new BasicGameInfoDto());
    playDetailDto.getGame().setId(play.getGame().getId());
    playDetailDto.getGame().setName(play.getGame().getName());
    playDetailDto
        .getGame()
        .setImageUrl(
            play.getGame().getImages().stream()
                .map(g -> BACKEND_HOST + "/images/" + g.getName())
                .findFirst()
                .orElse(null));
    playDetailDto.setDate(STANDARD_DATE_FORMAT.format(play.getDate()));
    return playDetailDto;
  }

  abstract Play basicCreatePlayToPlay(CreatePlayDto playDto);

  public Play createPlayToPlay(CreatePlayDto playDto) {
    Play play = basicCreatePlayToPlay(playDto);
    play.setPlayers(
        playDto.getPlayers().stream()
            .map(
                player ->
                    new PlayPlayerRel(
                        new PlayPlayerRelId(play, new Player(player.getPlayerId())),
                        player.getScore(),
                        player.isWinner()))
            .collect(Collectors.toList()));
    return play;
  }
}
