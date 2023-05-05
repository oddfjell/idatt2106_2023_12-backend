package no.ntnu.idatt2106.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * RequestFilter to check http requests
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    /**
     * JWTService field injection
     */
    @Autowired
    private JWTService jwtService;
    /**
     * AccountRepository field injection
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Method that checks the header "Authorization" to see if it starts with: "Bearer " since it then contains a
     * JWTToken. If the token is valid the HttpServletRequest gets through
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain FilterChain
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try{
                String username = jwtService.getUsername(token);
                Optional<AccountEntity> optionalUserEntity = accountRepository.findByUsernameIgnoreCase(username);

                if(optionalUserEntity.isPresent()){
                    AccountEntity user = optionalUserEntity.get();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }catch (JWTDecodeException ignored){
            }
        }
        filterChain.doFilter(request, response);
    }
}
