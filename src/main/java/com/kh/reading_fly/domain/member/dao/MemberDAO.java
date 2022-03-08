package com.kh.reading_fly.domain.member.dao;

import com.kh.reading_fly.domain.member.dto.MemberDTO;

public interface MemberDAO {

  // 회원가입
  String insertMember(MemberDTO memberDTO);

  // id 중복 비교
  boolean isExistId(String id);

  // 탈퇴 id 중복 비교
  boolean isDeleteId(String id);

  // email 중복 비교
  boolean isExistEmail(String email);

  // nickname 중복 비교
  boolean isExistNickname(String nickname);

  // 로그인 인증
  MemberDTO login(String id, String pw);

  //비밀번호 일치여부 체크
  boolean isMember(String id, String pw);

  //관리자 코드 로그인 확인
  String admin(String id);

  // 사용자  id 조회 또는 수정용 id 조회
  MemberDTO findByID(String id);

  // 사용자 email 조회 - 사용 확인 필요
  MemberDTO findByEmail(String email);

  // 사용자 닉네임 조회 -- 사용 확인 필요
  MemberDTO findByNickname(String nickname);

  // 회원 정보 수정
  void modifyMember(String id, MemberDTO memberDTO);

  // id 찾기
  String findIdMember(String email, String name);

  // pw 찾기
  String findPwMember(String id, String name, String email);

  // 회원탈퇴
  void deleteMember(String id);



}
