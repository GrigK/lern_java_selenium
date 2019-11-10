import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductCardPage;

import org.junit.Test;

import org.openqa.selenium.WebElement;


public class AddProductToBasket extends TestBase {
    @Test
    public void AddThreeProductToBasketAndDelete(){
        /**
         * ) открыть страницу какого-нибудь товара
         * 2) добавить его в корзину
         * 3) подождать, пока счётчик товаров в корзине обновится
         * 4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза,
         *    чтобы в общей сложности в корзине было 3 единицы товара
         * 5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
         * 6) удалить все товары из корзины один за другим, после каждого удаления
         *    подождать, пока внизу обновится таблица
         */
        HomePage homePage = new HomePage(driver);
        homePage.open();
        for(int i = 0; i < 3; i++){
            WebElement product = homePage.getPopularProducts().get(i);
            String urlProduct = homePage.getProductLink(product);
            product.click();

            ProductCardPage productCardPage = new ProductCardPage(driver, urlProduct);
            productCardPage.selectAttribute(1);
            productCardPage.setQuantity(1);
            productCardPage.addToCart();

            homePage.open();
        }

        homePage.clickToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.emptyCart();
    }
}
