package service;

import dao.CartDAO;

import java.sql.SQLException;

public class CartService {

    private final CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    // 수량 수정
    public void updateToCart(int cartIdx, int newQuantity) throws SQLException {
        cartDAO.updateQuantity(cartIdx, newQuantity);
        System.out.println("수량 수정 완료");
    }

    // 장바구니에서 상품 삭제
    public void deleteFromCart(int cartIdx) throws SQLException {
        cartDAO.deleteCart(cartIdx);
        System.out.println("삭제 완료");
    }

    // 장바구니에서 상품 구매
    public void buyInCart(int memberIdx, int phoneIdx) throws SQLException {
        cartDAO.buyCart(memberIdx, phoneIdx);
        System.out.println("구매 완료");
    }

    // 장바구니에 상품 추가
    public void addToCart(int memberIdx, int phoneIdx, int quantity) throws SQLException {
        cartDAO.insertCart(memberIdx, phoneIdx, quantity);
        System.out.println("상품 추가 완료");
    }

    public static void main(String[] args) {

        CartService cartService = new CartService();

        try {
            cartService.updateToCart(2,3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
