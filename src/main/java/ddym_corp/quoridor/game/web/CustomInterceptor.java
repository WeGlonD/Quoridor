package ddym_corp.quoridor.game.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

import static ddym_corp.quoridor.auth.web.SessionConst.*;

@Slf4j
public class CustomInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("CustomInterceptor beforeHandshake");
        if (request instanceof ServletServerHttpRequest) {
            log.info("request is instanceof ServletServerHttpRequest");
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            HttpServletRequest servletRequest = serverRequest.getServletRequest();
            HttpSession session = servletRequest.getSession();
            Long uid = Long.valueOf(servletRequest.getParameter("uid"));
            Long gameId = Long.valueOf(servletRequest.getParameter("gameId"));
            Integer turn = Integer.valueOf(servletRequest.getParameter("turn"));

            log.info("session get(): {}", session);
            for (Iterator<String> it = session.getAttributeNames().asIterator(); it.hasNext(); ) {
                String n = it.next();
                log.info("session attribute name: {}", n);
                log.info("session attribute value: {}", session.getAttribute(n));
            }

            log.info("session id: {}", session.getId());
            log.info("uid: {}", uid);
            log.info("gameId: {}", gameId);
            log.info("turn: {}", turn);
            attributes.put(USER_ID, uid);
            attributes.put(GAME_ID,gameId);
            attributes.put(TURN,turn);
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void setCopyAllAttributes(boolean copyAllAttributes) {
        super.setCopyAllAttributes(true);
    }

    @Override
    public void setCopyHttpSessionId(boolean copyHttpSessionId) {
        super.setCopyHttpSessionId(true);
    }
}
