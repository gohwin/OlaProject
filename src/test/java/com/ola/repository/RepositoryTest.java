package com.ola.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ola.entity.Basket;
import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.OrderList;
import com.ola.entity.Product;
import com.ola.entity.Role;
import com.ola.entity.TradeBoard;


@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private CommunityRepository commuRepo;
	
	@Autowired
	private TradeBoardRepository tradeRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private BasketRepository basketRepo;
	
	@Autowired
	private OrderListRepository orderRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	public void member2Insert() {
		Member member = Member.builder().name("안중근")
				.email("jgan@eamil.com")
				.phoneNumber("010-2222-2222")
				.address("서울시 광진구")
				.detailedAddress("건대2번 출구")
				.role(Role.ROLE_MEMBER)
				.memberId("member2")
				.password(encoder.encode("2222"))
				.zipNum("15314")
				.build();
		memberRepo.save(member);
	}
	
	@Test
	public void testAdminInsert() {
		Member member =
				Member.builder()
				.name("홍길동")
				.email("gdhong@email.com")
				.phoneNumber("010-1111-1111")
				.address("서울시 신림동")
				.detailedAddress("자이아파트 101동 101호")
				.role(Role.ROLE_ADMIN)
				.memberId("admin")
				.password(encoder.encode("1111"))
				.zipNum("14178")
				.build();
		  
		memberRepo.save(member);
		
		Member member1 =
				Member.builder()
				.name("이순신")
				.email("sslee@email.com")
				.phoneNumber("010-1111-1111")
				.address("서울시 강남구")
				.detailedAddress("롯데캐슬 101동 101호")
				.role(Role.ROLE_MEMBER)
				.memberId("member")
				.password(encoder.encode("1111"))
				.zipNum("14178")
				.build();
		  
		memberRepo.save(member1);

	}

	@Test
	public void testCommuBoard() {
		
		Member member = memberRepo.findById("member").get();
		IntStream.rangeClosed(1, 30).forEach(i -> {
		Community comm = Community.builder()
				.commentCount(0)
				.content("게시글입니다." + i)
				.likeCount(0)
				.member(member)
				.regDate(new Date())
				.viewCount(0)
				.title("테스트 게시글" + i)
				.build();
		
		commuRepo.save(comm);
		});
	}
	
	@Test
	public void testTradeBoard() {
		
		Member member = memberRepo.findById("member").get();
		IntStream.rangeClosed(1, 30).forEach(i -> {
			TradeBoard comm = TradeBoard.builder()
					.content("신발사요." + i)
					.tradeType(3)
					.member(member)
					.registrationDate(new Date())
					.title("신발 삽니다." + i)
					.build();
			
			tradeRepo.save(comm);
		});
	}
	
	@Test
	public void testProduct() {
		IntStream.rangeClosed(1, 14).forEach(i -> {
			Product product = Product.builder()
					.productName("top" + i)
					.prodCategory(1)
					.price(10000L)
					.prodSize("L")
					.salesQuantity(0L)
					.inventory(1000)
					.image("top"+i+".jpg")
					.build();
			prodRepo.save(product);
		});
		
		IntStream.rangeClosed(1, 14).forEach(i -> {
			Product product1 = Product.builder()
					.productName("bottom" + i)
					.prodCategory(2)
					.price(10000L)
					.prodSize("L")
					.salesQuantity(0L)
					.inventory(1000)
					.image("bottom"+i+".jpg")
					.build();
			prodRepo.save(product1);
		});
		
		IntStream.rangeClosed(1, 15).forEach(i -> {
			Product product3 = Product.builder()
					.productName("shoes"+i)
					.prodCategory(3)
					.price(25000L)
					.prodSize("265")
					.salesQuantity(0L)
					.inventory(1000)
					.image("shoes"+i+".jpg")
					.build();
			prodRepo.save(product3);
		});
		
		IntStream.rangeClosed(1, 15).forEach(i -> {
		Product product4 = Product.builder()
				.productName("etc"+i)
				.prodCategory(4)
				.price(15000L)
				.prodSize("f")
				.salesQuantity(0L)
				.inventory(1000)
				.image("etc"+i+".jpg")
				.build();
		prodRepo.save(product4);
		});
	}
	

	@Test
	public void testBasket() {

	    Product product = prodRepo.findById(1L).get();
	    Product product2 = prodRepo.findById(2L).get();

	    Member member = memberRepo.findById("member").get();
	    
	  
	        Basket basket = Basket.builder()
	            .member(member)
	            .build();

	        basket.getProducts().add(product); // 상품과 수량을 추가합니다
	        basket.getProducts().add(product2); // 상품과 수량을 추가합니다
	        basket.addProduct(product, 1); // 상품과 수량을 추가합니다
	        basket.addProduct(product2, 3); // 상품과 수량을 추가합니다
	        
	        basketRepo.save(basket);
	    
	}
	
	@Test
	public void testOrderList() {
	    Member member = memberRepo.findById("member").get();

	    IntStream.rangeClosed(1, 30).forEach(i -> {

	        Map<Long, Integer> productQuantities = new HashMap<>();
	        productQuantities.put(1L, 2); // Assume product number 1 with quantity 2
	        productQuantities.put(2L, 3); // Assume product number 2 with quantity 3

	        OrderList orderList = OrderList.builder()
	                .member(member)
	                .orderDate(new Date()) // Set the current date for the order date
	                .productQuantities(productQuantities)
	                .build();

	        orderRepo.save(orderList);
	    });
	}



}
