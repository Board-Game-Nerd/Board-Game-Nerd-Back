package com.eliasfb.bgn.service;

import java.time.format.DateTimeFormatter;

public class DateService {

  public static final DateTimeFormatter BGN_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  public static final DateTimeFormatter BG_STATS_DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
}
