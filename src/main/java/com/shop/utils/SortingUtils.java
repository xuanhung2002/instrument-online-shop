package com.shop.utils;

import java.util.Comparator;

import com.shop.dto.ItemDTO;

public class SortingUtils {

	public static Comparator<ItemDTO> getComparatorForSorting(String sortBy) {
		Comparator<ItemDTO> comparator;

		switch (sortBy) {
		case "name-asc": {
			comparator = Comparator.comparing(t -> t.getName());
			break;
		}
		case "name-desc": {
			comparator = Comparator.comparing(t -> t.getName(), Comparator.reverseOrder());
			break;
		}

		case "price-asc": {
			comparator = Comparator.comparing(ItemDTO::getPrice);
			break;
		}
		case "price-desc": {
			comparator = Comparator.comparing(ItemDTO::getPrice, Comparator.reverseOrder());
			break;
		}
		default:
			comparator = Comparator.comparing(t -> t.getName());
			break;
		}
		return comparator;
	}
}
