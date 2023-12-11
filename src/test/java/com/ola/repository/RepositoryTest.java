package com.ola.repository;

import java.util.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.ola.entity.Basket;
import com.ola.entity.Community;
import com.ola.entity.Member;
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
	private PasswordEncoder encoder;
	
	@Disabled
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
	
	@Disabled
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

	@Disabled
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
	
	@Disabled
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
	
	@Disabled
	@Test
	public void testProduct() {
		IntStream.rangeClosed(1, 5).forEach(i -> {
			Product product = Product.builder()
					.productName("임시상품" + i)
					.prodCategory(4)
					.price(30000L)
					.prodSize("XL")
					.salesQuantity(0L)
					.inventory(1000)
					.build();
			prodRepo.save(product);
		});
		
		Product product2 = Product.builder()
				.productName("테스트")
				.prodCategory(3)
				.price(15000L)
				.prodSize("XL")
				.salesQuantity(0L)
				.inventory(1000)
				.build();
		prodRepo.save(product2);
	}
	
	@Disabled
	@Test
	public void testBasket() {
	    Product product = prodRepo.findById(6L).get();
	    Product product2 = prodRepo.findById(3L).get();
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



}
