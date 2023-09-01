package ddym_corp.quoridor.history.web;

import ddym_corp.quoridor.auth.web.SessionConst;
import ddym_corp.quoridor.history.History;
import ddym_corp.quoridor.history.sevice.HistoryService;
import ddym_corp.quoridor.history.utils.Histories20ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HistoryControllerImpl {
    private final HistoryService historyService;
    @GetMapping("/histories")
    public List<Histories20ResponseDto> getRecentHistories(@RequestParam Long recentGameId, HttpServletRequest request){
        HttpSession session = getHttpSession(request);
        Long uid = (Long) session.getAttribute(SessionConst.USER_ID);
        return historyService.getHistories(uid, recentGameId);
    }
    @GetMapping("/histories/{gameId}")
    public History getDetailHistory(@PathVariable Long gameId, HttpServletRequest request){
        getHttpSession(request);
        return historyService.getOneHistory(gameId);
    }
    private static HttpSession getHttpSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(1800);
        return session;
    }
}
