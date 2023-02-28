package zipview_server.zipview.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zipview_server.config.BaseException;
import zipview_server.utils.SecretKey;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static zipview_server.config.BaseResponseStatus.EMPTY_JWT;
import static zipview_server.config.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    public String createJwt(String id){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("id",id)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*24)))
                .signWith(SignatureAlgorithm.HS256, SecretKey.JWT_SECRET_KEY)
                .compact();
    }

    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("ZIPVIEW-ACCESS-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SecretKey.JWT_SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId() throws BaseException {
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(SecretKey.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }


        return claims.getBody().get("id",String.class);
    }

}
