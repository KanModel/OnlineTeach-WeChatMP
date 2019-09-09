package me.kanmodel.july19.onlineteach.controller;

import me.kanmodel.july19.onlineteach.dao.OptionRepository;
import me.kanmodel.july19.onlineteach.entity.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 * @description: 接收对系统配置更改的请求
 * @author: KanModel
 * @create: 2019-07-09 09:48
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private OptionRepository optionRepository;

    @ModelAttribute
    public void generalModel(Model model){
        model.addAttribute("site_name", optionRepository.findByKey("site_name").get().getValue());
    }

    @RequestMapping("")
    public String manage(Model model) {
        List<Option> list = optionRepository.findAll();
        model.addAttribute("optionList", list);
        return "manage/manage";
    }

    @RequestMapping("/option/edit")
    public String editOption(Model model,
                             Long id,
                             String value) {
        Optional<Option> optionalOption = optionRepository.findById(id);
        if (optionalOption.isPresent()){
            Option option = optionalOption.get();
            option.setValue(value);
            optionRepository.save(option);
        }
        return "redirect:/manage";
    }
}
