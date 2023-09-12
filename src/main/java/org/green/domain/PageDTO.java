package org.green.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private Criteria cri;
	
	//생성자
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		this.endPage = (int) (Math.ceil(cri.getPageNum()/10.0))* 10;
		this.startPage = this.endPage - 9;
		//전체 마지막 페이지 번호
		int realEnd = (int) (Math.ceil((total*1.0)/cri.getAmount()));
		//마지막 페이지 보다 페이징마지막번호가 크면 페이징 마지막 번호에 실제 마지막 번호를 할당
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		//시작페이지 번호가 1보다 크면 prev는 true
		this.prev = this.startPage > 1;
		//마지막페이지 번호가 실제 마지막 페이지 번호보다 작으면 next true
		this.next = this.endPage < realEnd;
	}
	
}
