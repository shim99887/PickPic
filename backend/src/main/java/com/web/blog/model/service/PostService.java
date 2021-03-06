package com.web.blog.model.service;

import java.util.List;
import java.util.Map;

import com.web.blog.model.ImgDto;
import com.web.blog.model.PostDto;
import com.web.blog.model.PostLikeDto;
import com.web.blog.model.PostParameterDto;

public interface PostService {

	// PostController

	/** 게시글 목록 : 검색 기능 */
	public List<PostDto> getList(PostParameterDto postParameterDto) throws Exception;
	
	/** like 게시글 */
	public PostDto getLikePost(int no) throws Exception;

	/** 하나의 게시글 정보 */
	public PostDto getDetail(int postNo) throws Exception;

	/** 마지막으로 작성한 게시글 번호 */
	public int getLastPostNo(String email) throws Exception;

	/** 게시글 작성 */
	public boolean write(PostDto postDto) throws Exception;

	/** 게시글 수정 */
	public boolean modify(PostDto postDto) throws Exception;

	/** 게시글 삭제 */
	public boolean delete(int postNo) throws Exception;

	/** 해당 게시글의 좋아요 수 */
	public int likeCount(int postNo) throws Exception;

	/** 좋아요 했는지 조회 */
	public int likeCheck(Map<String, Object> map) throws Exception;

	/** 좋아요 했었던 적이 있었을 때 가져올 정보 */
	public PostLikeDto likeInfo(Map<String, Object> map) throws Exception;

	/** 게시글에 처음으로 좋아요 누름 */
	public int insertLike(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 */
	public int like(Map<String, Object> map) throws Exception;

	/** 좋아요 체크 해제 */
	public int unlike(Map<String, Object> map) throws Exception;

	/** 게시글의 좋아요 수 갱신 */
	public int likeCntUp(int postNo) throws Exception;

	public int likeCntDown(int postNo) throws Exception;

	/** File insert to DB */
	public boolean uploadFile(ImgDto img) throws Exception;

	/** 게시글의 이미지 가져오기 */
	public List<ImgDto> getImages(int postNo) throws Exception;

	/** 사진 정보 */
	public ImgDto getImgInfo(String picNo) throws Exception;

	/** File DB에서 지우기 */
	public boolean deleteImage(String picNo) throws Exception;

	// PostTempController

	/** 임시저장 게시글 목록 */
	public List<PostDto> getTempList(PostParameterDto postParameterDto) throws Exception;

	/** 임시저장 게시글 수 */
	public int getTempCount(String email) throws Exception;

	/** 임시저장글 작성 */
	public boolean writeTemp(PostDto postDto) throws Exception;


}
