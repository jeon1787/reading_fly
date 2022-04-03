package com.kh.reading_fly.domain.revirwtest.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewReqDTO {

	private Integer rnum;
	private Long risbn;
	private Integer rstar;
	private String rcontent;
	private String rid;
	private LocalDateTime rcdate;
	private LocalDateTime rudate;

	private String nickname;
//	private Integer rownum;
}
