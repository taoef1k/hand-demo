package com.vimouna.demo.handtopic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
  @JsonIgnore
  @Column(nullable = false, updatable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
  @CreatedDate
  @DateTimeFormat(pattern = "dd MM yyyy HH:mm")
  protected LocalDateTime createdDate;

  @Column(nullable = false, length = 150)
  @JsonIgnore
  @CreatedBy
  protected U createdBy;

  @JsonIgnore
  @Column(columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
  @LastModifiedDate
  @DateTimeFormat(pattern = "dd MM yyyy HH:mm")
  protected LocalDateTime lastModifiedDate;

  @JsonIgnore @LastModifiedBy protected U lastModifiedBy;

  @JsonIgnore
  @DateTimeFormat(pattern = "dd MM yyyy HH:mm")
  protected LocalDateTime deletedAt;
}
