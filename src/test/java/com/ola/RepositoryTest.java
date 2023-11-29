package com.ola;

import java.util.Date;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ola.entity.Basket;
import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.OrderList;
import com.ola.entity.Product;
import com.ola.entity.Reply;
import com.ola.entity.Role;
import com.ola.entity.TradeBoard;
import com.ola.repository.BasketRepository;
import com.ola.repository.CommunityRepository;
import com.ola.repository.MemberRepository;
import com.ola.repository.OrderListRepository;
import com.ola.repository.ProductRepository;
import com.ola.repository.ReplyRepository;
import com.ola.repository.TradeBoardRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private BasketRepository basketRepo;

	@Autowired
	private OrderListRepository orderRepo;

	@Autowired
	private TradeBoardRepository tradeRepo;

	@Autowired
	private CommunityRepository communityRepo;

	@Autowired
	private ReplyRepository replyRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Disabled
	@Test
	public void createProduct() {
		IntStream.rangeClosed(1, 13).forEach(i -> {
			Product product = Product.builder().productName("흰 티셔츠" + i).prodCategory(1).price(15000L).prodSize("L")
					.salesQuantity(0L).inventory(1000).image(null).build();

			productRepo.save(product);
		});
	}

	@Disabled
	@Test
	public void createMember() {
		Member member = Member.builder().address("경기 부천시 소사구").detailedAddress("경인로216번길 105")
				.email("jangsh4752@naver.com").memberId("member1").password(encoder.encode("1111")).name("장선호")
				.phoneNumber("010-1111-1111").role(Role.ROLE_MEMBER).build();

		memberRepo.save(member);
	}

	@Disabled
	@Test
	public void createBasket() {
		// Assuming there is a product with id 1 in the database
		Product product = productRepo.findById(1L).orElse(null);

		if (product != null) {
			Basket basket = Basket.builder().product(product).quantity(1).build();

			basketRepo.save(basket);
		}
	}

	@Disabled
	@Test
	public void createorder() {
		Member member = memberRepo.findById("member1").orElse(null);
		Product product = productRepo.findById(1L).orElse(null);

		OrderList order = OrderList.builder().product(product).member(member).orderDate(new Date()).orderQuantity(1)
				.build();

		orderRepo.save(order);
	}
	@Disabled
	@Test
	public void createTradeBoard() {
		Member member = memberRepo.findById("member1").orElse(null);

		TradeBoard tradeBoard = TradeBoard.builder().member(member).tradeType(1).content("이거 좋음").title("장갑")
				.registrationDate(new Date()).progressStatus(3).build();

		tradeRepo.save(tradeBoard);

	}

	@Disabled
	@Test
	public void createCommunity() {
		Member member = memberRepo.findById("member1").orElse(null);

		Community community = Community.builder().member(member).title("게시글입니당").content("게시글 내용입니당").viewCount(1)
				.commentCount(0).likeCount(0).regDate(new Date()).build();

		communityRepo.save(community);

	}

	@Disabled
	@Test
	public void createReply() {
		Community community = communityRepo.findById(1L).orElse(null);
		Member member = memberRepo.findById("member1").orElse(null);

		Reply reply = Reply.builder().community(community).member(member).content("댓글입니다.").build();

		replyRepo.save(reply);

	}
	@Disabled
	@Test
   public void testDataInsert() {
	   Member member = Member.builder()
			   .memberId("member1")
			   .password(encoder.encode("1111"))
			   .name("이순신")
			   .email("sslee@email.com")
			   .phoneNumber("010-1111-1234")
			   .address("경기 부천시 소사구 경인로216번길")
			   .detailedAddress("105, 1동 204호")
			   .role(Role.ROLE_MEMBER)
			   .build();
	   
	   memberRepo.save(member);
   }
}