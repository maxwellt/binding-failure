package be.bluemagma.bindingfailure;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FormController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/formRecord")
    public String getFormRecord(Model model) {
        model.addAttribute("form", new FormRecord("", "", null));
        model.addAttribute("types", Type.values());
        model.addAttribute("action", "/formRecord");
        return "form";
    }

    @PostMapping("/formRecord")
    public String processFormRecord(@Valid @ModelAttribute("form") FormRecord form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", Type.values());
            model.addAttribute("action", "/formRecord");
            model.addAttribute("form", form);
            return "form";
        }

        redirectAttributes.addFlashAttribute("formSubmitted", "success");
        return "redirect:/";
    }

    @GetMapping("/formClassJavaBean")
    public String getFormClassJavaBean(Model model) {
        model.addAttribute("form", new FormClassJavaBean("", "", null));
        model.addAttribute("types", Type.values());
        model.addAttribute("action", "/formClassJavaBean");
        return "form";
    }

    @PostMapping("/formClassJavaBean")
    public String processFormClass(@Valid @ModelAttribute("form") FormClassJavaBean form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", Type.values());
            model.addAttribute("action", "/formClassJavaBean");
            return "form";
        }

        redirectAttributes.addFlashAttribute("formSubmitted", "success");
        return "redirect:/";
    }

    @GetMapping("/formClassWithoutSetters")
    public String getFormClassWithoutSetters(Model model) {
        model.addAttribute("form", new FormClassWithoutSetters("", "", null));
        model.addAttribute("types", Type.values());
        model.addAttribute("action", "/formClassWithoutSetters");
        return "form";
    }

    @PostMapping("/formClassWithoutSetters")
    public String processFormClassWithoutSetters(@Valid @ModelAttribute("form") FormClassWithoutSetters form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", Type.values());
            model.addAttribute("action", "/formClassWithoutSetters");
            return "form";
        }

        redirectAttributes.addFlashAttribute("formSubmitted", "success");
        return "redirect:/";
    }
}
