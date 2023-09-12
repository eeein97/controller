package org.green.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	private String type;
	private String keyword;
	
	//생성자를 통해서 기본값 1페이지, 10개로 지정
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	//검색조건을 배열로 만들어서 리턴(한번에 처리하기 위함)
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
}
