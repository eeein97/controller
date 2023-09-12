package org.green.service;

import java.util.List;

import org.green.domain.BoardAttachVO;
import org.green.domain.BoardVO;
import org.green.domain.Criteria;
import org.green.mapper.BoardAttachMapper;
import org.green.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
	@Setter(onMethod_= {@Autowired})
	private BoardMapper mapper;
	
	@Setter(onMethod_= {@Autowired})
	private BoardAttachMapper attachMapper;

	@Override
	public void register(BoardVO board) {
		log.info("등록하기 : " + board);
		mapper.insertSelectKey(board);
		System.out.println("bno받아오기 : " + board.getBno());
		log.info("bno받아오기 : " + board.getBno());
		//첨부이미지가 없으면 그냥 리턴
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {return;}
		board.getAttachList().forEach(attach ->{
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("조회하기 : " + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("수정하기 : " + board);
		//bno번호에 해당하는 첨부파일 게시글 삭제
		attachMapper.deleteAll(board.getBno());
		//board테이블 수정
		boolean modifyResult = mapper.update(board) == 1;
		//전송받은 첨부파일 항목은 데이터베이스에 등록하기
		if(board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
		return mapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("삭제하기 : " + bno);
		//bno에 해당하는 첨부파일 게시글 삭제하기
		attachMapper.deleteAll(bno);
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("리스트 출력");
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("첨부리스트 게시글 번호 : " + bno);
		return attachMapper.findByBno(bno); 
	}
	
}
