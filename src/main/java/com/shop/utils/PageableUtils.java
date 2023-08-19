package com.shop.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {
	public static Pageable getPageable(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable pageable;
		switch (sortBy) {
		case "name-asc": {
			pageable = PageRequest.of(pageNo, pageSize, Sort.by("name"));
			break;
		}
		case "name-desc": {
			pageable = PageRequest.of(pageNo, pageSize, Sort.by("name").descending());
			break;
		}
		case "price-asc": {
			pageable = PageRequest.of(pageNo, pageSize, Sort.by("price").and(Sort.by("name")));
			break;
		}
		case "price-desc": {
			pageable = PageRequest.of(pageNo, pageSize, Sort.by("price").descending().and(Sort.by("name")));
			break;
		}
		default:
			pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			break;
		}
		
		return pageable;
	}
}
