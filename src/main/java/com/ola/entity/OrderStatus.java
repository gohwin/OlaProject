package com.ola.entity;

public enum OrderStatus {
	ORDER_COMPLETED("주문완료"), SHIPPING("배송중"), DELIVERED("배송완료");

	private final String displayName;

	OrderStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
