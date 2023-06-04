package com.example.util;

import com.example.listComponents.repository.UserListRepository;
import com.example.marketComponents.repository.ProductRepository;
import com.example.marketComponents.repository.SupermarketRepository;
import com.example.userComponents.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountUtil {
    private final AppUserRepository userRepository;
    private final ProductRepository productRepository;
    private final SupermarketRepository supermarketRepository;
    private final UserListRepository listRepository;
    @Autowired
    public CountUtil(AppUserRepository userRepository, ProductRepository productRepository, SupermarketRepository supermarketRepository, UserListRepository listRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.supermarketRepository = supermarketRepository;
        this.listRepository = listRepository;
    }


    public long getUserCont(){
        return userRepository.count ();
    }
    public long getProductCount(){
        return productRepository.count ();
    }
    public long getListCount(){
        return listRepository.count ();
    }
    public long getSupermarketCount(){
        return supermarketRepository.count ();
    }
}



