package org.green.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.green.domain.BoardVO;
import org.green.domain.Criteria;

public interface BoardMapper {
	//�߻�޼ҵ�
	@Select("Select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	
	//create -> insertó��
	public void insert(BoardVO vo);
	
	//read -> selectó��
	public BoardVO read(Long bno);
	
	//delete -> deleteó��
	public int delete(Long bno);
	
	//update -> updateó��
	public int update(BoardVO board);
	
	//����¡ ����ؼ� ��ȸ
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//��ü ������ ����
	public int getTotalCount(Criteria cri);
	
	//insert2 �޼ҵ忡 ��ϵ� ��ȣ �ޱ�
	public void insertSelectKey(BoardVO vo);

	//�˻� ����
	//Map <Ű: ��Ʈ��, ��: mapŸ��>
	public List<BoardVO> searchTest(Map<String, Map<String,String>> map);
}
