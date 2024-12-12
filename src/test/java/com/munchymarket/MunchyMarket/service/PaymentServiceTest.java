//package com.munchymarket.MunchyMarket.service;
//
//import com.munchymarket.MunchyMarket.request.PaymentRequest;
//import com.stripe.model.PaymentIntent;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.logging.LoggerFactory;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//
//@SpringBootTest
//class PaymentServiceTest {
//
//    @Autowired
//    private PaymentService paymentService;
//
//
//    /**
//     * Mockito.mock: 객체를 mocking 하여 실제 구현이 없는 가짜 객체를 생성한다
//     *               테스트에서 의존성 있는 객체를 모방하여 동작을 정의하거나 호출 기록을 추적할 수 있다.
//     *
//     * Mockito.when: 특정 메서드의 호출에 대해 Mock 객체의 동작을 정의함
//     *               즉, 어떤 메서드를 호출했을떄 가짜 값을 리턴하도록 설정함.
//     *
//     * Mockito.thenReturn: when 과 함께 사용되며, 특정 메서드 호출에 대해 반환값을 지정함
//     *
//     * Mockito.mockStatic: static 메서드를 mocking 할때 사용.
//     *                     일반 객체를 mocking 할떄와 유사하지만, static 메서드를 테스트할 수 있도록 해줌.
//     *
//     * Mockito.any: when 이나 verify 에서 메서드 호출에 대한 조건을 설정할 때, 어떤 값이 와도 상관없이 매칭되도록 함.
//     */
//
//    @Test
//    public void paymentServiceTest() throws Exception {
//
//        PaymentIntent mockPaymentIntent = Mockito.mock(PaymentIntent.class);
//
//
//        Mockito.when(mockPaymentIntent.getId()).thenReturn("pi_1JQ5Zv2eZvKYlo2C5J9J9J9J");
//        Mockito.when(mockPaymentIntent.getAmount()).thenReturn(5000L); // 50 JPY
//        // mockPaymentIntent.getId() 를 호출하면 무조건 "pi_1JQ5Zv2eZvKYlo2C5J9J9J9J" 를 리턴하도록 동작을 정의한것임
//        // mockPaymentIntent.getAmount() 를 호출하면 무조건 5000L 을 리턴하도록 동작을 정의한것임
//
//        Mockito.when(mockPaymentIntent.getPaymentMethod()).thenReturn("pm_1QSXy8G1ONWjX9Kxeek4SJtf");
//
//
//        Map<String, String> mockMetadata = new HashMap<>();
//        mockMetadata.put("member_id", "1");
//
//        Mockito.when(mockPaymentIntent.getMetadata()).thenReturn(mockMetadata);
//
//        Mockito.mockStatic(PaymentIntent.class); // Stripe 의 create 정적 메서드를 테스트에서 가짜 동작으로 대체하기 위해 Mocking
//        Mockito.when(PaymentIntent.create(any(Map.class))).thenReturn(mockPaymentIntent);
//        // any(Map.class) 로 create 메서드에 어떤 형태의 Map 객체가 와도 상관없다고 설정함
//        // 반환 값은 무조건 mockPaymentIntent 를 받겠다고 정의
//
//        // PaymentRequest 설정
//        Map<String, String> metaData = new HashMap<>();
//        metaData.put("member_id", "1");
//
//        PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setAmount(5000L);
//        paymentRequest.setCurrency("jpy");
//        paymentRequest.setMetadata(metaData);
//        paymentRequest.setPaymentMethodId("pm_1QSXy8G1ONWjX9Kxeek4SJtf");
//
//
//        // 테스트 대상 호출
////        PaymentIntent paymentIntent = new PaymentService().createPaymentIntent(paymentRequest);
////        System.out.println("paymentIntent.getPaymentMethod() = " + paymentIntent.getPaymentMethod());
//
//        // 테스트
////        assertThat(paymentIntent.getId()).isEqualTo("pi_1JQ5Zv2eZvKYlo2C5J9J9J9J");
////        assertThat(paymentIntent.getAmount()).isEqualTo(5000L);
////        assertThat(paymentIntent.getMetadata().get("member_id")).isEqualTo("1");
//
//    }
//
//
//}