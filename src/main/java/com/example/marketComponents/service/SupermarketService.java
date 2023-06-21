package com.example.marketComponents.service;

import com.example.marketComponents.controller.util.ProductDto;
import com.example.marketComponents.dto.SupermarketUserList;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.model.Supermarket;
import com.example.marketComponents.model.SupermarketProduct;
import com.example.marketComponents.repository.ProductRepository;
import com.example.marketComponents.repository.SupermarketProductRepository;
import com.example.marketComponents.repository.SupermarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.marketComponents.dto.Coordinates;

import javax.persistence.EntityNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupermarketService {
    final
    SupermarketRepository supermarketRepository;
    final
    SupermarketProductService supermarketProductService;
    final
    ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger (SupermarketService.class);
    final private SupermarketProductRepository supermarketProductRepository;

    public SupermarketService(SupermarketRepository supermarketRepository, SupermarketProductService supermarketProductService, ProductRepository productRepository, SupermarketProductRepository supermarketProductRepository) {
        this.supermarketRepository = supermarketRepository;
        this.supermarketProductService = supermarketProductService;
        this.productRepository = productRepository;
        this.supermarketProductRepository = supermarketProductRepository;
    }

    public Supermarket createSupermarket(Supermarket supermarket) {
        return supermarketRepository.save (supermarket);
    }

    public List<Supermarket> getAllSupermarkets() {
        return supermarketRepository.findAll ();
    }

    public List<SupermarketProduct> getAllSupermarketspr() {
        return supermarketProductService.getAll ();
    }

    public List<Supermarket> getAllSupermarketsWithProducts() {
        return supermarketRepository.findAll ();
    }

    public Supermarket getSupermarketById(Long id) throws ResourceNotFoundException {
        return supermarketRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException ("Supermarket", "id", id));
    }

    public Supermarket updateSupermarket(Long id, Supermarket supermarketDetails) throws ResourceNotFoundException {
        Supermarket supermarket = getSupermarketById (id);
        supermarket.setName (supermarketDetails.getName ());
        supermarket.setLocationX (supermarketDetails.getLocationX ());
        supermarket.setLocationY (supermarketDetails.getLocationY ());
        return supermarketRepository.save (supermarket);
    }

    public void deleteSupermarket(Long id) throws ResourceNotFoundException {
        Supermarket supermarket = getSupermarketById (id);
        supermarketProductService.getAll ().stream ()
                .filter (s -> s.getSupermarket ().equals (supermarket)).toList ()
                .forEach (s -> supermarketProductService.deleteById (s.getId ()));
        supermarketRepository.delete (supermarket);
    }

    public void removeProductFromSupermarket(Long productId, Long supermarketId) throws ResourceNotFoundException {
        Supermarket supermarket = supermarketRepository.findById (supermarketId)
                .orElseThrow (() -> new ResourceNotFoundException ("Supermarket not found with id: " + supermarketId));
        Product product = productRepository.findById (productId)
                .orElseThrow (() -> new ResourceNotFoundException ("Product not found with id: " + productId));
        SupermarketProduct supermarketProduct = supermarketProductService.getAll ().stream ()
                .filter (s -> s.getSupermarket ().equals (supermarket))
                .filter (s -> s.getProduct ().equals (product)).toList ().get (0);
        supermarketProductService.deleteById (supermarketProduct.getId ());
    }

    public void addProductFromSupermarket(Long productId, Long supermarketId, ProductDto productDto) throws EntityNotFoundException {
        Supermarket supermarket = supermarketRepository.findById (supermarketId)
                .orElseThrow (() -> new EntityNotFoundException ("Supermarket not found with id: " + supermarketId));

        Product product = productRepository.findById (productId)
                .orElseThrow (() -> new EntityNotFoundException ("Product not found with id: " + productId));
        SupermarketProduct supermarketProduct = new SupermarketProduct ();
        supermarketProduct.setProduct (product);
        supermarketProduct.setStock (productDto.getStock ());
        supermarketProduct.setPrice (productDto.getPrice ());
        supermarketProduct.setSupermarket (supermarket);
        supermarketProductService.save (supermarketProduct);
    }
    public List<Supermarket> searchInTheArea(Coordinates userCo, double radius) {
        return this.getAllSupermarkets ()
                .stream ()
                .filter (supermarket -> distanceFromPoint (userCo, parseCoordinates (supermarket), radius))
                .toList ();
    }
    public List<SupermarketUserList> searchInAreaContainingProducts(Coordinates userCo, double radius, List<Product> products) {
        return searchInTheArea (userCo, radius)
                .stream ()
                .map (supermarket -> getSupermarketListData (supermarket, products))
                .toList ();
    }

    private SupermarketUserList getSupermarketListData(Supermarket supermarket, List<Product> products) {
        List<Product> supermarketProducts = supermarketProductRepository
                .getSupermarketProductBySupermarketId (supermarket.getId ())
                .stream ()
                .map (SupermarketProduct::getProduct)
                .toList ();
        SupermarketUserList data = new SupermarketUserList ();
        List<Product> intersection = new ArrayList<> ();
        List<Product> nonIntersection = new ArrayList<> ();
        for (Product product : products) {
            if (supermarketProducts.contains (product)) {
                intersection.add (product);
            } else
                nonIntersection.add (product);
        }
        data.setContains (intersection);
        data.setDontContains (nonIntersection);
        data.setContainingSize (intersection.size ());
        data.setOriginalItemsSize (products.size ());
        double percentage = (double) intersection.size () / products.size () * 100;
        data.setContainPercentage (percentage);
        return data;
    }

    private boolean distanceFromPoint(Coordinates userLocation, Coordinates marketLocation, double radius) {
        double distance = calculateDistance (userLocation, marketLocation);
        return (distance <= radius);
    }

    private Coordinates parseCoordinates(Supermarket supermarket) {
        return new Coordinates (
                Double.parseDouble (supermarket.getLocationX ()),
                Double.parseDouble (supermarket.getLocationY ())
        );
    }

    private double calculateDistance(Coordinates co1, Coordinates co2) {

        double lat1Rad = Math.toRadians (co1.getX ());
        double lon1Rad = Math.toRadians (co1.getY ());
        double lat2Rad = Math.toRadians (co2.getX ());
        double lon2Rad = Math.toRadians (co2.getY ());

        // Earth's radius in kilometers
        double earthRadius = 6371.0;

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Use the Haversine formula to calculate the distance
        double a = Math.sin (dLat / 2) * Math.sin (dLat / 2)
                + Math.cos (lat1Rad) * Math.cos (lat2Rad)
                * Math.sin (dLon / 2) * Math.sin (dLon / 2);
        double c = 2 * Math.atan2 (Math.sqrt (a), Math.sqrt (1 - a));
        DecimalFormat decimalFormat = new DecimalFormat ("#.##");

        return Double.parseDouble (decimalFormat.format (earthRadius * c));
    }
}

