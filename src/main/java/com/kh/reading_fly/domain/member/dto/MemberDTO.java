package com.kh.reading_fly.domain.member.dto;

import lombok.*;

@Getter              // 모든멤버필드의 getter 메소드를 자동 생성해준다.
@Setter              // 모든멤버필드의 setter 메소드를 자동 생성해준다.
@NoArgsConstructor   // 디폴트 생성자 자동 생성해준다.
@AllArgsConstructor  // 모든멤버필드를 매개값으로 받아 생성자를 자동 만들어준다.
@ToString
@Data
public class MemberDTO {

  private String id;
  private String pw;
  private String name;
  private String nickname;
  private String email;


}