package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DisCountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {
    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DisCountService.class);

        DisCountService disCountService = ac.getBean(DisCountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int disCountPrice = disCountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(disCountService).isInstanceOf(DisCountService.class);
        assertThat(disCountPrice).isEqualTo(1000);

        int rateDiscountPrice = disCountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);

    }
    static class DisCountService {
        private final Map<String, DisCountPolicy> policyMap;
        private final List<DisCountPolicy> policies;
        public DisCountService(Map<String, DisCountPolicy> policyMap, List<DisCountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }
        public int discount(Member member, int price, String disCountCode) {
            DisCountPolicy disCountPolicy = policyMap.get(disCountCode);
            System.out.println("disCountCode = " + disCountCode);
            System.out.println("disCountPolicy = " + disCountPolicy);
            return disCountPolicy.discount(member, price);
        }
    }
}
