/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.CategoryMedicine;
import com.vvmntl.pojo.Medicine;
import com.vvmntl.services.CategoryMedicineServices;
import com.vvmntl.services.CategoryService;
import com.vvmntl.services.MedicineServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author locnguyen
 */
@Controller
//@RequestMapping("/service")
public class MedicineController {
    @Autowired
    private MedicineServices mediService;
    @Autowired
    private CategoryService cateService;
    @Autowired
    private CategoryMedicineServices cateMediService;
    
    @GetMapping("/medicines")
    public String list(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("categories", this.cateService.getCates());
        model.addAttribute("medicines", this.mediService.getMedicines(null));
        return "medicines";
    }
    
    @GetMapping("/medicines/{medicineId}")
    public String update(Model model, @PathVariable(value = "medicineId") int id) {
        model.addAttribute("medicine", this.mediService.getMedicineById(id));
        model.addAttribute("categories", this.cateService.getCates());
        
        return "medicines";
    }
    
    @PostMapping("/medicines")
    public String create(@ModelAttribute(value="medicine") Medicine m, @RequestParam("categoryId") Integer categoryId) {
        this.mediService.addOrUpdate(m);
        CategoryMedicine cm = new CategoryMedicine();
        
        cm.setCategoryId(this.cateService.getCategoryById(categoryId));
        cm.setMedicineId(m);
        this.cateMediService.addOrUpdate(cm);
        
        return "redirect:/";
    }
}
