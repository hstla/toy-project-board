package toyproject.board.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.member.MemberService;
import toyproject.board.web.member.form.MemberJoinForm;
import toyproject.board.web.member.form.MemberLoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") MemberLoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("form") MemberLoginForm form, HttpServletRequest request) {
        Member loginMember = memberService.login(form.getEmail(), form.getPassword());
        if (loginMember != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
            log.info("login={}", loginMember.getId());
            return "redirect:/posts";
        }

        return "/login/loginForm";
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("form") MemberJoinForm form) {
        return "login/joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("form") MemberJoinForm form, BindingResult bindingResult) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "notSamePassword", "비밀번호가 다릅니다.");
        }

        if (bindingResult.hasErrors()) {
            return "login/joinForm";
        }

        memberService.join(form.FormTOMember());

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // delete session
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}
