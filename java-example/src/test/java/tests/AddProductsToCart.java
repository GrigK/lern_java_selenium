package tests;

import org.junit.Test;

public class AddProductsToCart extends TestBaseNew {
    @Test
    public void canAddProductsToCart(){
        /**
         * используем PageObject Model
         *
         * 1) открыть страницу какого-нибудь товара
         * 2) добавить его в корзину
         * 3) подождать, пока счётчик товаров в корзине обновится
         * 4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза,
         *    чтобы в общей сложности в корзине было 3 единицы товара
         * 5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
         * 6) удалить все товары из корзины один за другим, после каждого удаления
         *    подождать, пока внизу обновится таблица
         */
        for(int i=0; i< 3; i++) {
            app.addProductToCart(app.getPopularProducts().get(i));
        }
        app.cleanCart();
    }
}
