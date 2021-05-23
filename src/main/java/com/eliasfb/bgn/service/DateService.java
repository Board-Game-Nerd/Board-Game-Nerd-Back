package com.eliasfb.bgn.service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class DateService {

  public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
}
