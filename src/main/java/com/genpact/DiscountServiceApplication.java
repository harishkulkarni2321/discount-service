package com.genpact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableBinding(Processor.class)
public class DiscountServiceApplication {

	Logger logger = LoggerFactory.getLogger(DiscountServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DiscountServiceApplication.class, args);
	}

	@Transformer(inputChannel = Processor.INPUT,outputChannel = Processor.OUTPUT)
	public List<Product> addDiscountToProduct(List<Product> products){
		List<Product> productlist =new ArrayList<>();
     for (Product product:products){
     	if(product.getPrice()>8000){
			productlist.add(calculateprice(product,10));
  		}
     	if(product.getPrice()>5000){
			productlist.add(calculateprice(product,5));
		}
	 }
     return  productlist;
	}

	private Product calculateprice(Product product,int prrcentage){
		double actualPrice=product.getPrice();
		double	 afterDiscountPrice = actualPrice - (actualPrice * prrcentage	 / 100);
		product.setPrice(afterDiscountPrice);
       logger.debug("product actual price is {} , after discout price is {}",actualPrice,afterDiscountPrice);
       return product;
	}
}
