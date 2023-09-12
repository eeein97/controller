package org.green.service;

import java.util.List;

import org.green.domain.Criteria;
import org.green.domain.GalleryVO;
import org.green.mapper.BoardAttachMapper;
import org.green.mapper.GalleryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class GalleryServiceImpl implements GalleryService{
	@Setter(onMethod_= {@Autowired})
	private GalleryMapper gallerymapper;

	@Override
	public void register(GalleryVO gvo) {
		log.info("등록하기 :"+gvo);
		gallerymapper.insert(gvo);
		System.out.println("gno : "+gvo.getGno());
	}
	@Override
	public GalleryVO get(int gno) {
		GalleryVO gvo = gallerymapper.read(gno);
		return gvo;
	}

	@Override
	public boolean modify(GalleryVO gvo) {
		return gallerymapper.update(gvo) == 1;
	}

	@Override
	public boolean remove(int gno) {
		return gallerymapper.delete(gno) == 1;
	}

	@Override
	public List<GalleryVO> getList() {
		List<GalleryVO> galleryList = gallerymapper.getList();
		return galleryList;
	}
	
}
