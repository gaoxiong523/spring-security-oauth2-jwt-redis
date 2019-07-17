package com.gaoxiong.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class Role implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private Integer id;

  private String roleName;

  
}