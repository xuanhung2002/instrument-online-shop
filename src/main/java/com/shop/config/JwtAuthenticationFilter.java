package com.shop.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.service.UserService;
import com.shop.service.impl.CustomUserDetailsService;
import com.shop.service.impl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService,
			JwtTokenProvider jwtTokenProvider) {
		this.customUserDetailsService = customUserDetailsService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	private String getJwtFromRequest(jakarta.servlet.http.HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException {
		try {
			// Lấy jwt từ request
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				// Lấy id user từ chuỗi jwt
//				Integer userId = JwtTokenProvider.getUserIdFromJWT(jwt).intValue();
				String username = JwtTokenProvider.getUsernameFromToken(jwt);
				// Lấy thông tin người dùng từ id
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				if (userDetails != null) {
					// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			logger.error("failed on set user authentication", ex);
		}

		filterChain.doFilter(request, response);

	}
}