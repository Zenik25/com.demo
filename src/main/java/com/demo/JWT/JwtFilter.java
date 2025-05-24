package com.demo.JWT;

import com.demo.POJO.Account;
import com.demo.dao.AccountDao;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailsService service;

    private Claims claims = null;
    private String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Public endpoints (bypass JWT validation)
        if (path.matches("/user/login|/user/forgotpassword|/user/signup|/category/get|/product/get|/deliveries/get|/deliveries/add|/table-login/auto-login/\\d+|/auto-login|/orders/placeOrder|/orders/getByShopId/\\d+|/delivery/placeOrder|/delivery/getByEmail|/delivery/getOrderByShop/\\d+|/bill/generate|/table-login/edit/\\d+|/table-login/all|/table-login/status/\\d+|/table-login/update-status/\\d+|/category/add|/category/update|/category/delete/\\d+|/product/add|/product/update/\\d+|/product/update-status/\\d+|/product/delete/\\d+|/delivery/all|/delivery/\\d+|/delivery/status/\\d+|/user/get|/user/admin|/orders/get|/orders/\\d+|/shop/add|/shop/update|/shop/delete/\\d+|/shop/get|/account/create|/account/all|/account/update/\\d+|/account/delete/\\d+|/account/login|/account/id/\\d+|/table-login/grouped-by-shop|/table-login/shop/\\d+|table-login/all|/shop/shop-name/\\d+|/product/shop/\\d+")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(token);
            }

            if (token != null && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = service.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // ✅ Save email into ThreadLocal for access across backend
                    currentUserEmail.set(username);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT Token");
                    return;
                }
            }

            filterChain.doFilter(request, response);

        } finally {
            // ✅ Cleanup to prevent memory leaks
            currentUserEmail.remove();
        }
    }

    @Autowired
    AccountDao accountDao;
//    private String currentUserEmail;
    // ✅ Prevent NullPointerException in claims
    public boolean isAdmin() {
        return claims != null && "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        return claims != null && "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {
        return username != null ? username : "Unknown";  // ✅ Handle null usernames safely
    }
    private static final ThreadLocal<String> currentUserEmail = new ThreadLocal<>();
    public String getCurrentUserEmail() {
        return currentUserEmail.get();
    }

    public Integer getCurrentUserShopId() {
        String email = currentUserEmail.get();
        if (email != null) {
            Account account = accountDao.findByEmail(email);
            if (account != null && account.getShop() != null) {
                return account.getShop().getId();
            }
        }
        return null;
    }

}
