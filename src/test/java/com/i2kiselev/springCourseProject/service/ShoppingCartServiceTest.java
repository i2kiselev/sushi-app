package com.i2kiselev.springCourseProject.service;

import com.i2kiselev.springCourseProject.model.AbstractProduct;
import com.i2kiselev.springCourseProject.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ShoppingCartServiceTest {
    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @InjectMocks
    @Spy
    private ShoppingCartService shoppingCartService;

    @Test
    void addProductToEmptyShoppingCart(){
        Product product = new Product();
        shoppingCartService.addProduct(product);
        assertThat(shoppingCartService.getProductsInCart()).containsEntry(product,1);
    }

    @Test
    void addTwoEqualProducts(){
        Product product = new Product();
        shoppingCartService.addProduct(product);
        shoppingCartService.addProduct(product);
        assertThat(shoppingCartService.getProductsInCart()).containsEntry(product,2);
    }


    @Test
    void addTwoDifferentProducts(){
        Product product = new Product();
        product.setId(1L);
        shoppingCartService.addProduct(product);
        Product product1 = new Product();
        product1.setId(2L);
        shoppingCartService.addProduct(product1);
        assertThat(shoppingCartService.getProductsInCart()).containsEntry(product,1);
        assertThat(shoppingCartService.getProductsInCart()).containsEntry(product1,1);
    }

    @Test
    void removeProductFromEmptyCart(){
        Product product = new Product();
        shoppingCartService.addProduct(product);
        shoppingCartService.removeProduct(product);
        shoppingCartService.removeProduct(product);
        assertThat(shoppingCartService.getProductsInCart().isEmpty());
    }

    @Test
    void removeProductFromNotEmptyCart(){
        Product product = new Product();
        product.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        shoppingCartService.addProduct(product);
        shoppingCartService.addProduct(product2);
        shoppingCartService.addProduct(product2);
        shoppingCartService.removeProduct(product2);
        assertThat(shoppingCartService.getProductsInCart()).containsEntry(product,1);
        assertThat(shoppingCartService.getProductsInCart()).containsEntry(product2,1);
    }
    @Test
    void removeProductFromCartWithOneProduct(){
        Product product = new Product();
        product.setId(1L);
        shoppingCartService.addProduct(product);
        shoppingCartService.removeProduct(product);
        assertThat(shoppingCartService.getProductsInCart()).isEmpty();
    }

    @Test
    void getListOfProducts(){
        Product product = new Product();
        product.setId(1L);
        shoppingCartService.addProduct(product);
        Map<AbstractProduct,Integer> products = shoppingCartService.getProductsInCart();
        assertThat(products).containsEntry(product,1);
    }

   /* @Test
    void checkout(){
        Product product = new Product();
        product.setId(1L);
        User user = new User();
        Order order = new Order();
        given(userService.getCurrentUser()).willReturn(user);
        doNothing().when(orderService).setTotal(order);
        doNothing().when(orderService).saveOrder(order);
        shoppingCartService.addProduct(product);
        shoppingCartService.checkout();
        assertThat(shoppingCartService.getProductsInCart()).isEmpty();
    }*/

    @Test
    void getTotal(){
        Product product = new Product();
        product.setPrice(100);
        shoppingCartService.addProduct(product);
        assertThat(shoppingCartService.getTotal()).isEqualTo(100);
    }
}


