/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.ProductCardDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.dto.ProductCard;
import model.entity.Category;

/**
 *
 * @author Leo
 */
public class Shop extends HttpServlet {

    private CategoryDAO categoryDao;
    private ProductCardDAO productCardDao;

    @Override
    public void init() {
        categoryDao = CategoryDAO.getInstance();
        productCardDao = ProductCardDAO.getInstance();
    }

    private static final Map<String, String> PRICE_RANGE_MAP = Map.of(
            "P1", "0-200000",
            "P2", "200000-500000",
            "P3", "500000-1000000",
            "P4", "1000000-10000000");

    private static final Map<String, String> PRICE_LABEL_MAP = createPriceLabelMap();

    private static Map<String, String> createPriceLabelMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "Dưới 200,000");
        map.put("P2", "Từ 200,000 - 500,000");
        map.put("P3", "Từ 500,000 - 1,000,000");
        map.put("P4", "Trên 1,000,000");
        return map;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadInitialData(request);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        loadInitialData(request);

        //Get Parameters Filter
        String[] subCategories = request.getParameterValues("chkSubCategory");
        String[] prices = request.getParameterValues("chkPrice");

        //Check null 
        if ((subCategories == null || subCategories.length == 0)
                && (prices == null || prices.length == 0)) {
            
//            PrintWriter out = response.getWriter();
//            out.println("null");
            request.getRequestDispatcher("shop.jsp").forward(request, response);
            return;
        }

        //Get subCategory filter set
        Set<Integer> subCategoryFilter = new HashSet<>();
        if (subCategories != null) {
            for (String s : subCategories) {
                try {
                    subCategoryFilter.add(Integer.valueOf(s));
                } catch (NumberFormatException e) {
                }
            }
            //set category check list
            request.setAttribute("categoriesChkList", Arrays.asList(subCategories));
        }

        //Get price filter set
        Set<String> priceFilter = new HashSet<>();
        if (prices != null) {
            priceFilter.addAll(Arrays.asList(prices));

            //set price check list
            request.setAttribute("pricesChkList", Arrays.asList(prices));
        }

        //Get all products   
        List<ProductCard> allProducts = productCardDao.getAllProductCards();

        //filter by category
        List<ProductCard> filteredByCategory;
        if (subCategoryFilter.isEmpty()) {
            filteredByCategory = allProducts;
        } else {
            filteredByCategory = filterByCategory(allProducts, subCategoryFilter);
        }

        //filter by price
        List<ProductCard> filterdByPrice;
        if (priceFilter.isEmpty()) {
            filterdByPrice = filteredByCategory;
        } else {
            filterdByPrice = filterByPrice(filteredByCategory, priceFilter);
        }

        request.setAttribute("productCards", filterdByPrice);

        //test
//        PrintWriter out = response.getWriter();
//        for(String s : subCategories) {
//            out.println(s);
//        }
//        for(String s : prices) {
//            out.println(s);
//        }

        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }

    private void loadInitialData(HttpServletRequest request) {

        //Load Category    
        List<Category> categories = categoryDao.getCategoriesByParentId(0);
        request.setAttribute("categories", categories);

        //Load SubCategory
        Map<Integer, List<Category>> subCategoriesMap = new HashMap<>();
        for (Category c : categories) {
            List<Category> subCategories = categoryDao.getCategoriesByParentId(c.getCategoryId());
            subCategoriesMap.put(c.getCategoryId(), subCategories);
        }
        request.setAttribute("subCategoriesMap", subCategoriesMap);

        //Load Price
        request.setAttribute("prices", PRICE_LABEL_MAP);

        //ProductCard   
        List<ProductCard> productCards = productCardDao.getAllProductCards();
        request.setAttribute("productCards", productCards);
    }

    private List<ProductCard> filterByCategory(List<ProductCard> products, Set<Integer> categories) {
        List<ProductCard> filtered = new ArrayList<>();
        for (ProductCard p : products) {
            if (categories.contains(p.getCategoryId())) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    private List<ProductCard> filterByPrice(List<ProductCard> products, Set<String> priceKey) {
        List<ProductCard> priceFiltered = new ArrayList<>();
        Set<ProductCard> productsSet = new HashSet<>(products);

        for (String key : priceKey) {
            String range = PRICE_RANGE_MAP.get(key);
            if (range == null) {
                continue;
            }

            String[] parts = range.split("-");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);

            //get products in price range
            List<ProductCard> productsInRange = productCardDao.getProductCardsByPrice(from, to);

            for (ProductCard pc : productsInRange) {
                if (productsSet.contains(pc)) {
                    priceFiltered.add(pc);
                }
            }

        }

        return priceFiltered;
    }

}
