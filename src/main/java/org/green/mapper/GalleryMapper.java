package org.green.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.green.domain.BoardVO;
import org.green.domain.Criteria;
import org.green.domain.GalleryVO;

public interface GalleryMapper {
	
	public List<GalleryVO> getList();
	
	public void insert(GalleryVO gvo);
	
	public GalleryVO read(int gno);
	
	public int delete(int gno);
	
	public int update(GalleryVO gvo);
}
