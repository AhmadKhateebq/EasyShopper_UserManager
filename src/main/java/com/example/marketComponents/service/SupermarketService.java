package com.example.marketComponents.service;

import com.example.marketComponents.controller.util.ProductDto;
import com.example.marketComponents.dto.Coordinates;
import com.example.marketComponents.dto.SupermarketUserList;
import com.example.marketComponents.exception.ResourceNotFoundException;
import com.example.marketComponents.model.Product;
import com.example.marketComponents.model.ProductPrice;
import com.example.marketComponents.model.Supermarket;
import com.example.marketComponents.model.SupermarketProduct;
import com.example.marketComponents.repository.ProductRepository;
import com.example.marketComponents.repository.SupermarketProductRepository;
import com.example.marketComponents.repository.SupermarketRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SupermarketService {
    final
    SupermarketRepository supermarketRepository;
    final
    SupermarketProductService supermarketProductService;
    final
    ProductRepository productRepository;
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

    public List<SupermarketUserList> searchInAreaContainingProducts(double radius, Map<String, Object> requestBody) {
        Coordinates userCo = coordinatesDecoder (requestBody);
        List<Product> products = productsListDecoder (requestBody);
        return searchInTheArea (userCo, radius)
                .stream ()
                .map (supermarket -> getSupermarketListData (supermarket, products))
                .toList ();
    }

    private SupermarketUserList getSupermarketListData(Supermarket supermarket, List<Product> products) {
        List<SupermarketProduct> supermarketProducts = supermarketProductRepository
                .getSupermarketProductBySupermarketId (supermarket.getId ());
        List<Product> productList = supermarketProducts.stream ()
                .map (SupermarketProduct::getProduct)
                .toList ();
        SupermarketUserList data = new SupermarketUserList ();
        List<Product> intersection = new ArrayList<> ();
        List<Product> nonIntersection = new ArrayList<> ();
        for (Product product : products) {
            if (productList.contains (product)) {
                intersection.add (product);
            } else
                nonIntersection.add (product);
        }
        List<ProductPrice> contains = new ArrayList<> ();
        for (int i = 0; i < productList.size (); i++) {
            if (intersection.contains (productList.get (i))){
                SupermarketProduct product = supermarketProducts.get (i);
                contains.add (new ProductPrice (product.getId (),product.getProduct (),product.getPrice (),product.getStock ()));
            }
        }
        data.setContains (contains);
        data.setDontContains (nonIntersection);
        data.setContainingSize (intersection.size ());
        data.setOriginalItemsSize (products.size ());
        data.setSupermarket (supermarket);
        double total = 0;

        for (ProductPrice supermarketProduct : contains) {
            total += supermarketProduct.getPrice ();
        }

        data.setTotal (total);
        double percentage = (double) intersection.size () / products.size () * 100.00;
        percentage = Math.round (percentage * 100.0) / 100.0;
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

    private Coordinates coordinatesDecoder(Map<String, Object> requestBody) {
        Map<String, String> coordinatesMap = ((List<Map<String, String>>) requestBody.get ("Coordinates")).stream ().findFirst ().get ();
        Coordinates coordinates = new Coordinates ();
        coordinates.setX (Double.parseDouble (coordinatesMap.get ("x")));
        coordinates.setY (Double.parseDouble (coordinatesMap.get ("y")));
        return coordinates;
    }

    public List<Product> productsListDecoder(Map<String, Object> requestBody) {
        List<Map<String, Object>> productsMapList = ((List<Map<String, Object>>) requestBody.get ("products"));
        List<Product> products = new ArrayList<> ();
        for (Map<String, Object> stringMapMap : productsMapList) {
            Product product = new Product ();
            if (stringMapMap.containsKey ("id"))
                product.setId (Long.valueOf (String.valueOf (stringMapMap.get ("id"))));
            else
                product.setId (-1L);
            product.setName (String.valueOf (stringMapMap.get ("name")));
            product.setBrand (String.valueOf (stringMapMap.get ("brand")));
            product.setCategory (String.valueOf (stringMapMap.get ("category")));
            product.setDescription (String.valueOf (stringMapMap.get ("description")));
            product.setImageUrl (String.valueOf (stringMapMap.get ("imageUrl")));
            products.add (product);
        }
        return products;
    }
}

