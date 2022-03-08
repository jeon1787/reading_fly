package com.kh.reading_fly.domain.member.svc;

import com.kh.reading_fly.domain.member.dao.MemberDAO;
import com.kh.reading_fly.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  // 회원가입
  @Override
  public void insertMember(MemberDTO memberDTO) {
    String id = memberDAO.insertMember(memberDTO);
  }

  // id 중복 비교
  @Override
  public boolean isExistId(String id) {
    return memberDAO.isExistId(id);
  }

  @Override
  public boolean isDeleteId(String id) {
    return memberDAO.isDeleteId(id);
  }

  // email 중복 비교
  @Override
  public boolean isExistEmail(String email) {
    return memberDAO.isExistEmail(email);
  }

  // nickname 중복 비교
  @Override
  public boolean isExistNickname(String nickname) {
    return memberDAO.isExistNickname(nickname);
  }

  // 로그인 인증
  @Override
  public MemberDTO login(String id, String pw) {
    return memberDAO.login(id, pw);
  }

  //비밀번호 일치여부 체크
  @Override
  public boolean isMember(String id, String pw) {
    return memberDAO.isMember(id, pw);
  }

  @Override
  public String admin(String id) {
    return memberDAO.admin(id);
  }

  // 사용자 정보 수정용 id 조회
  @Override
  public MemberDTO findByID(String id) {
    return memberDAO.findByID(id);
  }

  // 사용자 email 조회
  @Override
  public MemberDTO findByEmail(String email) {
    return memberDAO.findByEmail(email);
  }

  // 사용자 닉네임 조회
  @Override
  public MemberDTO findByNickname(String nickname) {
    return memberDAO.findByNickname(nickname);
  }

  // 회원 정보 수정
  @Override
  public void modifyMember(String id, MemberDTO memberDTO) {
    memberDAO.modifyMember(id, memberDTO);
  }

  // id 찾기
  @Override
  public String findIdMember(String email, String name) {
    return memberDAO.findIdMember(email, name);
  }

  // pw 찾기
  @Override
  public String findPwMember(String id, String name, String email) {
    return memberDAO.findPwMember(id, name, email);
  }

  // 회원탈퇴
  @Override
  public void deleteMember(String id) {
    memberDAO.deleteMember(id);
  }
}
