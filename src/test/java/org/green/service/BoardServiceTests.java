package org.green.service;

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
public class BoardServiceTests {
	@Setter(onMethod_ = {@Autowired})
	private BoardService service;
	
	//���
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("���񽺿��� �ۼ��ϴ� ����");
		board.setContent("���񽺿��� �ۼ��ϴ� ����");
		board.setWriter("Bluuue");
		service.register(board);
		log.info("����");
	}
	
	//��ϸ���Ʈ
	@Test
	public void testGetList() {
		service.getList(new Criteria(2,10)).forEach(board -> log.info(board));
	}
	
	//��ȸ
	@Test
	public void testGet() {
		log.info(service.get(4L));
	}
	
	//����
	public void TestDelete() {
		log.info(service.remove(2L));
	}
	
	//����
	@Test
	public void testUpdate() {
		BoardVO board = service.get(6L);
		if(board==null) {return;}
		board.setTitle("�������");
		log.info(service.modify(board));
	}
}
