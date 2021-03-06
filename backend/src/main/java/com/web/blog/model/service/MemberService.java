package com.web.blog.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.MemberDto;
import com.web.blog.model.PostDto;

public interface MemberService {

	// MemberController
	/** 회원 목록을 List로 반환 */
	public List<MemberDto> getAllMember() throws Exception;
	
	/** 회원의 권한 반환 */
	public String getRole(String email) throws Exception;

	/** 인자로 MemberDto를 생성 */
	public void join(MemberDto dto) throws Exception;

	/** 인자로 받은 email의 중복 여부 */
	public boolean emailCheck(String email);

	/** 인자로 받은 name의 중복 여부 */
	public boolean nameCheck(String name);

	/** 인자로 받은 email의 비밀번호를 반환 */
	public MemberDto findUserInfo(String email) throws Exception;

	/** 인자로 받은 MemberDto를 수정하고, 성공 여부를 반환 */
	public boolean updateIntro(MemberDto memberDto) throws Exception;

	/** 인자로 받은 MemberDto의 비밀번호를 수정하고, 성공 여부를 반환 */
	public boolean updatePwd(MemberDto memberDto) throws Exception;

	/** 인자로 받은 email의 member를 삭제하고, 성공 여부를 반환 */
	public boolean delete(String email) throws Exception;

	/** 인자로 받은 MemberDto로 프로필을 설정 */
	public void saveImg(MemberDto memberDto) throws IOException, SQLException;

	/** 인자로 받은 email의 프로필 사진을 삭제 */
	public void deleteImg(String email);

	// LoginController
	/** id, pwd와 일치하는 MemberDto를 반환 */
	public MemberDto login(MemberDto dto) throws Exception;

	/** 게시글 리스트 */
	public List<PostDto> getPostList(String email) throws Exception;

}
