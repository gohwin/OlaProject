package com.ola.adminController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ola.service.SalesService;

@Controller
public class adminSaleController {
    
    @Autowired
    private SalesService salesService;
    
    @GetMapping("/admin/saleChart")
    public String saleChartView(Model model) {
        Map<String, Map<Long, Long>> salesData = salesService.getTotalSalesData();
        model.addAttribute("salesVolumeData", salesData.get("salesVolume"));
        model.addAttribute("salesAmountData", salesData.get("salesAmount"));
        return "admin/saleChart";
    }

    @GetMapping("/admin/saleChart/1month")
    public String get1MonthSalesData(Model model) {
        Map<String, Map<Long, Long>> salesData = salesService.getMonthlySalesData();
        model.addAttribute("salesVolumeData", salesData.get("salesVolume"));
        model.addAttribute("salesAmountData", salesData.get("salesAmount"));
        return "admin/saleChart";
    }

    @GetMapping("/admin/saleChart/3months")
    public String get3MonthsSalesData(Model model) {
        Map<String, Map<Long, Long>> salesData = salesService.get3MonthsSalesData();
        model.addAttribute("salesVolumeData", salesData.get("salesVolume"));
        model.addAttribute("salesAmountData", salesData.get("salesAmount"));
        return "admin/saleChart";
    }

    @GetMapping("/admin/saleChart/6months")
    public String get6MonthsSalesData(Model model) {
        Map<String, Map<Long, Long>> salesData = salesService.get6MonthsSalesData();
        model.addAttribute("salesVolumeData", salesData.get("salesVolume"));
        model.addAttribute("salesAmountData", salesData.get("salesAmount"));
        return "admin/saleChart";
    }

    @GetMapping("/admin/saleChart/1year")
    public String get1YearSalesData(Model model) {
        Map<String, Map<Long, Long>> salesData = salesService.get1YearSalesData();
        model.addAttribute("salesVolumeData", salesData.get("salesVolume"));
        model.addAttribute("salesAmountData", salesData.get("salesAmount"));
        return "admin/saleChart";
    }
}
