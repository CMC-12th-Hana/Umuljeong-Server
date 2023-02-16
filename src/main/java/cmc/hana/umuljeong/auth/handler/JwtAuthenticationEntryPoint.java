package cmc.hana.umuljeong.auth.handler;

import cmc.hana.umuljeong.exception.ApiErrorResult;
import cmc.hana.umuljeong.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiErrorResult apiErrorResult = ApiErrorResult.builder()
                .errorCode(errorCode)
                .message(errorCode.getMessage())
                .cause(errorCode.getClass().getName())
                .build();
        try{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer.write(apiErrorResult.toString());
        }catch(NullPointerException e){
            LOGGER.error("응답 메시지 작성 에러", e);
        }finally{
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
        response.getWriter().write(apiErrorResult.toString());
    }
}
