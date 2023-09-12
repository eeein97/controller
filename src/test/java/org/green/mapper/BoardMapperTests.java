package org.green.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.green.domain.BoardVO;
import org.green.domain.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_ = {@Autowired})
	private BoardMapper mapper;
	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}
	//insertó�� �׽�Ʈ
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("�����ۼ��ϴ� ����");
		board.setContent("�����ۼ��ϴ� ����");
		board.setWriter("OJEJE");
		mapper.insert(board);
		log.info(board);		
	}
	
	//selectó�� �׽�Ʈ
	@Test
	public void testRead() {
		BoardVO board = mapper.read(5L);
		log.info(board);
	}
	
	//deleteó�� �׽�Ʈ
	@Test
	public void testDelete() {
		log.info("Delete count : " +  mapper.delete(3L));
	}
	
	//updateó�� �׽�Ʈ
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setBno(1L);
		board.setTitle("������ ����");
		board.setContent("������ ����");
		board.setWriter("������ Ojeje");
		int count = mapper.update(board);
		log.info(count);
			
	}
	
	//����¡ ���ڵ� �׽�Ʈ
	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(4);
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
	//�˻� �׽�Ʈ
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("������");
		cri.setType("TC");
		List<BoardVO> list = mapper.getListWithPaging(cri);
		log.info(list);
	}

}
