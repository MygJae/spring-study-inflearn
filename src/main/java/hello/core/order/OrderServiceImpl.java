package hello.core.order;

import hello.core.discount.DisCountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DisCountPolicy disCountPolicy;

/*
    public OrderServiceImpl(MemberRepository memberRepository, DisCountPolicy disCountPolicy) {
        this.memberRepository = memberRepository;
        this.disCountPolicy = disCountPolicy;
    }
*/
    /*
        @Autowired
        public void init(MemberRepository memberRepository, DisCountPolicy disCountPolicy) {
            this.memberRepository = memberRepository;
            this.disCountPolicy = disCountPolicy;
        }

        @Autowired(required = false)
        public void setMemberRepository(MemberRepository memberRepository) {
            System.out.println("memberRepository = " + memberRepository);
            this.memberRepository = memberRepository;
        }
        @Autowired
        public void setDiscountPolicy(DisCountPolicy disCountPolicy) {
            System.out.println("disCountPolicy = " + disCountPolicy);
            this.disCountPolicy = disCountPolicy;
        }
        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, DisCountPolicy disCountPolicy) {
            this.memberRepository = memberRepository;
            this.disCountPolicy = disCountPolicy;
        }
    */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = disCountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
