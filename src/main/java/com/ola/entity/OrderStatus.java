package com.ola.entity;

public enum OrderStatus {
	ORDER_STAY("주문접수"), ORDER_COMPLETED("주문완료"), SHIPPING("배송중"), DELIVERED("배송완료")
	, ORDER_CANCEL("주문취소");

	private final String displayName;

	OrderStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
