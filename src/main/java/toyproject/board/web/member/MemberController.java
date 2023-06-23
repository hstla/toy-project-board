package toyproject.board.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.board.domain.member.MapMemberRepository;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.member.MemberService;
import toyproject.board.web.member.form.MemberJoinForm;
import toyproject.board.web.member.form.MemberLoginForm;
import toyproject.board.web.member.form.MemberUpdateForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MapMemberRepository mapMemberRepository;

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

    @GetMapping("/member/{id}")
    public String user(@PathVariable Long id, Model model) {
        Member findMember = mapMemberRepository.findById(id);
        model.addAttribute("form", findMember);
        return "member/member";
    }

    @GetMapping("/member/edit/{id}")
    public String updateMemberForm(@PathVariable Long id, Model model) {
        Member findMember = mapMemberRepository.findById(id);
        model.addAttribute("form", findMember);
        return "member/editForm";
    }

    @PostMapping("/member/edit/{id}")
    public String updateMember(@PathVariable Long id, @Validated @ModelAttribute("form") MemberUpdateForm memberUpdateForm, BindingResult bindingResult) {
        mapMemberRepository.updateMember(id, memberUpdateForm.FormTOMember());
        return "redirect:/member/{id}";
    }

    @GetMapping("/member/withdrawal/{id}")
    public String memberWithdrawal(@PathVariable Long id, HttpServletRequest request) {
        memberService.withdrawal(id);

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        return "redirect:/posts";
    }
}
