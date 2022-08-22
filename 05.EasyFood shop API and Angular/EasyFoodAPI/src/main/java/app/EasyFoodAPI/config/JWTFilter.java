//package app.EasyFoodAPI.config;
//
//import app.EasyFoodAPI.security.JWTUtil;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//    private final JWTUtil jwtUtil;
//
//    public JWTFilter(JWTUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // every query goes throw this filter. we try to receive authHeader from it
//        String authHeader = request.getHeader("Authorization");
//
//        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
//            String jwt = authHeader.substring(7); // remove "Bearer " from string
//            if(jwt.isBlank()) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//                        "Invalid JWT Token in Authorization header");
//            } else {
//                try {
//                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
//                } catch (JWTVerificationException e) {
//                    // if jwtUtil.validateTokenAndRetrieveClaim(jwt) thew exception
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//                            "Invalid JWT Token in Authorization header");
//                }
//            }
//        }
//    }
//}

//  error on every request