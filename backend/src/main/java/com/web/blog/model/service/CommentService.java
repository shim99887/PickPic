package com.web.blog.model.service;

import java.util.List;
import java.util.Map;

import com.web.blog.model.CommentDto;
import com.web.blog.model.CommentLikeDto;

public interface CommentService {
	/** 댓글 호출 */
	public CommentDto getOne(int commentNo) throws Exception;

	/** 댓글 목록 호출 */
	public List<CommentDto> commentList(Map<String, Object> map) throws Exception;

	/** 댓글 목록 무한 스크롤링 */
	public List<CommentDto> infiniteScrollDown(Map<String, Object> map) throws Exception;

	/** 인자로 받은 CommentDto를 작성하고, 성공 여부를 반환 */
	public boolean commentWrite(CommentDto dto) throws Exception;

	/** 인자로 받은 CommentDto를 수정하고, 성공 여부를 반환 */
	public boolean commentUpdate(CommentDto dto) throws Exception;

	/** 인자로 받은 commentNo의 댓글을 삭제하고, 성공 여부를 반환 */
	public boolean commentDelete(int commentNo) throws Exception;

	/** 해당 댓글의 좋아요 수 */
	public int likeCount(int commentNo) throws Exception;

	/** 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws Exception;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public CommentLikeDto likeInfo(Map<String, Object> map) throws Exception;

	/** 게시글에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/** 게시글의 좋아요 수 갱신Up */
	public int likeCntUp(int commentNo) throws Exception;

	/** 게시글의 좋아요 수 갱신Down */
	public int likeCntDown(int commentNo) throws Exception;

}
