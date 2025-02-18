package com.vimouna.demo.handtopic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {

  /** response timestamp. */
  private long timestamp;
  private String status;
  private String message;
  private String exception;
}
