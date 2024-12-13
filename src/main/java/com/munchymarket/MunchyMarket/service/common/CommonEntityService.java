package com.munchymarket.MunchyMarket.service.common;

import com.munchymarket.MunchyMarket.domain.Cart;
import com.munchymarket.MunchyMarket.domain.Coupon;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.repository.address.AddressRepository;
import com.munchymarket.MunchyMarket.repository.cart.CartRepository;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import com.munchymarket.MunchyMarket.repository.coupon.CouponRepository;
import com.munchymarket.MunchyMarket.repository.image.ImageRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.repository.packagingtype.PackagingTypeRepository;
import com.munchymarket.MunchyMarket.repository.payment.PaymentRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.repository.review.ReviewRepository;
import com.munchymarket.MunchyMarket.repository.sorttype.SortTypeRepository;
import com.munchymarket.MunchyMarket.repository.verificationCode.VerificationCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonEntityService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final SortTypeRepository sortTypeRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PackagingTypeRepository packagingTypeRepository;
    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;


//    public final <T> T findById(Long id, JpaRepository<T, Long> repository, String entityName) {
//        return repository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));
//    }


    public final Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    public final Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public final Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found"));
    }

    public final Cart findCartByMemberId(Long memberId) {
        return cartRepository.findCartByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }


}