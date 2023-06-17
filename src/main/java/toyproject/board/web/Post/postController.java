package toyproject.board.web.Post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.board.domain.post.MapPostRepository;
import toyproject.board.domain.post.Post;
import toyproject.board.web.Post.form.PostSaveForm;
import toyproject.board.web.Post.form.PostUpdateForm;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class postController {
    private final MapPostRepository mapPostRepository;

    @GetMapping()
    public String posts(Model model) {
        List<Post> posts = mapPostRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("post", new PostSaveForm());
        return "posts/addPost";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post") @Validated PostSaveForm createPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/addPost";
        }
        Post post = new Post(createPost.getWriter(), createPost.getTitle(), createPost.getContent());
        mapPostRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        log.info("id={}", id);
        Post findPost = mapPostRepository.findById(id);
        mapPostRepository.updateViewCount(findPost);
        model.addAttribute("post", findPost);
        return "posts/post";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Post findPost = mapPostRepository.findById(id);
        model.addAttribute("post", findPost);
        return "posts/editPost";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @Validated @ModelAttribute("post") PostUpdateForm form,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "posts/editPost";
        }
        Post post = new Post(form.getWriter(), form.getTitle(), form.getContent());
        mapPostRepository.update(id, post);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/posts/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        mapPostRepository.deletePost(id);
        return "redirect:/posts";
    }
}
