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
		log.info("����ϱ� : " + board);
		mapper.insertSelectKey(board);
		System.out.println("bno�޾ƿ��� : " + board.getBno());
		log.info("bno�޾ƿ��� : " + board.getBno());
		//÷���̹����� ������ �׳� ����
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {return;}
		board.getAttachList().forEach(attach ->{
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("��ȸ�ϱ� : " + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("�����ϱ� : " + board);
		//bno��ȣ�� �ش��ϴ� ÷������ �Խñ� ����
		attachMapper.deleteAll(board.getBno());
		//board���̺� ����
		boolean modifyResult = mapper.update(board) == 1;
		//���۹��� ÷������ �׸��� �����ͺ��̽��� ����ϱ�
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
		log.info("�����ϱ� : " + bno);
		//bno�� �ش��ϴ� ÷������ �Խñ� �����ϱ�
		attachMapper.deleteAll(bno);
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("����Ʈ ���");
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("÷�θ���Ʈ �Խñ� ��ȣ : " + bno);
		return attachMapper.findByBno(bno); 
	}
	
}
