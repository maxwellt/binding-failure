## Strange behaviour in data binding in Spring MVC

I have setup this project to highlight the differences in how data binding works between different types of backing objects.

In `index.html` there are three links:

* Submit the form that is backed by a Java class and is JavaBean compliant (i.e. getters and setters)
* Submit the form that is backed by a Java class that is not JavaBean compliant (setters have been omitted)
* Submit the form that is backed by a Java record (which is never JavaBean compliant as is it immutable)

`form.html` is re-used for each of the different form backing objects.

In the form the first line will print out the `firstName` value (empty on form load of course).

You will see that the form has three fields, `firstName`, `lastName` and `type`. The last field is bound to the enum `Type` and has only two possible correct values, `ACTIVE` and `INACTIVE`. You will see that there is a third item in the dropdownlist, which is invalid (more on that later).

To run the project simply `./mvnw spring-boot:run`.

The flow I want to run you though is as follows:

1. Open each of the forms
2. Submit as is

You will see that in all three cases the `firstName` and `lastName` field's background color changed to red, to indicate that the values were invalid. This is because those two fields are annotated with `@NotBlank`.

If all has gone well, in each of the three cases those two fields should have been red. The Java class non-JavaBean compliant form will have shown `null` for 'Entered firstName', whereas the Record form will have shown nothing (just like the JavaBean form)

Now go through the following flow for each of the forms:

1. Enter a value in the `firstName` field
2. Select the `INVALID_VALUE` in the dropdownlist
3. Submit

You should see the following results:

* JavaBean form does as you would expect: `firstName` and `type` are marked red as they are invalid and you see the entered `firstName`
* Class without setters will mark `firstName` and `lastName` red **and** the value for `type` is reset to `ACTIVE` and you will see `null` for 'Entered first name:'
* Record form will have thrown an HTTP 500.

The fact that the JavaBean form does exactly what we expect is not wholly unsurprising. In the Spring MVC documentation they explicitly state:

> Only public properties following the JavaBeans naming conventions are exposed for data binding — for example, public String getFirstName() and public void setFirstName(String) methods for a firstName property.

For the non-JavaBean compliant form it seems like it is simply reset to an empty form, which is why the `firstName` property is not retained and shown on the page and why the `INVALID_VALUE` is no longer selected.

In the case of the Record form, the backing object itself seems to be set to NULL, which is why we see the HTTP 500, the logs print the following stacktrace:

> org.springframework.expression.spel.SpelEvaluationException: EL1007E: Property or field 'firstName' cannot be found on null

If you were to re-add the `form` to the `model` manually, everything would work fine, so change the `processRecordForm` from this:

    @PostMapping("/formRecord")
    public String processFormRecord(@Valid @ModelAttribute("form") FormRecord form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", Type.values());
            model.addAttribute("action", "/formRecord");
            return "form";
        }

        redirectAttributes.addFlashAttribute("formSubmitted", "success");
        return "redirect:/";
    }

To this:

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

And it works as you would expect...

Furthermore, if you were to remove the printing of the `firstName` value, i.e. the following line:

`<p th:text="|Entered first name: ${form.firstName}|"></p>` in `form.html` and then run through the second scenario again, you'll get another (unexpected outcome). Namely, there is no longer an HTTP 500, but after the `typeMismatch` due to using an invalid enum value for `type`, the binding seems to have stopped completely, because the `lastName` field will **not** be marked as invalid...

To summarize into questions I'm left with: 

1. What's the difference in data binding between a non-JavaBean compliant Java class and a Java record? There clearly is a difference as I'm getting different behavior.
2. Why is the form not automatically being added again to the `model`, it's clearly marked as `@ModelAttribute`.




