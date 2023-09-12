package org.green.domain;

import lombok.Data;

@Data
public class GalleryVO {
	private Long gno;
	private String title;
	private String writer;
	private String fileName;
	private String uploadPath;
	private String category;
	private String content;
	private String fullName;
}
