package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFromControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() { // 매핑정보에 역할을 한다
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFromControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI(); // 주소를 받는다
        ControllerV3 controller = controllerMap.get(requestURI); // 주소에 해당되는 컨트롤러를 꺼낸다 (다형성)
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 모든 파라미터 꺼내서 Map에 넣어서 paramMap 만들어줌
        Map<String, String> paramMap = createParamMap(request);
        // paramMap 넘겨줌
        ModelView mv = controller.process(paramMap);
        // 논리이름을 얻어옴
        String viewName = mv.getViewName();
        // 알맞은 view에 맵핑함
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);

    }

    //level을 맞춰주기 위해 함수로 분리하는것이 좋다.
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    //level을 맞춰주기 위해 함수로 분리하는것이 좋다.
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap= new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
