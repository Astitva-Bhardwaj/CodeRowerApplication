package com.example.backend.controller;

import com.example.backend.model.ConfigurationEntity;
import com.example.backend.repo.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @PostMapping("/{id}")
    public ResponseEntity<Object> addConfiguration(@RequestBody ConfigurationEntity newConfiguration) {
        try {
            ConfigurationEntity savedConfiguration = configurationRepository.save(newConfiguration);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedConfiguration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view")
    public String viewConfigurationPage() {
        return "configurations";
    }

    // Task 1
    @GetMapping("/{id}")
    public ModelAndView getConfiguration(@PathVariable String id, Model model) {
        Optional<ConfigurationEntity> configurationOptional = configurationRepository.findById(id);
        if (configurationOptional.isPresent()) {
            ConfigurationEntity configurationEntity = configurationOptional.get();
            model.addAttribute("configuration", configurationEntity.getData());
            return new ModelAndView("configurations");
        } else {
            return new ModelAndView("configurations"); // Redirect to the main page or handle as needed
        }
    }
    // Task 2
    @PutMapping("/{id}")
    public String updateRemark(@PathVariable String id, @RequestBody UpdateRemarkRequest request, Model model) {
        Optional<ConfigurationEntity> configurationOptional = configurationRepository.findById(id);
        if (configurationOptional.isPresent()) {
            ConfigurationEntity configurationEntity = configurationOptional.get();
            configurationEntity.setRemark(request.getRemark());
            configurationRepository.save(configurationEntity);
            model.addAttribute("configId", id);
            model.addAttribute("updateResultMessage", "Remark updated successfully!");
            return "update-remark";
        } else {
            return "redirect:/error";
        }
    }

    public static class UpdateRemarkRequest {
        private String remark;

        // getters and setters
        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        // getters
        public String getMessage() {
            return message;
        }
    }
}
